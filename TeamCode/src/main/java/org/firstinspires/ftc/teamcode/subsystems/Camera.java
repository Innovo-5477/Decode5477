package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.graphics.Color;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.ftc.FTCCoordinates;
import com.pedropathing.geometry.PedroCoordinates;

import com.pedropathing.geometry.Pose;
import com.pedropathing.math.Vector;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.subsystems.SubsystemGroup;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToVelocity;
import kotlin.time.Instant;

@Configurable
public class Camera implements Subsystem {
    public static final Camera INSTANCE = new Camera();
    private Camera() {}
    private Limelight3A tom;
    String [] array21 = {"G", "P", "P"};
    String [] array22 = {"P", "G", "P"};
    String [] array23 = {"P", "P", "G"};
    String [] gulp = {"G", "U", "LP"};
    int fiducialID = 0;
    double angle = 0;

    //Initializing variables to store pipelines that we use so it's more intuitive
    int obeliskLLIndex = 7;
    int GoalLLIndex = 8;


    boolean [] arr = {false, false};
    Pose3D botPose = new Pose3D(new Position(DistanceUnit.INCH, 0.0, 0.0, 0.0, 0), new YawPitchRollAngles(AngleUnit.DEGREES, 0.0, 0.0, 0.0, 0));
    double[] pose = {0, 0, 0};

    public Command changePipeline(int index) {
        return new InstantCommand (() -> tom.pipelineSwitch(index));
    }

    public Command Obelisk = new LambdaCommand()
            .setStart(() -> {
                fiducialID = 0;
                tom.pipelineSwitch(obeliskLLIndex);
            })
            .setUpdate(() -> {
                LLResult result = tom.getLatestResult();
                if (result != null && result.isValid()) {
                    LLResultTypes.FiducialResult fiduicalResult = result.getFiducialResults().get(0);
                    fiducialID = fiduicalResult.getFiducialId();
                }
            })
            .setStop(interrupted -> {})
            .setIsDone(() -> (fiducialID != 0))
            .requires(tom)
            .setInterruptible(true)
            .setStop(interrupted -> {
                //turn off color sensor
            });


    public String [] getObelisk() {
        Obelisk.schedule();
        if (fiducialID == 21) {
            return array21;
        }
        else if (fiducialID == 22) {
            return array22;
        }
        else if (fiducialID == 23) {
            return array23;
        }
        else { return gulp; }
    }
    public double getAngle() {
        return angle;
    }

    public double[] getPose() {
        pose[0] = metersToInches(botPose.getPosition().x);
        pose[1] = metersToInches(botPose.getPosition().z);
        pose[2] = botPose.getOrientation().getYaw();
        return pose;
    }

    public boolean [] isValid() {
        arr[0] = tom.getLatestResult().isValid();
        arr[1] = tom.getLatestResult() != null;
        return arr;
    }


    @Override
    public void initialize() {
        tom = ActiveOpMode.hardwareMap().get(Limelight3A.class, "tom");
        tom.setPollRateHz(90);
        tom.pipelineSwitch(GoalLLIndex);
        tom.start();
        botPose = new Pose3D(new Position(DistanceUnit.INCH, 0.0, 0.0, 0.0, 0), new YawPitchRollAngles(AngleUnit.DEGREES, 0.0, 0.0, 0.0, 0));
    }

    @Override
    public void periodic() {
        if (ActiveOpMode.opModeIsActive()) {

            /*
            LLResult result = tom.getLatestResult();
            if (result != null && result.isValid()) {
                botPose = result.getBotpose();
                angle = result.getTx();
            }
            pose = getPose();

            ActiveOpMode.telemetry().addData("Pose x: ", pose[0]);
            ActiveOpMode.telemetry().addData("Pose z: ", pose[1]);
            ActiveOpMode.telemetry().addData("Pose Heading: ", pose[2]);
            ActiveOpMode.telemetry().addData("Angle: ", angle);
            ActiveOpMode.telemetry().addData("Full pose: ", botPose);
            ActiveOpMode.telemetry().addData("Blue Distance: ", distance(pose, "blue"));
            ActiveOpMode.telemetry().addData("Red Distance: ", distance(pose, "red"));
            ActiveOpMode.telemetry().update();
            */


            List<LLResultTypes.FiducialResult> r = tom.getLatestResult().getFiducialResults();
            LLResultTypes.FiducialResult target = null;
            for (LLResultTypes.FiducialResult i: r) {
                if (i != null) {
                    target = i;
                    break;
                }
            }
            double baronx = metersToInches(target.getCameraPoseTargetSpace().getPosition().x);
            double baronz = metersToInches(target.getCameraPoseTargetSpace().getPosition().z);
            double distance2D = Math.sqrt(Math.pow(baronx, 2) + Math.pow(baronz, 2));
            Vector e = new Vector();
            e.setOrthogonalComponents(baronx, baronz);
            double mag = e.getMagnitude();
            ActiveOpMode.telemetry().addData("Baron x: ", baronx);
            ActiveOpMode.telemetry().addData("Baron z: ", baronz);
            ActiveOpMode.telemetry().addData("Baron distance: ", distance2D);
            ActiveOpMode.telemetry().update();
        }


        //String [] pat = Camera.INSTANCE.getObelisk();
        //telemetry.addData("Is valid: ", tom.getLatestResult().isValid());
        //telemetry.addData("Exists?: ", tom.getLatestResult() != null);
    }

    public double metersToInches(double meters) {
        return meters/0.0254;
    }
    double distance (double [] pose, String alliance){
        double x = pose[0];
        double y = pose[1];
        double dy;
        double dx;
        if (alliance.equals("blue")) {
            dx = -58.35-x;
            dy = -55.6-y;
        } else{
            dx = -58.35-x;
            dy = 55.6-y;
        }
        return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }


}
