package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.ServoGroup;
import dev.nextftc.hardware.positionable.SetPosition;
import dev.nextftc.hardware.positionable.SetPositions;
import dev.nextftc.hardware.powerable.Powerable;
import dev.nextftc.hardware.powerable.SetPower;

@Configurable
public class Loader implements Subsystem {
    public static final Loader INSTANCE = new Loader();
    private Loader() { }

    ServoEx loader = new ServoEx("loader");
    public Command load_ball = new SetPosition(loader, .52).requires(this);
    public Command reset_loader = new SetPosition(loader, .35).requires(this);
    @Override
    public void periodic() {
        //ActiveOpMode.telemetry().addData("Loader position: ", loader.getPosition());
        //ActiveOpMode.telemetry().update();
    }
}