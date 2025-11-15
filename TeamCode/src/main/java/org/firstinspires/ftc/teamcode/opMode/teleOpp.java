package org.firstinspires.ftc.teamcode.opMode;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.retired.Tom;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.subsystems.Loader;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.driving.MecanumDriverControlled;

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

    double targetHeading = Math.toRadians(180); // Radians
    PIDFController controller = new PIDFController(new PIDFCoefficients(0.1, 0, 0.01, 0));
    double heading_error = 0;
    boolean heading_lock;
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
                        ? 1
                        : Gamepads.gamepad1().rightStickX().get()
                //new FieldCentric(imu)
        );
        driverControlled.schedule();


        Gamepads.gamepad1().b().whenBecomesTrue(
                new InstantCommand(() -> heading_lock = !heading_lock)

        );

        Gamepads.gamepad1().y().whenBecomesTrue(Flywheel.INSTANCE.shootingVelocity(()->Flywheel.INSTANCE.veloc_targ));
        Gamepads.gamepad1().x().whenBecomesTrue(Flywheel.INSTANCE.shootingVelocity(()->0));

        Gamepads.gamepad1().dpadUp().whenBecomesTrue(
                () -> Flywheel.INSTANCE.veloc_targ += 10
        );
        Gamepads.gamepad1().dpadDown().whenBecomesTrue(
                () -> Flywheel.INSTANCE.veloc_targ -= 10
        );

        Gamepads.gamepad1().leftTrigger().inRange(0.1,1).whenBecomesTrue(Loader.INSTANCE.load_ball);
        Gamepads.gamepad1().rightTrigger().inRange(0.1,1).whenBecomesTrue(Loader.INSTANCE.reset_loader);


        /*
        Gamepads.gamepad1().y().whenBecomesTrue(
                new InstantCommand(() -> AutoAim.INSTANCE.toggle = !AutoAim.INSTANCE.toggle)
        );
         */


        //double turnAngle = Camera.INSTANCE.getAngle();
        //double rX = controller.calculate(new KineticState(Math.toRadians(turnAngle));



    }

    @Override
    public void onUpdate(){
        BindingManager.update();
        //heading_error = 180 - Math.toDegrees(Camera.INSTANCE.getAngle());
        //heading_error = Math.IEEEremainder(heading_error, 2 * Math.PI);
    }

    @Override
    public void onStop(){
        BindingManager.reset();
    }
}


