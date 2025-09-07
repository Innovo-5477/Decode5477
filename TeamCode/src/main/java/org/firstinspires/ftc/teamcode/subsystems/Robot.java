package org.firstinspires.ftc.teamcode.subsystems;
import org.firstinspires.ftc.teamcode.subsystems.Intake;

import dev.nextftc.core.subsystems.SubsystemGroup;

public class Robot extends SubsystemGroup {
    public static final Robot INSTANCE = new Robot();
    private Robot() {
        super(
                Intake.INSTANCE,
                Sorter.INSTANCE,
                Flywheel.INSTANCE
        );
    }
}