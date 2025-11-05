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
public class Gate implements Subsystem {
    public static final Gate INSTANCE = new Gate();
    private Gate() { }

    ServoEx gate = new ServoEx("gate");
    public Command open_gate = new SetPosition(gate, .5).requires(this);
    public Command close_gate = new SetPosition(gate, 0).requires(this);

}