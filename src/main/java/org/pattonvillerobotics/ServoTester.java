package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

/**
 * Created by trieud01 on 10/10/17.
 */

@TeleOp(name = "ServoTester", group = OpModeGroups.TESTING)
public class ServoTester extends LinearOpMode {

    private Servo testServo;
    private ListenableGamepad gamepad;


    @Override
    public void runOpMode() throws InterruptedException {

        testServo = hardwareMap.servo.get("test-servo");

        gamepad = new ListenableGamepad();
        gamepad.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                testServo.setPosition(0);
                displayTelemetry(testServo.getPosition());
                telemetry.addData("Servo Position", "Position: " + testServo.getPosition());
            }
        });

        gamepad.getButton(GamepadData.Button.RIGHT_BUMPER).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                testServo.setPosition(1);
                telemetry.addData("Servo Position", "Position: " + testServo.getPosition());
            }
        });

        waitForStart();
        displayTelemetry("Test Servo Init");

        while (opModeIsActive()) {

            gamepad.update(new GamepadData(gamepad1));

            telemetry.update();
            idle();
        }
    }

    public void displayTelemetry(Object message) {
        telemetry.addData("Test Servo", message).setRetained(true);
        telemetry.update();
    }
}
