package org.pattonvillerobotics.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.mechanisms.GlyphGrabber;

/**
 * Created by pieperm on 2/21/18.
 */
@TeleOp(name = "DoubleGrabberTester", group = OpModeGroups.TESTING)
public class GlyphGrabberTester extends LinearOpMode {

    private GlyphGrabber glyphGrabber;
    private ListenableGamepad gamepad;

    @Override
    public void runOpMode() throws InterruptedException {

        glyphGrabber = new GlyphGrabber(hardwareMap, this, Globals.GrabberState.RELEASED);
        gamepad = new ListenableGamepad();

        gamepad.addButtonListener(GamepadData.Button.DPAD_UP, ListenableButton.ButtonState.JUST_PRESSED, () -> {
            glyphGrabber.getLeftTopServo().setPosition(glyphGrabber.getLeftTopServo().getPosition() + 0.05);
        });

        gamepad.addButtonListener(GamepadData.Button.DPAD_DOWN, ListenableButton.ButtonState.JUST_PRESSED, () -> {
            glyphGrabber.getLeftTopServo().setPosition(glyphGrabber.getLeftTopServo().getPosition() - 0.05);
        });

        gamepad.addButtonListener(GamepadData.Button.DPAD_LEFT, ListenableButton.ButtonState.JUST_PRESSED, () -> {
            glyphGrabber.getRightTopServo().setPosition(glyphGrabber.getRightTopServo().getPosition() - 0.05);
        });

        gamepad.addButtonListener(GamepadData.Button.DPAD_RIGHT, ListenableButton.ButtonState.JUST_PRESSED, () -> {
            glyphGrabber.getRightTopServo().setPosition(glyphGrabber.getRightTopServo().getPosition() + 0.05);
        });

        gamepad.addButtonListener(GamepadData.Button.Y, ListenableButton.ButtonState.JUST_PRESSED, () -> {
            glyphGrabber.getLeftBottomServo().setPosition(glyphGrabber.getLeftBottomServo().getPosition() + 0.05);
        });

        gamepad.addButtonListener(GamepadData.Button.A, ListenableButton.ButtonState.JUST_PRESSED, () -> {
            glyphGrabber.getLeftBottomServo().setPosition(glyphGrabber.getLeftBottomServo().getPosition() - 0.05);
        });

        gamepad.addButtonListener(GamepadData.Button.X, ListenableButton.ButtonState.JUST_PRESSED, () -> {
            glyphGrabber.getRightBottomServo().setPosition(glyphGrabber.getRightBottomServo().getPosition() - 0.05);
        });

        gamepad.addButtonListener(GamepadData.Button.B, ListenableButton.ButtonState.JUST_PRESSED, () -> {
            glyphGrabber.getRightBottomServo().setPosition(glyphGrabber.getRightBottomServo().getPosition() + 0.05);
        });

        waitForStart();

        while (opModeIsActive()) {
            gamepad.update(new GamepadData(gamepad1));

            telemetry.addData("Top Left", glyphGrabber.getLeftTopServo().getPosition());
            telemetry.addData("Top Right", glyphGrabber.getRightTopServo().getPosition());
            telemetry.addData("Bottom Left", glyphGrabber.getLeftBottomServo().getPosition());
            telemetry.addData("Bottom Right", glyphGrabber.getRightBottomServo().getPosition());
            telemetry.update();

            idle();
        }

    }

}
