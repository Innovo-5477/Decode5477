package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.ServoEx;
import dev.nextftc.hardware.positionable.SetPosition;

@Configurable
public class RightFin implements Subsystem {
    public static final RightFin INSTANCE = new RightFin();
    private RightFin() { }
    private ServoEx right_fin = new ServoEx("right_fin");

    public static double straight = 0.5;
    public static double redirect_awayfrom_right = 0.2;
    public static double redirect_awayfrom_middle = 0.15;
    public static double right_fin_close = 0.1;
    public static double hit_right_ball = 0;
    public static double hit_middle_ball = 1;

    public Command rightFinStraight = new SetPosition(right_fin, straight).requires(this);

    public Command redirectFromRight= new SetPosition(right_fin, redirect_awayfrom_right).requires(this);

    public Command hitRightBall = new SetPosition(right_fin, hit_right_ball).requires(this);

    public Command closeRightFin = new SetPosition(right_fin, right_fin_close).requires(this);

    public Command redirectFromMiddle = new SetPosition(right_fin, redirect_awayfrom_middle).requires(this);

}