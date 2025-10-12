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
public class Intake implements Subsystem {
    public static double target = 0;
    public static PIDCoefficients coefficients = new PIDCoefficients(0.0005, 0, 0);
    public static BasicFeedforwardParameters ff = new BasicFeedforwardParameters(0.0002, 0, 0.3);
    public static final Intake INSTANCE = new Intake();
    private Intake() { }
    private MotorEx intake_motor = new MotorEx("intake");


    ControlSystem controller = ControlSystem.builder()
            .velPid(coefficients)
            .basicFF(ff)
            .build();

    public Command intake = new InstantCommand(
            () -> controller.setGoal(
                    new KineticState(
                            intake_motor.getCurrentPosition(),
                            target
                    )
            )
    );

    @Override
    public void initialize(){
        intake_motor.zeroed();
    }
    @Override
    public void periodic() {
        intake_motor.setPower(controller.calculate(
                new KineticState(
                        intake_motor.getCurrentPosition(),
                        intake_motor.getVelocity()
                )
        ));
        ActiveOpMode.telemetry().addData("Intake velocity: ", intake_motor.getVelocity());
        ActiveOpMode.telemetry().addData("Intake target: ", target);
        ActiveOpMode.telemetry().update();
    }
}