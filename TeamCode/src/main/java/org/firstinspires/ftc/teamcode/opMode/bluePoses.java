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
        Pose ShootPose = new Pose(54.7, 100, Math.toRadians(135));

        Pose ClosePickup = new Pose(23.8873, 89.69, Math.toRadians(135));
        Pose Control1ClosePickup = new Pose(31.32, 103.211);
        Pose Control2ClosePickup = new Pose(23.66, 103.309);

        Pose MiddlePickup = new Pose(23.8873, 89.69, Math.toRadians(135));
        Pose Control1MiddlePickup = new Pose(21.4, 93.3);

        Poses.add(StartPose);
    }

    public List<Pose> getBluePoses() {
        return Poses;
    }
    public List<Pose> getRedPoses() {
        for (Pose pose : Poses) {
            pose.mirror();
        }
        return Poses;
    }

}
