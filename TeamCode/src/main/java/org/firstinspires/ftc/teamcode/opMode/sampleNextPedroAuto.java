package org.firstinspires.ftc.teamcode.opMode;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;

@Autonomous(name = "Sample NextFTC Pedro Autonomous")
public class sampleNextPedroAuto extends NextFTCOpMode {
    {
        addComponents(new PedroComponent(Constants::createFollower),
                new SubsystemComponent(Claw.INSTANCE);
    }
    Pose startPose =  new Pose(62.87, 105.01, Math.toRadians(90));
    Pose shootPose = new Pose(54.7, 100, Math.toRadians(135));
    PathChain ScorePreload;
    public void buildPaths(){
        ScorePreload = follower.pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();
    }
    public Command run = new SequentialGroup(
            new FollowPath(ScorePreload),
            new Delay(.2),
            Claw.INSTANCE.open()
    );

    @Override
    public void onInit(){
        follower().setStartingPose(startPose);
        Claw.INSTANCE.close().schedule();
        buildPaths();
    }

    @Override
    public void onWaitForStart(){
        //scan obelisk if necessary
    }

    @Override
    public void onStartButtonPressed(){
        run.schedule();
    }
}
