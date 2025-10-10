package org.firstinspires.ftc.teamcode.opMode;

import static org.firstinspires.ftc.teamcode.pedroPathing.Tuning.follower;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.pedropathing.paths.PathChain;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.pedroPathing.Constants;
import org.firstinspires.ftc.teamcode.subsystems.Camera;
import org.firstinspires.ftc.teamcode.subsystems.ColorSensors;
import org.firstinspires.ftc.teamcode.subsystems.Flywheel;
import org.firstinspires.ftc.teamcode.subsystems.Intake;
import org.firstinspires.ftc.teamcode.subsystems.LeftFin;
import org.firstinspires.ftc.teamcode.subsystems.LeftGate;
import org.firstinspires.ftc.teamcode.subsystems.RightFin;
import org.firstinspires.ftc.teamcode.subsystems.RightGate;
import org.firstinspires.ftc.teamcode.subsystems.Robot;
import org.firstinspires.ftc.teamcode.subsystems.Sorter;

import static dev.nextftc.extensions.pedro.PedroComponent.follower;
import java.util.List;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.CommandManager;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.extensions.pedro.FollowPath;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "Tomathan Testjamin")
public class Tomtest extends NextFTCOpMode {
    public Tomtest() {addComponents(new SubsystemComponent(Camera.INSTANCE));}
    //Limelight3A tom = hardwareMap.get(Limelight3A.class, "tom");
    //Shmuel was here
    @Override
    public void onInit(){

    }

    @Override
    public void onWaitForStart(){
        //tom scan to update motif variable
        //color sensor scan for preloads
    }

    @Override
    public void onStartButtonPressed(){
        //TODO: Cancel the tom apriltag command
        //CommandManager.INSTANCE.cancelCommand(c);
        String [] pat = Camera.INSTANCE.getObelisk();
        telemetry.addData("First item:", pat[0]);
        telemetry.addData("Second item:", pat[1]);
        telemetry.addData("Third item", pat[2]);
        telemetry.update();

    }

    @Override
    public void onUpdate() {
        String [] pat = Camera.INSTANCE.getObelisk();
        telemetry.addData("First item:", pat[0]);
        telemetry.addData("Second item:", pat[1]);
        telemetry.addData("Third item", pat[2]);
        telemetry.addData("stuff", pat[0]);
        boolean [] isValidstuff = Camera.INSTANCE.isValid();
        telemetry.addData("Is it valid: ", isValidstuff[0]);
        telemetry.addData("Is it not null: ", isValidstuff[1]);
        telemetry.update();
    }
}
