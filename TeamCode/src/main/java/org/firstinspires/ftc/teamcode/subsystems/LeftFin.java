package org.firstinspires.ftc.teamcode.subsystems;

import androidx.core.view.ContentInfoCompat;

import com.bylazar.configurables.annotations.Configurable;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

@Configurable
public class LeftFin implements Subsystem {
    public static final LeftFin INSTANCE = new LeftFin();
    private LeftFin() { }

    private ServoEx left_fin = new ServoEx("left_fin");

    public static double straight = 0.5;
    public static double redirect_awayfrom_left = 0.2;
    public static double redirect_awayfrom_middle = 0.2;
    public static double left_fin_close = 0.1;
    public static double hit_left_ball = 0;
    public static double hit_middle_ball = 1;

    public Command leftFinStraight = new SetPosition(left_fin, straight).requires(this);

    public Command redirectFromLeft = new SetPosition(left_fin, redirect_awayfrom_left).requires(this);

    public Command hitLeftBall = new SetPosition(left_fin, hit_left_ball).requires(this);

    public Command closeLeftFin = new SetPosition(left_fin, left_fin_close).requires(this);

    public Command redirectFromMiddle = new SetPosition(left_fin, redirect_awayfrom_middle).requires(this);

    public Command hitMiddleBall = new SetPosition(left_fin, hit_middle_ball).requires(this);

}