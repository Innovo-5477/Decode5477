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
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "Sample NextFTC Pedro Autonomous")
public class sampleNextPedroAuto extends NextFTCOpMode {

    public sampleNextPedroAuto() {
        addComponents(
                new PedroComponent(Constants::createFollower),
                BulkReadComponent.INSTANCE
        );
    }
    Pose startPose =  new Pose(8, 8, Math.toRadians(90));
    Pose shootPose = new Pose(8, 31, Math.toRadians(135));
    PathChain ScorePreload;
    public void buildPaths(){
        ScorePreload = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();
    }
    public Command run() {
        return new SequentialGroup(
                new FollowPath(ScorePreload),
                new Delay(.2)
        );
    }

    @Override
    public void onInit(){
        PedroComponent.follower().setStartingPose(startPose);
        buildPaths();
    }

    @Override
    public void onWaitForStart(){
        //scan obelisk if necessary
    }

    @Override
    public void onStartButtonPressed(){
        run().schedule();
    }
}