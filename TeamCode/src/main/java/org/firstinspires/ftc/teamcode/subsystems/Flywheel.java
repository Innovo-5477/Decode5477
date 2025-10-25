package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;

import org.firstinspires.ftc.robotcore.internal.hardware.android.GpioPin;

import java.util.function.DoubleSupplier;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforward;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
import dev.nextftc.control.feedforward.FeedforwardElement;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.commands.Command;

import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

@Configurable
public class Flywheel implements Subsystem {
    public static double target = 0;
    public static PIDCoefficients coefficients = new PIDCoefficients(0.0005, 0, 0);
    public static BasicFeedforwardParameters ff = new BasicFeedforwardParameters(0.0002, 0, 0.3);
    public static final Flywheel INSTANCE = new Flywheel();
    private Flywheel() { }
    private MotorEx flywheel_motor = new MotorEx("flywheel");


    ControlSystem controller = ControlSystem.builder()
            .velPid(coefficients)
            .basicFF(ff)
            .build();

    //TODO: once tuned, copy this method for a specific number
    public Command shootingVelocity = new InstantCommand(
            () -> controller.setGoal(
                    new KineticState(
                            flywheel_motor.getCurrentPosition(),
                            target
                    )
            )
    );
    public Command zeroVelocity = new InstantCommand(
            () -> controller.setGoal(
                    new KineticState(
                            flywheel_motor.getCurrentPosition(),
                            0
                    )
            )
    );


    @Override
    public void initialize(){
        flywheel_motor.zeroed();
    }
    @Override
    public void periodic() {
        flywheel_motor.setPower(controller.calculate(
                new KineticState(
                        flywheel_motor.getCurrentPosition(),
                        flywheel_motor.getVelocity()
                )
        ));
        ActiveOpMode.telemetry().addData("Flywheel velocity: ", flywheel_motor.getVelocity());
        ActiveOpMode.telemetry().addData("Flywheel target: ", target);
        ActiveOpMode.telemetry().update();
    }
}