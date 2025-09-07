package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.opmodecontrol.ActiveOpMode;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.commands.Command;

import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

@Configurable
public class Intake implements Subsystem {
    public static final Intake INSTANCE = new Intake();
    private Intake() { }
    private MotorEx intake_motor = new MotorEx("intake");

    public static double p = 0.1, i= 0, d = 0;
    public static PIDCoefficients coefficients = new PIDCoefficients(p, i, d);

    private ControlSystem controller = ControlSystem.builder()
            .velPid(coefficients)
            .build();

    public Command stopSpinning = new RunToVelocity(controller, 0).requires(this);
    public Command intake = new RunToVelocity(controller, 1000).requires(this);
    public Command spinOut = new RunToVelocity(controller, -1000).requires(this);

    @Override
    public void initialize() {
    }

    @Override
    public void periodic() {
        intake_motor.setPower(controller.calculate(intake_motor.getState()));
        coefficients.kP = p;
        coefficients.kI = i;
        coefficients.kD = d;
    }
}