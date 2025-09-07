package org.firstinspires.ftc.teamcode.opMode;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.subsystems.Intake;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.Gamepads;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;
import dev.nextftc.hardware.driving.FieldCentric;
import dev.nextftc.hardware.driving.MecanumDriverControlled;

import dev.nextftc.hardware.impl.Direction;
import dev.nextftc.hardware.impl.IMUEx;
import dev.nextftc.hardware.impl.MotorEx;

@TeleOp(name = "teleOp")
public class teleOp extends NextFTCOpMode {
    public teleOp() {
            addComponents(
                    new SubsystemComponent(Intake.INSTANCE),
                    BulkReadComponent.INSTANCE,
                    BindingsComponent.INSTANCE
            );
        }
    private final MotorEx frontLeftMotor = new MotorEx("leftFront").brakeMode();
    private final MotorEx frontRightMotor = new MotorEx("rightFront").brakeMode();
    private final MotorEx backLeftMotor = new MotorEx("leftRear").brakeMode();
    private final MotorEx backRightMotor = new MotorEx("rightRear").brakeMode();
    private IMUEx imu = new IMUEx("imu", Direction.UP, Direction.FORWARD).zeroed();

    @Override
    public void onStartButtonPressed() {
        Command driverControlled = new MecanumDriverControlled(
                frontLeftMotor,
                frontRightMotor,
                backLeftMotor,
                backRightMotor,
                Gamepads.gamepad1().leftStickY(),
                Gamepads.gamepad1().leftStickX(),
                Gamepads.gamepad1().rightStickX(),
                new FieldCentric((imu))
        );
        driverControlled.schedule();
    }

}


