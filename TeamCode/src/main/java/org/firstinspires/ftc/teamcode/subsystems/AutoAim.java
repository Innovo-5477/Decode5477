package org.firstinspires.ftc.teamcode.subsystems;


import com.bylazar.configurables.annotations.Configurable;

import dev.nextftc.core.subsystems.SubsystemGroup;
import dev.nextftc.ftc.ActiveOpMode;

@Configurable
public class AutoAim extends SubsystemGroup {
    public static final AutoAim INSTANCE = new AutoAim();
    public boolean toggle = false;
    double goaldist = 0;
    private AutoAim() {
        super(
                Camera.INSTANCE
        );
    }
    double distance (double [] pose, String alliance){
        double x = pose[0];
        double y = pose[1];
        double dy = 130 - y;
        double dx;
        if (alliance.equals("blue")) {
            dx = 15-x;
        } else{
            dx = 130-x;
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

