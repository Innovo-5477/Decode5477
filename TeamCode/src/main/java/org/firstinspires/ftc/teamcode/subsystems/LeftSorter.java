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
public class LeftSorter implements Subsystem {
    public static final LeftSorter INSTANCE = new LeftSorter();
    private LeftSorter() { }

    ServoGroup left_sorters = new ServoGroup(new ServoEx("left_middle"), new ServoEx("left_front"));
    public Command runLeftSide; //TODO: figure this out
    public Command stopLeftSide; //TODO: figure this out


}