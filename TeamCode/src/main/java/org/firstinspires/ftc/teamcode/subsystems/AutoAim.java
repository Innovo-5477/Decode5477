package org.firstinspires.ftc.teamcode.subsystems;


import com.bylazar.configurables.annotations.Configurable;

import org.firstinspires.ftc.teamcode.retired.Tom;

import dev.nextftc.core.subsystems.SubsystemGroup;

@Configurable
public class AutoAim extends SubsystemGroup {
    public static final AutoAim INSTANCE = new AutoAim();
    public boolean toggle = false;
    double goaldist = 0;
    private AutoAim() {
        super(
                Tom.INSTANCE
        );
    }
    public double getDistance (double [] pose, String alliance){
        double x = pose[0];
        double y = pose[1];
        double dx = x+58;
        double dy;
        if (alliance.equals("blue")) {
            dy = y-55;
        } else{
            dy = y+55;
        }
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    double interpolate (double distance){
        //this is where the linerp on distance will go
        return distance * 10;

    }

    public void periodic() {
        //goaldist = distance(Camera.INSTANCE.getPose(), "blue");
        //ActiveOpMode.telemetry().addData("Goal distance", goaldist);
        //ActiveOpMode.telemetry().update();
    }
}

