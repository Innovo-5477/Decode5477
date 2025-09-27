package org.firstinspires.ftc.teamcode.opMode;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "Blue auto pls be wac")
public class blueAuto extends NextFTCOpMode {
    {
        addComponents(
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE
        );
    }
    bluePoses poseClass = new bluePoses();
    public List<Pose> Poses = poseClass.getBluePoses();

    //TODO: Motif Variable
    Pose startPose = Poses.get(0);
    Pose shootPose = Poses.get(1);
    PathChain ScorePreload;
    public void buildPaths(){
         ScorePreload = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();
    }
    public Command run = new SequentialGroup(
        new FollowPath(ScorePreload)
    );

    @Override
    public void onInit(){
        PedroComponent.follower().setStartingPose(startPose);
        buildPaths();
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
