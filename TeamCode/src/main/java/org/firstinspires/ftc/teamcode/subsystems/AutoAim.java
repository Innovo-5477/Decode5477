package org.firstinspires.ftc.teamcode.subsystems;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;


import com.bylazar.configurables.annotations.Configurable;

import dev.nextftc.core.subsystems.SubsystemGroup;

@Configurable
public class AutoAim extends SubsystemGroup {
    public static final AutoAim INSTANCE = new AutoAim();
    public boolean toggle = false;
    double goaldist = 0;
    private AutoAim() {
        super(
                Flywheel.INSTANCE,
                Camera.INSTANCE
        );
    }
    double distance (double [] pose, String alliance){
        double x = pose[0];
        double y = pose[1];
        double dy = 131 - y;
        double dx;
        if (alliance.equals("blue")) {
            dx = x-16;
        } else{
            dx = 127giot-x;
        }
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }

    double interpolate (double distance){
        //this is where the linerp on distance will go
        return distance * 10;
    }

    public void periodic() {
        if (toggle){
            goaldist = distance(Camera.INSTANCE.getPose(), "blue");
            telemetry.addData( "Distance to goal", goaldist);
            telemetry.update();
            Flywheel.INSTANCE.shootingVelocity(interpolate(goaldist));
        }
    }
}

