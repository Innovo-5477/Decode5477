package org.firstinspires.ftc.teamcode.subsystems;

import com.bylazar.configurables.annotations.Configurable;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;
import org.firstinspires.ftc.robotcore.internal.hardware.android.GpioPin;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

@Configurable
public class Camera implements Subsystem {

    public static final Camera INSTANCE = new Camera();
    private Camera() {}
    private AprilTagProcessor aprilTag;
    private VisionPortal visionPortal;
    double angle = 0;
    double heading = 0;
    double distance2D = 0;

    @Override
    public void initialize() {
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();
        visionPortal = VisionPortal.easyCreateWithDefaults(
                ActiveOpMode.hardwareMap().get(WebcamName.class, "Webcam 1"), aprilTag);
    }
    @Override
    public void periodic() {
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();
        ActiveOpMode.telemetry().addData("# AprilTags Detected", currentDetections.size());
        for (AprilTagDetection detection : currentDetections) {
            if (detection.metadata != null) {
                ActiveOpMode.telemetry().addLine(String.format("\n==== (ID %d) %s", detection.id, detection.metadata.name));
                ActiveOpMode.telemetry().addLine(String.format("XYZ %6.1f %6.1f %6.1f  (inch)", detection.ftcPose.x, detection.ftcPose.y, detection.ftcPose.z));
                ActiveOpMode.telemetry().addLine(String.format("PRY %6.1f %6.1f %6.1f  (deg)", detection.ftcPose.pitch, detection.ftcPose.roll, detection.ftcPose.yaw));
                ActiveOpMode.telemetry().addLine(String.format("RBE %6.1f %6.1f %6.1f  (inch, deg, deg)", detection.ftcPose.range, detection.ftcPose.bearing, detection.ftcPose.elevation));
                distance2D = Math.sqrt(Math.pow(detection.ftcPose.x,2) + Math.pow(detection.ftcPose.y,2));
                ActiveOpMode.telemetry().addData("Distance:", distance2D);

            } else {
                ActiveOpMode.telemetry().addLine(String.format("\n==== (ID %d) Unknown", detection.id));
                ActiveOpMode.telemetry().addLine(String.format("Center %6.0f %6.0f   (pixels)", detection.center.x, detection.center.y));
            }
        }   // end for() loop

        // Add "key" information to telemetry
        ActiveOpMode.telemetry().addLine("\nkey:\nXYZ = X (Right), Y (Forward), Z (Up) dist.");
        ActiveOpMode.telemetry().addLine("PRY = Pitch, Roll & Yaw (XYZ Rotation)");
        ActiveOpMode.telemetry().addLine("RBE = Range, Bearing & Elevation");
        ActiveOpMode.telemetry().update();


    }


}
