package org.firstinspires.ftc.teamcode.opMode;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathPoint;

import java.util.ArrayList;
import java.util.List;

@Configurable
public class bluePoses {
    public List<Pose> Poses = new ArrayList<>();

    public bluePoses() {
        Pose StartPose = new Pose(62.87, 105.01, Math.toRadians(90));
        Poses.add(StartPose);
    }


    public List<Pose> getBluePoses() {
        return Poses;
    }
    public List<Pose> getRedPoses() {
        //implement a loop that mirrors all poses
        return Poses;
    }

}
