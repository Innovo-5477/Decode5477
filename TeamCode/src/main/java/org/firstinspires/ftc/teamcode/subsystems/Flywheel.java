package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

@Configurable
public class Flywheel implements Subsystem {
    //public static double target = 0;
    public static PIDCoefficients coefficients = new PIDCoefficients(0.006, 0, 0.001);
    public static BasicFeedforwardParameters ff = new BasicFeedforwardParameters(0, 0, 0.01);
    public static final Flywheel INSTANCE = new Flywheel();
    public double veloc_targ = 900;

    private Flywheel() { }
    MotorGroup flywheel_motor = new MotorGroup(
            new MotorEx("leftFlywheel"),
            new MotorEx("rightFlywheel").reversed()
    );

    ControlSystem controller = ControlSystem.builder()
            .velPid(coefficients)
            .basicFF(ff)
            .build();

    public Command shootingVelocity(double setPoint) {
        return new InstantCommand(
                () -> controller.setGoal(
                        new KineticState(
                                flywheel_motor.getLeader().getCurrentPosition(),
                                setPoint
                        )
                )
        );
    }

    public Command shoottoVelTarget() {
        return new InstantCommand(
                () -> controller.setGoal(
                        new KineticState(
                                flywheel_motor.getLeader().getCurrentPosition(),
                                veloc_targ
                        )
                )
        );
    }

    @Override
    public void initialize(){

    }
    @Override
    public void periodic() {
        if (ActiveOpMode.opModeIsActive()) {
            flywheel_motor.setPower(controller.calculate(
                    new KineticState(
                            flywheel_motor.getLeader().getCurrentPosition(),
                            flywheel_motor.getLeader().getVelocity()
                    )
            ));
        }

        ActiveOpMode.telemetry().addData("Flywheel target: " ,veloc_targ);
        ActiveOpMode.telemetry().addData("Flywheel Leader velocity: ", flywheel_motor.getLeader().getVelocity());
        ActiveOpMode.telemetry().addData("Flywheel Follower velocity: ", flywheel_motor.getFollowers()[0].getVelocity());

        ActiveOpMode.telemetry().update();
    }
}