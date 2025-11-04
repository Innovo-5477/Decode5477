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
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.subsystems.SubsystemGroup;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToVelocity;

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
    boolean endLoop = false;

    //Initializing variables to store pipelines that we use so it's more intuitive
    int obeliskLLIndex = 7;
    int GoalLLIndex = 8;

    boolean [] arr = {false, false};
    Pose3D botPose = new Pose3D(new Position(DistanceUnit.INCH, 0.0, 0.0, 0.0, 0), new YawPitchRollAngles(AngleUnit.DEGREES, 0.0, 0.0, 0.0, 0));

    double[] pose = {0, 0, 0};

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

    public Command goalAngle = new LambdaCommand()
            .setStart(() -> {
                angle = 1e-99;
                tom.pipelineSwitch(GoalLLIndex);
            })
            .setUpdate(() -> {
                LLResult result = tom.getLatestResult();
                if (result != null && result.isValid()) {
                    angle = result.getTx();
                    /*
                    LLResultTypes.FiducialResult fiduicalResult = result.getFiducialResults().get(0);
                    if ((fiduicalResult.getFiducialId() == 20) || (fiduicalResult.getFiducialId() == 24)) {
                        angle = result.getTx();
                    }
                    */
                }
            })
            .setStop(interrupted -> {})
            .setIsDone(() -> (angle != 1e-99)) //Command runs until angle has changed
            .requires(tom)
            .setInterruptible(true)
            .setStop(interrupted -> {
                //turn off color sensor
            });
    public double getAngle() {
        goalAngle.schedule();
        return angle;
    }

    public Command setPose = new LambdaCommand()
            .setStart(() -> {
                botPose = new Pose3D(new Position(DistanceUnit.INCH, 0.0, 0.0, 0.0, 0), new YawPitchRollAngles(AngleUnit.DEGREES, 0.0, 0.0, 0.0, 0));
                endLoop = false;
            })
            .setUpdate(() -> {
                LLResult result = tom.getLatestResult();
                if (result != null && result.isValid()) {
                    botPose = result.getBotpose();
                    endLoop = true;
                }
            })
            .setStop(interrupted -> {})
            .setIsDone(() -> (endLoop)) //Could be a source of error here
            .requires(tom)
            .setInterruptible(true)
            .setStop(interrupted -> {
                //turn off color sensor
            });


    public double[] getPose(){
        setPose.schedule();
        //Idk what these x and y coordinates are relative to, we'll have to see when testing
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
        tom.pipelineSwitch(obeliskLLIndex);
        tom.start();
    }
    @Override
    public void periodic() {
        pose = getPose(); //Are we making it get pose if a toggle is pressed?

        //String [] pat = Camera.INSTANCE.getObelisk();
        //telemetry.addData("Is valid: ", tom.getLatestResult().isValid());
        //telemetry.addData("Exists?: ", tom.getLatestResult() != null);
    }

}
