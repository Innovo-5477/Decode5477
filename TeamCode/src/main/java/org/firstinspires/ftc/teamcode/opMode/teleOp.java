package org.firstinspires.ftc.teamcode.opMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.Sorter;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.bindings.Button;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.core.units.Angle;
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
@TeleOp(name = "teleOp")
public class teleOp extends NextFTCOpMode {
    public teleOp() {
            addComponents(
                    new SubsystemComponent(Intake.INSTANCE, Sorter.INSTANCE),
                    BulkReadComponent.INSTANCE,
                    BindingsComponent.INSTANCE
            );
        }
    Button gamepad1a = button(() -> gamepad1.a);
    Button gamepad1b = button(() -> gamepad1.b);
    Button gamepad1y = button(() -> gamepad1.y);
    Button gamepad1x = button(() -> gamepad1.x);
    Button gamepad1dpadup = button(() -> gamepad1.dpad_up);
    Button gamepad1dpaddown = button(() -> gamepad1.dpad_down);
    Button gamepad1dpadright = button(() -> gamepad1.dpad_right);
    Button gamepad1dpadleft = button(() -> gamepad1.dpad_left);

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
        Sorter.INSTANCE.StartingConfig.schedule();
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
        gamepad1dpadleft.whenBecomesTrue(Intake.INSTANCE.intake());
    }

    @Override
    public void onUpdate(){
        BindingManager.update();
    }

    @Override
    public void onStop(){

    }
}


