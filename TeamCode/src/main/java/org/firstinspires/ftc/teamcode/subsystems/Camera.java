package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import android.graphics.Color;

import com.bylazar.configurables.annotations.Configurable;
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
    boolean valid = false;


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

    public double[] getPose(){
        pose[0] = botPose.getPosition().x;
        pose[1] = botPose.getPosition().z; //I think it's z
        pose[2] = botPose.getOrientation().getYaw(); //I'm pretty sure it's yaw, but I need to verify when we use it
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
        LLResult result = tom.getLatestResult();
        valid = result != null && result.isValid();
        if (valid) {
            botPose = result.getBotpose();
            angle = result.getTx();
        }
        pose = getPose();
        ActiveOpMode.telemetry().addData("Null?", result == null);
        ActiveOpMode.telemetry().addData("Is Valid?", valid); //Calling isValid method could result in null pointer exception, so I'm j using this compound expression instead
        ActiveOpMode.telemetry().addData("Pose x: ", pose[0]);
        ActiveOpMode.telemetry().addData("Pose y: ", pose[1]);
        ActiveOpMode.telemetry().addData("Pose Heading: ", pose[2]);
        ActiveOpMode.telemetry().addData("Angle: ", angle);
        ActiveOpMode.telemetry().update();
        //String [] pat = Camera.INSTANCE.getObelisk();
    }

}
