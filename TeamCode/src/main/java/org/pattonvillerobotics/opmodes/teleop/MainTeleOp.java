package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

@TeleOp(name = "MainTeleOp", group = OpModeGroups.MAIN)
public class MainTeleOp extends LinearOpMode {

    public SimpleDrive drive;
    public ListenableGamepad gamepad;

    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        while (opModeIsActive()) {
            gamepad.update(new GamepadData(gamepad1));
            drive.moveFreely(gamepad1.left_stick_y, gamepad1.right_stick_y);
            telemetry.addData("ToggleableGamePad x:", gamepad1.left_stick_y);
            telemetry.addData("ToggleableGamePad y:", gamepad1.right_stick_y);
        }
    }

    public void initialize() {
        gamepad = new ListenableGamepad();

        gamepad.addButtonListener(GamepadData.Button.A, ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                // do stuff when the A button is just pressed
            }
        });
        drive = new SimpleDrive(this, hardwareMap);
    }
}
