package org.firstinspires.ftc.teamcode.pancake;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedRGBA;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

@Configurable
public class ColorSensors implements Subsystem {
    public static final ColorSensors INSTANCE = new ColorSensors();
    private ColorSensors() {}
    NormalizedColorSensor LeftSensor;
    NormalizedColorSensor MiddleSensor;
    NormalizedColorSensor RightSensor;
    String outputLeft = "N";
    String outputMiddle = "N";
    String outputRight = "N";
    String [] outputs = {outputLeft, outputMiddle, outputRight};
    int [] minPurple = {0,0,0};
    int [] maxPurple = {255, 255, 255};
    int loopLeft = 0;
    int loopMiddle = 0;
    int loopRight = 0;
    int loop = 0;
    int [] minGreen = {0,0,0};
    int [] maxGreen = {255, 255, 255};
    public Command allSensors = new LambdaCommand()
            .setStart(() -> {
                outputLeft = "N";
                outputMiddle = "N";
                outputRight = "N";
                loop = 0;
            })
            .setUpdate(() -> {
                NormalizedRGBA colorsLeft = LeftSensor.getNormalizedColors();
                double red = colorsLeft.red;
                double green = colorsLeft.green;
                double blue = colorsLeft.blue;

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
                else if(isGreen) {
                    outputLeft = "G";
                }
                else {
                    outputLeft = "N";
                }

                NormalizedRGBA colorsMiddle = MiddleSensor.getNormalizedColors();
                red = colorsMiddle.red;
                green = colorsMiddle.green;
                blue = colorsMiddle.blue;

                purpleRed = minPurple[0] < red && red < maxPurple[0];
                purpleGreen = minPurple[1] < green && green < maxPurple[1];
                purpleBlue = minPurple[2] < blue && blue < maxPurple[2];
                isPurple = purpleRed && purpleGreen && purpleBlue;

                greenRed = minGreen[0] < red && red < maxGreen[0];
                greenGreen = minGreen[1] < green && green < maxGreen[1];
                greenBlue = minGreen[2] < blue && blue < maxGreen[2];
                isGreen = greenRed && greenGreen && greenBlue;

                if (isPurple) {
                    outputMiddle = "P";
                }
                else if(isGreen) {
                    outputMiddle = "G";
                }
                else {
                    outputMiddle = "N";
                }

                NormalizedRGBA colorsRight = MiddleSensor.getNormalizedColors();
                red = colorsRight.red;
                green = colorsRight.green;
                blue = colorsRight.blue;

                purpleRed = minPurple[0] < red && red < maxPurple[0];
                purpleGreen = minPurple[1] < green && green < maxPurple[1];
                purpleBlue = minPurple[2] < blue && blue < maxPurple[2];
                isPurple = purpleRed && purpleGreen && purpleBlue;

                greenRed = minGreen[0] < red && red < maxGreen[0];
                greenGreen = minGreen[1] < green && green < maxGreen[1];
                greenBlue = minGreen[2] < blue && blue < maxGreen[2];
                isGreen = greenRed && greenGreen && greenBlue;

                if (isPurple) {
                    outputRight = "P";
                }
                else if(isGreen) {
                    outputRight = "G";
                }
                else {
                    outputRight = "N";
                }

                loop++;
            })
            .setStop(interrupted -> {

            })
            .setIsDone(() -> ((!(outputLeft.equals("N")) && !(outputMiddle.equals("N")) && !(outputRight.equals("N"))) || loop == 80))
            .requires(LeftSensor)
            .setInterruptible(true)
            .setStop(interrupted -> {
                LeftSensor.close();
                MiddleSensor.close();
                RightSensor.close();
            });

    public String [] getAllSensors() {
        allSensors.schedule();
        outputs[0] = outputLeft;
        outputs[1] = outputMiddle;
        outputs[2] = outputRight;
        return outputs;
    }

    @Override
    public void initialize() {
        LeftSensor = ActiveOpMode.hardwareMap().get(NormalizedColorSensor.class, "left_sensor");
        RightSensor = ActiveOpMode.hardwareMap().get(NormalizedColorSensor.class, "right_sensor");
        MiddleSensor = ActiveOpMode.hardwareMap().get(NormalizedColorSensor.class, "middle_sensor");
    }
}
