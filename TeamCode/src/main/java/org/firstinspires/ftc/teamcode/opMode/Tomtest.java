package org.firstinspires.ftc.teamcode.opMode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.retired.Tom;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;

@Autonomous(name = "Tomathan Testjamin")
public class Tomtest extends NextFTCOpMode {
    public Tomtest() {addComponents(new SubsystemComponent(Tom.INSTANCE));}
    //Limelight3A tom = hardwareMap.get(Limelight3A.class, "tom");
    //Shmuel was here
    //Shmuel 2
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
        String [] pat = Tom.INSTANCE.getObelisk();
        telemetry.addData("First item:", pat[0]);
        telemetry.addData("Second item:", pat[1]);
        telemetry.addData("Third item", pat[2]);
        telemetry.update();

    }

    @Override
    public void onUpdate() {
//        String [] pat = Camera.INSTANCE.getObelisk();
//        telemetry.addData("First item:", pat[0]);
//        telemetry.addData("Second item:", pat[1]);
//        telemetry.addData("Third item", pat[2]);
//        telemetry.addData("stuff", pat[0]);


        boolean [] isValidstuff = Tom.INSTANCE.isValid();
        telemetry.addData("Is it valid: ", isValidstuff[0]);
        telemetry.addData("Is it not null: ", isValidstuff[1]);
        telemetry.update();



    }
}
