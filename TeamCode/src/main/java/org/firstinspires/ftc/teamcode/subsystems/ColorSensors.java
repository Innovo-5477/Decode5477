package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.graphics.Color;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.SubsystemGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;

public class ColorSensors {

    private ColorSensors() {}
    NormalizedColorSensor LeftSensor = hardwareMap.get(NormalizedColorSensor.class, "left_sensor");
    NormalizedColorSensor MiddleSensor = hardwareMap.get(NormalizedColorSensor.class, "middle_sensor");
    NormalizedColorSensor RightSensor = hardwareMap.get(NormalizedColorSensor.class, "right_sensor");
    String outputLeft = "N";
    public Command leftSensor = new LambdaCommand()
            .setStart(() -> {
                int [] minPurple = {0,0,0};
                int [] maxPurple = {255, 255, 255};

                int [] minGreen = {0,0,0};
                int [] maxGreen = {255, 255, 255};
                outputLeft = "N";
            })
            .setUpdate(() -> {
                int [] minPurple = {0,0,0};
                int [] maxPurple = {255, 255, 255};

                int [] minGreen = {0,0,0};
                int [] maxGreen = {255, 255, 255};
                // YOU NEED TO EDIT ALL OF THESE VALUES LATER
                NormalizedRGBA colors = LeftSensor.getNormalizedColors();
                double red = colors.red;
                double green = colors.green;
                double blue = colors.blue;

                boolean purpleRed = minPurple[0] < red && red < maxPurple[0];
                boolean purpleGreen = minPurple[1] < green && green < maxPurple[1];
                boolean purpleBlue = minPurple[2] < blue && blue < maxPurple[2];
                boolean isPurple = purpleRed && purpleGreen && purpleBlue;


                boolean greenRed = minGreen[0] < red && red < maxGreen[0];
                boolean greenGreen = minGreen[1] < green && green < maxGreen[1];
                boolean greenBlue = minGreen[2] < blue && blue < maxGreen[2];
                boolean isGreen = greenRed && greenGreen && greenBlue;


                if (isPurple) {
                    outputLeft = "P";
                }
                if(isGreen) {
                    outputLeft = "G";
                }
            })
            .setStop(interrupted -> {

            })
            .setIsDone(() -> true)
            .requires()
            .setInterruptible(true)
            .setStop(interrupted -> {

            })
            .setIsDone(() -> true)
            .requires()
            .setInterruptible(true);
}
