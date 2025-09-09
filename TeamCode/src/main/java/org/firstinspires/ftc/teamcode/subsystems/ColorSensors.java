package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import android.graphics.Color;

import com.bylazar.configurables.annotations.Configurable;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.NormalizedColorSensor;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.subsystems.SubsystemGroup;

public class ColorSensors {

    private ColorSensors() {}
    ColorSensor LeftSensor = hardwareMap.get(ColorSensor.class, "left_sensor");
    ColorSensor MiddleSensor = hardwareMap.get(ColorSensor.class, "middle_sensor");
    ColorSensor RightSensor = hardwareMap.get(ColorSensor.class, "right_sensor");

    float hsvValues[] = {0F, 0F, 0F};
    final float values []= hsvValues;

    final double SCALE_FACTOR = 255;

    //final View relativeLayout = ((Activity) hardwareMap.appContext).findViewById(relativeLayoutId);

    public String Left() {


    }

}
