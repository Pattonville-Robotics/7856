package org.pattonvillerobotics.opmodes.teleop;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

/**
 * Created by pieperm on 5/18/17.
 */
@TeleOp(name = "TankBotTeleOp", group = OpModeGroups.TESTING)
public class TankBotTeleOp extends LinearOpMode {

    private static final String TAG = TankBotTeleOp.class.getSimpleName();

    private CRServo leftDriveServo, rightDriveServo;
    private Servo angleAdjustServo, rotateTopServo;
    private ListenableGamepad gamepad;

    @Override
    public void runOpMode() throws InterruptedException {
        Log.i(TAG, "runOpMode: Initialize starting");

        leftDriveServo = hardwareMap.crservo.get("left-drive-servo");
        rightDriveServo = hardwareMap.crservo.get("right-drive-servo");
        angleAdjustServo = hardwareMap.servo.get("angle-adjust-servo");
        rotateTopServo = hardwareMap.servo.get("rotate-top-servo");
        gamepad = new ListenableGamepad();

        angleAdjustServo.setPosition(.5);
        rotateTopServo.setPosition(.5);

        rightDriveServo.setDirection(DcMotorSimple.Direction.REVERSE);

        gamepad.getButton(GamepadData.Button.DPAD_DOWN).addListener(ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                angleAdjustServo.setPosition(angleAdjustServo.getPosition() - 0.0025);
            }
        });

        gamepad.getButton(GamepadData.Button.DPAD_UP).addListener(ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                angleAdjustServo.setPosition(angleAdjustServo.getPosition() + 0.0025);
            }
        });

        gamepad.getButton(GamepadData.Button.DPAD_LEFT).addListener(ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                rotateTopServo.setPosition(rotateTopServo.getPosition() - 0.005);
            }
        });

        gamepad.getButton(GamepadData.Button.DPAD_RIGHT).addListener(ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                rotateTopServo.setPosition(rotateTopServo.getPosition() + 0.005);
            }
        });
        Log.i(TAG, "runOpMode: Initialize complete");

        waitForStart();

        while (opModeIsActive()) {
            Log.i(TAG, "runOpMode: Looping");
            gamepad.update(new GamepadData(gamepad1));

            double leftPower = Range.clip(gamepad1.left_stick_y * FastMath.abs(gamepad1.left_stick_y), -1, 1);
            double rightPower = Range.clip(gamepad1.right_stick_y * FastMath.abs(gamepad1.right_stick_y), -1, 1);

            leftDriveServo.setPower(leftPower);
            rightDriveServo.setPower(rightPower);
        }
        Log.i(TAG, "runOpMode: Loop complete");
    }
}
