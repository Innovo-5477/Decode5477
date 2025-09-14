package org.firstinspires.ftc.teamcode.opMode;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import static dev.nextftc.extensions.pedro.PedroComponent.follower;
import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;

public class blueAuto extends NextFTCOpMode {
    {
        addComponents(new PedroComponent(Constants::createFollower));
    }
    bluePoses poseClass = new bluePoses();
    public List<Pose> Poses = poseClass.getBluePoses();

    //TODO: Motif Variable
    Pose startPose = Poses.get(0);
    Pose shootPose = Poses.get(1);
    PathChain ScorePreload;
    public void buildPaths(){
         ScorePreload = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();
    }
    public Command run = new SequentialGroup(
                new FollowPath(ScorePreload)
    );

    @Override
    public void onInit(){
        buildPaths();
        follower().setStartingPose(startPose);
    }

    @Override
    public void onWaitForStart(){
        //tom scan to update motif variable
        //color sensor scan for preloads
    }

    @Override
    public void onStartButtonPressed(){
        //TODO: Cancel the tom apriltag command
        //CommandManager.INSTANCE.cancelCommand(c);
        run.schedule();
    }
}
