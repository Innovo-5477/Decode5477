package org.firstinspires.ftc.teamcode.opMode;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.BezierCurve;
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

@Autonomous(name = "Red Far Partner Push Auto")
public class redFarPartnerPushAuto extends NextFTCOpMode {
    public redFarPartnerPushAuto() {
        addComponents(
                new PedroComponent(Constants::createFollower),
                new SubsystemComponent(
                        Flywheel.INSTANCE,
                        Loader.INSTANCE
                ),
                BulkReadComponent.INSTANCE
        );
    }
    Pose startPose =  new Pose(79, 8.563380281690137, Math.toRadians(180));
    Pose pushPose = new Pose(99, 11, Math.toRadians(180));
    Pose shootPose = new Pose(99, 100, Math.toRadians(45));
    Pose endPose = new Pose(124, 99, Math.toRadians(90));
    PathChain ScorePreload;
    PathChain Leave;
    public void buildPaths(){
        ScorePreload = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(startPose, pushPose))
                .setConstantHeadingInterpolation(startPose.getHeading())
                .setTimeoutConstraint(500)
                .addPath(new BezierCurve(pushPose, new Pose(66.70422535211267, 62.64788732394366), shootPose))
                .setLinearHeadingInterpolation(pushPose.getHeading(), shootPose.getHeading())
                .build();
        Leave = PedroComponent.follower().pathBuilder()
                .addPath(new BezierLine(shootPose, endPose))
                .setLinearHeadingInterpolation(shootPose.getHeading(), endPose.getHeading())
                .build();
    }
    public Command run() {
        return new SequentialGroup(
                new Delay(0.2),
                new FollowPath(ScorePreload),
                Flywheel.INSTANCE.shootingVelocity(()->MechanismConstants.FLYWHEEL_CLOSE_VEL),
                new Delay(1.5),
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
                Loader.INSTANCE.load_ball,
                Flywheel.INSTANCE.shootingVelocity(()->0)
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