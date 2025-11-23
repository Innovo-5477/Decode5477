package org.firstinspires.ftc.teamcode.opMode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.pedropathing.follower.Follower;
import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.PedroCoordinates;
import com.pedropathing.geometry.Pose;
import com.qualcomm.hardware.bosch.BHI260IMU;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.retired.Tom;
import org.firstinspires.ftc.teamcode.subsystems.AutoAim;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.subsystems.InterpLUT;
import org.firstinspires.ftc.teamcode.subsystems.Loader;

import java.util.function.BooleanSupplier;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.driving.MecanumDriverControlled;

import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "field centric teleop")
@Configurable
public class teleOpp extends NextFTCOpMode {
    public teleOpp() {
        addComponents(
                new SubsystemComponent(
                        Flywheel.INSTANCE,
                        Loader.INSTANCE,
                        Camera.INSTANCE
                ),
                BindingsComponent.INSTANCE
        );
    }

    private MotorEx frontLeftMotor = new MotorEx("fl").brakeMode().reversed();
    private MotorEx frontRightMotor = new MotorEx("fr").brakeMode();
    private MotorEx backLeftMotor = new MotorEx("bl").brakeMode().reversed();
    private MotorEx backRightMotor = new MotorEx("br").brakeMode();

    InterpLUT interpolate = new InterpLUT(); //am I creating the class the right way even?
    public static PIDFCoefficients headingPIDcoefficients = new PIDFCoefficients(0.0001, 0, 0, 0);
    PIDFController controller = new PIDFController(headingPIDcoefficients);
    double heading_error = 0;
    double heading_lock_power = 0;
    boolean heading_lock;
    double distance = 0;
    double[] camPose = {0, 0, 0, 0};
    double driverOffset = 0;

    //private IMUEx imu = new IMUEx("imu", Direction.LEFT, Direction.FORWARD).zeroed();

    GoBildaPinpointDriver odo;

    @Override
    public void onInit() {
        odo = hardwareMap.get(GoBildaPinpointDriver.class,"pinpoint");
        odo.setOffsets(-4, 4, DistanceUnit.INCH);
        odo.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_SWINGARM_POD);
        odo.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.REVERSED); //x supposed to increase when moving forward, y supposed to increase when moving left
        odo.resetPosAndIMU();
    }

    @Override
    public void onStartButtonPressed() {
        heading_lock = false;
        Command driverControlled = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().leftStickX(),
                () -> heading_lock
                        ? heading_lock_power
                        : Gamepads.gamepad1().rightStickX().get()
        );
        driverControlled.schedule();

        Gamepads.gamepad1().b().whenBecomesTrue(
                new InstantCommand(() -> heading_lock = !heading_lock)

        );
        Gamepads.gamepad1().leftStickButton().whenBecomesTrue(
                new InstantCommand(() -> odo.resetPosAndIMU())
        ); //Command to reset imu

        Gamepads.gamepad1().leftBumper().whenBecomesTrue(
                new InstantCommand(() -> driverOffset = 0)
        ); //Resets driver offset


        Gamepads.gamepad1().y().whenBecomesTrue(Flywheel.INSTANCE.shootingVelocity(() -> Flywheel.INSTANCE.veloc_targ));
        Gamepads.gamepad1().x().whenBecomesTrue(Flywheel.INSTANCE.shootingVelocity(() -> 0));

        Gamepads.gamepad1().dpadUp().whenBecomesTrue(
                () -> Flywheel.INSTANCE.veloc_targ += 10
        );
        Gamepads.gamepad1().dpadDown().whenBecomesTrue(
                () -> Flywheel.INSTANCE.veloc_targ -= 10
        );

        Gamepads.gamepad1().leftTrigger().inRange(0.1, 1)
                .whenBecomesTrue(Loader.INSTANCE.load_ball);

        Gamepads.gamepad1().rightTrigger().inRange(0.1, 1).whenBecomesTrue(Loader.INSTANCE.reset_loader);
    }

    @Override
    public void onUpdate() {
        camPose = Camera.INSTANCE.getCamPose();
        odo.update();

        if (camPose[3] == 1) {
            //odo.setPosition(new Pose2D(DistanceUnit.INCH, camPose[0], camPose[1], AngleUnit.DEGREES,camPose[2]));
        }
        //Flywheel.INSTANCE.veloc_targ = interpolate.get(distance) + driverOffset;

        double odoX = odo.getPosition().getX(DistanceUnit.INCH);
        double odoY = odo.getPosition().getY(DistanceUnit.INCH);

        double goalX = -60;
        double goalY = -62.5;

        double deltaX = odoX - goalX;
        double deltaY = odoY - goalY;
        double targetAngle = Math.toDegrees(Math.atan2(deltaY, deltaX));
        double robotAngle = odo.getHeading(AngleUnit.DEGREES);
        heading_error = AngleUnit.normalizeDegrees(targetAngle - robotAngle);
        controller.updateError(heading_error);
        heading_lock_power = controller.run();

        //telemetry.addData("target angle: ", targetAngle);
        telemetry.addData("heading error: ", heading_error);
        telemetry.addData("----------------------------", "camera stuff:");
        telemetry.addData("cam x:", camPose[0]);
        telemetry.addData("cam y:", camPose[1]);
        telemetry.addData("----------------------------", "pinpoint stuff:");
        telemetry.addData("pinpoint x: ", odoX);
        telemetry.addData("pinpoint y: ", odoY);
        telemetry.addData("pinpoint heading: ", odo.getHeading(AngleUnit.DEGREES));
        telemetry.update();
        BindingManager.update();
    }

    @Override
    public void onStop() {
        BindingManager.reset();
    }
}


