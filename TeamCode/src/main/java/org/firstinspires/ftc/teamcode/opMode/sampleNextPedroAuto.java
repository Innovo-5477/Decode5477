package org.firstinspires.ftc.teamcode.opMode;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.PathChain;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.subsystems.Loader;

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
                new SubsystemComponent(
                        Flywheel.INSTANCE,
                        Loader.INSTANCE
                ),
                BulkReadComponent.INSTANCE
        );
    }
    Pose startPose =  new Pose(33.57746478873239, 135.211, Math.toRadians(90));
    Pose shootPose = new Pose(47.774647887323944, 95.54929577464789, Math.toRadians(135));
    Pose endPose = new Pose(23.2112676056338, 95.77464788732395, Math.toRadians(90));

    PathChain ScorePreload;
    PathChain Leave;

    public void buildPaths(){
        ScorePreload = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();
        Leave = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(shootPose, endPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();
    }
    public Command run() {
        return new SequentialGroup(
                Flywheel.INSTANCE.shootingVelocity(()->900),
                new FollowPath(ScorePreload),
                new Delay(1),
                Loader.INSTANCE.load_ball,
                new Delay(1),
                Loader.INSTANCE.reset_loader,
                new Delay(1),
                Loader.INSTANCE.load_ball,
                new Delay(1),
                Loader.INSTANCE.reset_loader,
                new Delay(1),
                Loader.INSTANCE.load_ball,
                new Delay(1),
                Loader.INSTANCE.reset_loader,
                new Delay(1),
                new FollowPath(Leave)
        );
    }

    @Override
    public void onInit(){
        PedroComponent.follower().setStartingPose(startPose);
        Loader.INSTANCE.reset_loader.schedule();
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