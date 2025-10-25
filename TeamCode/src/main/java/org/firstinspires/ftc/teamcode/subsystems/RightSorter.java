package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.ServoGroup;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.positionable.SetPositions;
import dev.nextftc.hardware.powerable.Powerable;
import dev.nextftc.hardware.powerable.SetPower;

@Configurable
public class RightSorter implements Subsystem {
    public static final RightSorter INSTANCE = new RightSorter();
    private RightSorter() { }

    ServoGroup right_sorters = new ServoGroup(new ServoEx("left_middle"), new ServoEx("left_front"));
    public Command runRightSide; //TODO: figure this out
    public Command stopRightSide; //TODO: figure this out

}