package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.graphics.Color;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.SubsystemGroup;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.positionable.SetPositions;

@Configurable
public class Sorter extends SubsystemGroup {
    public static final Sorter INSTANCE = new Sorter();
    ColorSensor LeftSensor = hardwareMap.get(ColorSensor.class, "left_sensor");
    ColorSensor MiddleSensor = hardwareMap.get(ColorSensor.class, "middle_sensor");
    ColorSensor RightSensor = hardwareMap.get(ColorSensor.class, "right_sensor");
    private Sorter() {
        super(
                RightFin.INSTANCE,
                LeftFin.INSTANCE,
                RightGate.INSTANCE,
                LeftGate.INSTANCE
        );
    }
    public Command ResetFins = new ParallelGroup(
            RightFin.INSTANCE.rightFinStraight,
            LeftFin.INSTANCE.leftFinStraight
    );
    public Command CloseGates = new ParallelGroup(
            RightGate.INSTANCE.closeRight,
            LeftGate.INSTANCE.closeLeftAndMiddle
    );

    public Command StartingConfig = new ParallelGroup(
            ResetFins,
            CloseGates
    );

    public Command PushLeftBall = new SequentialGroup(
            LeftGate.INSTANCE.openLeft,
            LeftFin.INSTANCE.hitLeftBall.afterTime(0.1),
            LeftGate.INSTANCE.closeLeftAndMiddle.afterTime(0.3)
    );

    public Command PushMiddleBall = new SequentialGroup(
            LeftGate.INSTANCE.openMiddle,
            LeftFin.INSTANCE.hitMiddleBall.afterTime(0.1),
            LeftGate.INSTANCE.closeLeftAndMiddle.afterTime(0.3)
    );

    public Command PushRightBall = new SequentialGroup(
        RightGate.INSTANCE.openRight,
        RightFin.INSTANCE.hitRightBall.afterTime(0.1),
        RightGate.INSTANCE.closeRight.afterTime(0.3)
    );

    public Command FunnelFromMiddle = new ParallelGroup(
        RightFin.INSTANCE.redirectFromMiddle,
        LeftFin.INSTANCE.redirectFromMiddle
    );

    @Override
    public void periodic() {

    }


}