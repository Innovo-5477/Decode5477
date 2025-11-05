package org.firstinspires.ftc.teamcode.pancake;

import com.bylazar.configurables.annotations.Configurable;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.ServoGroup;

@Configurable
public class RightSorter implements Subsystem {
    public static final RightSorter INSTANCE = new RightSorter();
    private RightSorter() { }

    ServoGroup right_sorters = new ServoGroup(new ServoEx("left_middle"), new ServoEx("left_front"));
    public Command runRightSide; //TODO: figure this out
    public Command stopRightSide; //TODO: figure this out

}