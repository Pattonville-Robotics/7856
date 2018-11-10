package org.pattonvillerobotics.OpModes.teleop;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.robotClasses.HookLiftingMechanism;

@TeleOp (name = "TestTeleop", group = OpModeGroups.TESTING)
public class TestTeleop extends LinearOpMode {

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

//        gamepad.addButtonListener(GamepadData.Button.A, ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
//            @Override
//so                telemetry
//
//            }
//        });
//        drive = new SimpleDrive(this, hardwareMap);
    }
}
