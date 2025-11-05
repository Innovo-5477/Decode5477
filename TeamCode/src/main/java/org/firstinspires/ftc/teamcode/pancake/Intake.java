package org.firstinspires.ftc.teamcode.pancake;

import com.bylazar.configurables.annotations.Configurable;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.commands.Command;

import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.impl.MotorEx;

@Configurable
public class Intake implements Subsystem {
    public static double target = 0;
    public static PIDCoefficients coefficients = new PIDCoefficients(0.005, 0, 0);
    public static BasicFeedforwardParameters ff = new BasicFeedforwardParameters(0.01, 0, 0.2);
    public static final Intake INSTANCE = new Intake();
    private Intake() { }
    MotorGroup intake_motor = new MotorGroup(
            new MotorEx("leftFlywheel"),
            new MotorEx("rightFlywheel").reversed()
    );

    ControlSystem controller = ControlSystem.builder()
            .velPid(coefficients)
            .basicFF(ff)
            .build();

    //TODO: once tuned, copy this method for a specific number
    public Command intakeVelocity = new InstantCommand(
            () -> controller.setGoal(
                    new KineticState(
                            intake_motor.getLeader().getCurrentPosition(),
                            2500
                    )
            )
    );
    public Command zeroVelocity = new InstantCommand(
            () -> controller.setGoal(
                    new KineticState(
                            intake_motor.getLeader().getCurrentPosition(),
                            0
                    )
            )
    );


    @Override
    public void initialize(){
    }
    @Override
    public void periodic() {
        intake_motor.setPower(controller.calculate(
                new KineticState(
                        intake_motor.getLeader().getCurrentPosition(),
                        intake_motor.getLeader().getVelocity()
                )
        ));
        ActiveOpMode.telemetry().addData("Intake velocity: ", intake_motor.getLeader().getVelocity());
        ActiveOpMode.telemetry().addData("Intake target: ", target);
        ActiveOpMode.telemetry().update();
    }
}