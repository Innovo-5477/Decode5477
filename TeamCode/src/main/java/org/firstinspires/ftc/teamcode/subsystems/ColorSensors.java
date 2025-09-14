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
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.core.subsystems.SubsystemGroup;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToVelocity;

@Configurable
public class ColorSensors implements Subsystem {
    private ColorSensors() {}
    NormalizedColorSensor LeftSensor;
    NormalizedColorSensor MiddleSensor;
    NormalizedColorSensor RightSensor;
    String outputLeft = "N";
    String outputMiddle = "N";
    String outputRight = "N";
    int [] minPurple = {0,0,0};
    int [] maxPurple = {255, 255, 255};
    int loopLeft = 0;
    int loopMiddle = 0;
    int loopRight = 0;
    int [] minGreen = {0,0,0};
    int [] maxGreen = {255, 255, 255};
    public Command leftSensor = new LambdaCommand()
            .setStart(() -> {
                outputLeft = "N";
                loopLeft = 0;
            })
            .setUpdate(() -> {
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
                else if(isGreen) {
                    outputLeft = "G";
                }
                else {
                    outputLeft = "N";
                }
                loopLeft++;
            })
            .setStop(interrupted -> {

            })
            .setIsDone(() -> (!outputLeft.equals("N")|| loopLeft == 20))
            .requires(LeftSensor)
            .setInterruptible(true)
            .setStop(interrupted -> {
                //turn off color sensor
            });

    public Command middleSensor = new LambdaCommand()
            .setStart(() -> {
                outputMiddle = "N";
                loopMiddle = 0;
            })
            .setUpdate(() -> {
                NormalizedRGBA colors = MiddleSensor.getNormalizedColors();
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
                    outputMiddle = "P";
                }
                else if(isGreen) {
                    outputMiddle = "G";
                }
                else {
                    outputMiddle = "N";
                }
                loopMiddle++;

            })
            .setStop(interrupted -> {

            })
            .setIsDone(() -> (!outputMiddle.equals("N") || loopMiddle == 20))
            .requires(MiddleSensor)
            .setInterruptible(true)
            .setStop(interrupted -> {
                //turn off color sensor
            });

    public Command rightSensor = new LambdaCommand()
            .setStart(() -> {
                outputRight = "N";
                loopRight = 0;
            })
            .setUpdate(() -> {
                NormalizedRGBA colors = RightSensor.getNormalizedColors();
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
                    outputRight = "P";
                }
                else if(isGreen) {
                    outputRight = "G";
                }
                else {
                    outputRight = "N";
                }
                loopRight++;
            })
            .setStop(interrupted -> {

            })
            .setIsDone(() -> (!outputRight.equals("N") || loopRight == 20))
            .requires(RightSensor)
            .setInterruptible(true)
            .setStop(interrupted -> {
                //turn off color sensor
            });

    public String getLeft() {
        leftSensor.schedule();
        return outputLeft;
    }
    public String getMiddle() {
        middleSensor.schedule();
        return outputMiddle;
    }
    public String getRight() {
        rightSensor.schedule();
        return outputRight;
    }

    @Override
    public void initialize() {
        LeftSensor = ActiveOpMode.hardwareMap().get(NormalizedColorSensor.class, "left_sensor");
        RightSensor = ActiveOpMode.hardwareMap().get(NormalizedColorSensor.class, "right_sensor");
        MiddleSensor = ActiveOpMode.hardwareMap().get(NormalizedColorSensor.class, "middle_sensor");
    }
}
