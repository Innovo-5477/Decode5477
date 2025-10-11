package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;

import org.firstinspires.ftc.robotcore.internal.hardware.android.GpioPin;

import java.util.function.DoubleSupplier;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforward;
import dev.nextftc.control.feedforward.FeedforwardElement;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.commands.Command;

import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

@Configurable
public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() { }
    private MotorEx intake_motor = new MotorEx("intake");

    public static double target = 0;
    public static PIDCoefficients coefficients = new PIDCoefficients(0.0001, 0, 0);
    public static FeedforwardElement ff = new BasicFeedforward(10);
    ControlSystem controller = ControlSystem.builder()
            .velPid(coefficients)
            .feedforward(ff)
            .build();

    public Command intake = new RunToVelocity(controller, target).requires(this);

    @Override
    public void periodic() {
        intake_motor.setPower(controller.calculate(intake_motor.getState()));
        ActiveOpMode.telemetry().addData("Intake velocity: ", intake_motor.getVelocity());
        ActiveOpMode.telemetry().addData("Intake target: ", target);
        ActiveOpMode.telemetry().update();
    }
}