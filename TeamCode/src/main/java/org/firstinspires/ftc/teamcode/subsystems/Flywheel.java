package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;
import com.bylazar.telemetry.PanelsTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.internal.hardware.android.GpioPin;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.control.feedback.PIDCoefficients;
import dev.nextftc.control.feedforward.BasicFeedforward;
import dev.nextftc.control.feedforward.BasicFeedforwardParameters;
import dev.nextftc.control.feedforward.FeedforwardElement;
import dev.nextftc.control.filters.LowPassFilter;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.commands.Command;

import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

@Configurable
public class Flywheel implements Subsystem {
    public static PIDCoefficients Lcoefficients = new PIDCoefficients(0.006, 0, 0);
    public static PIDCoefficients Rcoefficients = new PIDCoefficients(0.006, 0, 0);

    public static BasicFeedforwardParameters Lff = new BasicFeedforwardParameters(0, 0, 0);
    public static BasicFeedforwardParameters Rff = new BasicFeedforwardParameters(0, 0, 0);

    public static final Flywheel INSTANCE = new Flywheel();
    public double veloc_targ = 900;
    private Flywheel() { }

    public MotorEx l = new MotorEx("leftFlywheel");
    public MotorEx r = new MotorEx("rightFlywheel");

    ControlSystem leftController = ControlSystem.builder()
            .velPid(Lcoefficients)
            .basicFF(Lff)
            .build();

    ControlSystem rightController = ControlSystem.builder()
            .velPid(Rcoefficients)
            .basicFF(Rff)
            .build();

    public Command shootingVelocity(DoubleSupplier setPointSupplier) {
        return new InstantCommand(() -> {
            double setPoint = setPointSupplier.getAsDouble(); // Get value when executed
            leftController.setGoal(new KineticState(l.getCurrentPosition(), setPoint));
            rightController.setGoal(new KineticState(r.getCurrentPosition(), -setPoint));
        });
    }

    @Override
    public void periodic() {
        if (ActiveOpMode.opModeIsActive()) {

            l.setPower(leftController.calculate(new KineticState(
                    l.getCurrentPosition(), l.getVelocity()
            )));

            r.setPower(rightController.calculate(new KineticState(
                    r.getCurrentPosition(), r.getVelocity()
            )));
        }
        ActiveOpMode.telemetry().addData("Flywheel target", veloc_targ);
        ActiveOpMode.telemetry().addData("Left velocity", l.getVelocity());
        ActiveOpMode.telemetry().addData("Right velocity", r.getVelocity());
    }

}
