package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.opmodes.autonomous.Globals;

/**
 * Created by pieperm on 5/18/17.
 */
@TeleOp(name = "TankBotTeleOp", group = OpModeGroups.TESTING)
public class TankBotTeleOp extends LinearOpMode {

    private CRServo leftDriveServo, rightDriveServo;
    private Servo angleAdjustServo, rotateTopServo;
    private ListenableGamepad gamepad;

    @Override
    public void runOpMode() throws InterruptedException {

        leftDriveServo = hardwareMap.crservo.get("left-drive-servo");
        rightDriveServo = hardwareMap.crservo.get("right-drive-servo");
        angleAdjustServo = hardwareMap.servo.get("angle-adjust-servo");
        rotateTopServo = hardwareMap.servo.get("rotate-top-servo");

        while(opModeIsActive()) {

            gamepad.update(new GamepadData(gamepad1));

            double leftPower = Range.clip(gamepad1.left_stick_y * FastMath.abs(gamepad1.left_stick_y), -Globals.MAX_MOTOR_POWER, Globals.MAX_MOTOR_POWER);
            double rightPower = Range.clip(gamepad1.right_stick_y * FastMath.abs(gamepad1.right_stick_y), -Globals.MAX_MOTOR_POWER, Globals.MAX_MOTOR_POWER);

            leftDriveServo.setPower(leftPower);
            rightDriveServo.setPower(rightPower);

            idle();

        }

        gamepad.getButton(GamepadData.Button.DPAD_DOWN).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                angleAdjustServo.setPosition(0);
            }
        });

        gamepad.getButton(GamepadData.Button.DPAD_UP).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                angleAdjustServo.setPosition(1);
            }
        });

        gamepad.getButton(GamepadData.Button.LEFT_BUMPER).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                rotateTopServo.setPosition(0);
            }
        });

        gamepad.getButton(GamepadData.Button.RIGHT_BUMPER).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                rotateTopServo.setPosition(1);
            }
        });

    }
}
