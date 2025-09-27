package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.opMode;

import android.graphics.Color;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

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
    String [] gulp = {"gulp"};
    int fiducialID = 0;
    double angle = 0;

    public Command Obelisk = new LambdaCommand()
            .setStart(() -> {
                fiducialID = 0;
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
                angle = 0;
            })
            .setUpdate(() -> {
                LLResult result = tom.getLatestResult();
                if (result != null && result.isValid()) {
                    LLResultTypes.FiducialResult fiduicalResult = result.getFiducialResults().get(0);
                    if ((fiduicalResult.getFiducialId() == 20) || (fiduicalResult.getFiducialId() == 24)) {
                        angle = result.getTx();
                    }
                }
            })
            .setStop(interrupted -> {})
            .setIsDone(() -> (false))
            .requires(tom)
            .setInterruptible(true)
            .setStop(interrupted -> {
                //turn off color sensor
            });
    public double getAngle() {
        goalAngle.schedule();
        return angle;
    }



    @Override
    public void initialize() {
        tom = opMode.hardwareMap.get(Limelight3A.class, "tom");
        //tom = hardwareMap.get(Limelight3A.class, "tom");
        tom.setPollRateHz(90);
        tom.pipelineSwitch(0);
        tom.start();
    }
}
