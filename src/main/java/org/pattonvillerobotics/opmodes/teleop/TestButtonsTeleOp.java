package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

/**
 * Created by skaggsm on 10/6/16.
 */

public class TestButtonsTeleOp extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        ListenableGamepad listenableGamepad1 = new ListenableGamepad();

        listenableGamepad1.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                telemetry.addData(this.getClass().getSimpleName(), "Button A pressed!");
            }
        });

        waitForStart();

        while (opModeIsActive()) {
            listenableGamepad1.update(new GamepadData(gamepad1));
        }
    }
}
