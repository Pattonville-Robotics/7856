package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.robotclasses.mechanisms.HookLiftingMechanism;

@TeleOp(name = "MainTeleOp", group = OpModeGroups.MAIN)
public class MainTeleOp extends LinearOpMode {

    public SimpleDrive drive;
    public ListenableGamepad gamepad;
    public HookLiftingMechanism hookLifter;

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
                //do something
            }
        });
        gamepad.addButtonListener(GamepadData.Button.RIGHT_BUMPER, ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                hookLifter.raise(0.5);
            }
        });
        gamepad.addButtonListener(GamepadData.Button.LEFT_BUMPER, ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                hookLifter.lower(0.5);
            }
        });
        drive = new SimpleDrive(this, hardwareMap);
    }
}
