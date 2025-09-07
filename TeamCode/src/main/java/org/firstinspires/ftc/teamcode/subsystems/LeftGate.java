package org.firstinspires.ftc.teamcode.subsystems;

import androidx.core.view.ContentInfoCompat;

import com.bylazar.configurables.annotations.Configurable;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

@Configurable
public class LeftGate implements Subsystem {
    public static final LeftGate INSTANCE = new LeftGate();
    private LeftGate() { }

    private ServoEx left_gate = new ServoEx("left_gate");

    public static double close_left_and_middle = 0.5;
    public static double open_left = 0.2;
    public static double open_middle = 0.3;
    public Command closeLeftAndMiddle = new SetPosition(left_gate, close_left_and_middle).requires(this);
    public Command openLeft = new SetPosition(left_gate, open_left).requires(this);
    public Command openMiddle = new SetPosition(left_gate, open_middle).requires(this);

}