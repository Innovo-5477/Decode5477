package org.firstinspires.ftc.teamcode.subsystems;

import androidx.core.view.ContentInfoCompat;

import com.bylazar.configurables.annotations.Configurable;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

@Configurable
public class RightGate implements Subsystem {
    public static final RightGate INSTANCE = new RightGate();
    private RightGate() { }

    private ServoEx right_gate = new ServoEx("right_gate");
    public static double close_right = 0.5;
    public static double open_right = 0.3;
    public Command closeRight = new SetPosition(right_gate, close_right).requires(this);
    public Command openRight = new SetPosition(right_gate, open_right).requires(this);

}