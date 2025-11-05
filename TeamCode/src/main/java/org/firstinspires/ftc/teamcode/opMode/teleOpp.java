package org.firstinspires.ftc.teamcode.opMode;
import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.control.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pancake.Intake;
import org.firstinspires.ftc.teamcode.subsystems.AutoAim;
import org.firstinspires.ftc.teamcode.subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.subsystems.Gate;
import org.firstinspires.ftc.teamcode.subsystems.Loader;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedback.PIDController;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.driving.FieldCentric;
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
                            //AutoAim.INSTANCE
                            Flywheel.INSTANCE,
                            Loader.INSTANCE,
                            Gate.INSTANCE
                    ),
                    BindingsComponent.INSTANCE
            );
        }

    private MotorEx frontLeftMotor = new MotorEx("fl").brakeMode().reversed();
    private MotorEx frontRightMotor = new MotorEx("fr");
    private MotorEx backLeftMotor = new MotorEx("bl").brakeMode().reversed();
    private MotorEx backRightMotor = new MotorEx("br");
    private IMUEx imu = new IMUEx("imu", Direction.UP, Direction.LEFT).zeroed();

    //public static PIDFCoefficients coefficients = new PIDFCoefficients(0.005, 0, 0, 0);
    //PIDFController headingController = new PIDFController(coefficients);
    //double heading_error = 0;
    @Override
    public void onStartButtonPressed() {

        Command driverControlled = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY().negate(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX(),
                new FieldCentric(imu)
        );
        driverControlled.schedule();

        Gamepads.gamepad1().dpadUp().whenBecomesTrue(
                new InstantCommand(() -> imu.zero())

        );
        Gamepads.gamepad1().x()
                .toggleOnBecomesTrue()
                .whenBecomesTrue(Flywheel.INSTANCE.shootingVelocity(2500))
                .whenBecomesFalse(Flywheel.INSTANCE.zeroVelocity);

        Gamepads.gamepad1().leftTrigger().inRange(0, 1)
                .whenBecomesTrue(new SequentialGroup(
                    Gate.INSTANCE.open_gate,
                    new Delay(0.1),
                    Loader.INSTANCE.load_ball,
                    new Delay(0.1),
                    Gate.INSTANCE.close_gate,
                    new Delay(0.1),
                    Loader.INSTANCE.reset_loader
                ));

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
        //heading_error = 45 - Math.toDegrees(imu.get().getValue());
        //heading_error = Math.IEEEremainder(heading_error, 2 * Math.PI);
    }

    @Override
    public void onStop(){
        BindingManager.reset();
    }
}


