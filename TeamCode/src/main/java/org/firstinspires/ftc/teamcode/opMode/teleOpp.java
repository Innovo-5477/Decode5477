package org.firstinspires.ftc.teamcode.opMode;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.LeftFin;
import org.firstinspires.ftc.teamcode.subsystems.LeftGate;
import org.firstinspires.ftc.teamcode.subsystems.RightFin;
import org.firstinspires.ftc.teamcode.subsystems.RightGate;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Sorter;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.bindings.Button;
import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.AngleType;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.core.units.Angle;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.extensions.pedro.PedroDriverControlled;
import dev.nextftc.extensions.pedro.TurnTo;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.DriverControlledCommand;
import dev.nextftc.hardware.driving.FieldCentric;
import dev.nextftc.hardware.driving.MecanumDriverControlled;
import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;
import static dev.nextftc.bindings.Bindings.*;

@TeleOp(name = "tele dkibidi")
@Configurable
public class teleOpp extends NextFTCOpMode {
    public teleOpp() {
            addComponents(
                    new SubsystemComponent(
                            Intake.INSTANCE),
                    BindingsComponent.INSTANCE
            );
        }

    @Override
    public void onStartButtonPressed() {
        /*
        DriverControlledCommand driverControlled = new PedroDriverControlled(
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX(),
                false
        );

        driverControlled.schedule();

         */
        //new TurnTo(Angle.fromDeg(90)).schedule();

        /*
        gamepad1a.whenBecomesTrue(Sorter.INSTANCE.ResetFins);
        gamepad1b.whenBecomesTrue(Sorter.INSTANCE.CloseGates);
        gamepad1y.whenBecomesTrue(Sorter.INSTANCE.StartingConfig);
        gamepad1x.whenBecomesTrue(Sorter.INSTANCE.PushLeftBall);
        gamepad1dpadup.whenBecomesTrue(Sorter.INSTANCE.PushMiddleBall);
        gamepad1dpaddown.whenBecomesTrue(Sorter.INSTANCE.PushRightBall);
        gamepad1dpadright.whenBecomesTrue(Sorter.INSTANCE.FunnelFromMiddle);
         */
        Gamepads.gamepad1().dpadLeft().whenBecomesTrue(Intake.INSTANCE.intake);

        /* This is the calculation to get rX with headlock when we actually code the rest of teleOp

        double turnAngle = Camera.INSTANCE.getAngle();
        double rX = controller.calculate(new KineticState(Math.toRadians(turnAngle));

         */
    }

    @Override
    public void onUpdate(){
        BindingManager.update();
    }

    @Override
    public void onStop(){

    }
}


