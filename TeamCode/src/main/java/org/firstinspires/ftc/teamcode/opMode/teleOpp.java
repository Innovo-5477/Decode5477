package org.firstinspires.ftc.teamcode.opMode;
import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.modernrobotics.comm.ReadWriteRunnable;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.LeftSorter;
import org.firstinspires.ftc.teamcode.subsystems.Loader;
import org.firstinspires.ftc.teamcode.subsystems.RightSorter;

import dev.nextftc.bindings.BindingManager;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.PedroDriverControlled;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.hardware.driving.DriverControlledCommand;

@TeleOp(name = "field centric teleop")
@Configurable
public class teleOpp extends NextFTCOpMode {
    public teleOpp() {
            addComponents(
                    new SubsystemComponent(
                            Intake.INSTANCE,
                            Loader.INSTANCE,
                            LeftSorter.INSTANCE,
                            RightSorter.INSTANCE,
                            Flywheel.INSTANCE
                    ),
                    BindingsComponent.INSTANCE
            );
        }
    @Override
    public void onStartButtonPressed() {

        DriverControlledCommand driverControlled = new PedroDriverControlled(
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX(),
                false
        );

        driverControlled.schedule();

        SequentialGroup IntakeStuffOn = new SequentialGroup(
                Intake.INSTANCE.intakeVelocity,
                LeftSorter.INSTANCE.runLeftSide,
                RightSorter.INSTANCE.runRightSide
        );
        SequentialGroup IntakeStuffOff = new SequentialGroup(
                Intake.INSTANCE.zeroVelocity,
                LeftSorter.INSTANCE.stopLeftSide,
                RightSorter.INSTANCE.stopRightSide
        );

        Gamepads.gamepad1().rightTrigger().inRange(0, 1)
                .toggleOnBecomesTrue()
                .whenBecomesTrue(IntakeStuffOn)
                .whenBecomesFalse(IntakeStuffOff);

        Gamepads.gamepad1().leftTrigger().inRange(0, 1)
                .toggleOnBecomesTrue()
                .whenBecomesTrue(Loader.INSTANCE.load_ball)
                .whenBecomesFalse(Loader.INSTANCE.reset_loader);

        //figure out how to make this a loop and give encoder reference
        Gamepads.gamepad1().y().whenBecomesTrue(
               Flywheel.INSTANCE.shootingVelocity
        );


        //double turnAngle = Camera.INSTANCE.getAngle();
        //double rX = controller.calculate(new KineticState(Math.toRadians(turnAngle));

    }

    @Override
    public void onUpdate(){
        BindingManager.update();
    }

    @Override
    public void onStop(){
        BindingManager.reset();
    }
}


