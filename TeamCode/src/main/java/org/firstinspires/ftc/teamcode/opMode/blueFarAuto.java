package org.firstinspires.ftc.teamcode.opMode;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.ftc.FTCCoordinates;
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

@Autonomous(name = "Blue Far Auto")
public class blueFarAuto extends NextFTCOpMode {

    public blueFarAuto() {
        addComponents(
                new PedroComponent(Constants::createFollower),
                new SubsystemComponent(
                        Flywheel.INSTANCE,
                        Loader.INSTANCE
                ),
                BulkReadComponent.INSTANCE
        );
    }
    Pose startPose =  new Pose(55.88732394366197, 7.887323943661974, Math.toRadians(90));
    Pose shootPose = new Pose(59.49295774647888, 20.732394366197177, Math.toRadians(115));
    Pose endPose = new Pose(59.49295774647888, 34.92957746478872, Math.toRadians(90));
    PathChain ScorePreload;
    PathChain Leave;
    public void buildPaths(){
        ScorePreload = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(startPose, shootPose))
                .setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading())
                .build();
        Leave = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(shootPose, endPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), endPose.getHeading())
                .build();
    }
    public Command run() {
        return new SequentialGroup(
                Flywheel.INSTANCE.shootingVelocity(()->1330),
                new Delay(0.2),
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
                new FollowPath(Leave),
                new Delay(1),
                Loader.INSTANCE.load_ball
        );
    }

    @Override
    public void onInit(){
        PedroComponent.follower().setStartingPose(startPose);
        Loader.INSTANCE.load_ball.schedule();
        Flywheel.INSTANCE.veloc_targ = 0;
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

    @Override
    public void onStop(){
        Pose end = PedroComponent.follower().getPose();
        end = end.minus(new Pose(72, 72));
        end = end.rotate(Math.PI / 2, true);
        teleOpp.end = end;
        teleOpp.a = teleOpp.alliance.BLUE;
        teleOpp.auto_happened = true;
    }
}