package org.pattonvillerobotics.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.mechanisms.GlyphGrabber;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

/**
 * Created by martint08 on 9/30/17.
 */
@TeleOp(name = "MainTeleop", group = OpModeGroups.MAIN)
public class MainTeleop extends LinearOpMode {

    private GlyphGrabber glyphGrabber;
    private SimpleMecanumDrive simpleMecanumDrive;

    public void runOpMode() throws InterruptedException {
        glyphGrabber = new GlyphGrabber(hardwareMap, this);
        ListenableGamepad gamepad = new ListenableGamepad();
        simpleMecanumDrive = new SimpleMecanumDrive(this, hardwareMap);


        gamepad.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_PRESSED,
                new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        //action here
                        glyphGrabber.release();
                    }
                });
        waitForStart();

        while (opModeIsActive()) {
            simpleMecanumDrive.driveWithJoysticks(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);


        }

        idle();
        gamepad.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_PRESSED,
                new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        //being grabbed
                        glyphGrabber.clamp();

                    }

                });
        gamepad.getButton(GamepadData.Button.B).addListener(ListenableButton.ButtonState.JUST_PRESSED,
                new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        //being released
                        glyphGrabber.release();
                    }

                });
        gamepad.getButton(GamepadData.Button.Y).addListener(ListenableButton.ButtonState.JUST_PRESSED,
                new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        //grab relic
                    }

                });
        gamepad.getButton(GamepadData.Button.X).addListener(ListenableButton.ButtonState.JUST_PRESSED,
                new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        //automatic balance
                    }
                });


    }
}