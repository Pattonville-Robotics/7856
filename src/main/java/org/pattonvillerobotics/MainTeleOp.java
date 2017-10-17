package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

/**
 * Created by trieud01 on 9/30/17.
 */

@TeleOp(name = "MainTeleOp", group = OpModeGroups.TESTING)
public class MainTeleOp extends LinearOpMode{

    private GlyphGrabber glyphGrabber;
    private ListenableGamepad gamepad;

    @Override
    public void runOpMode() throws InterruptedException {

        glyphGrabber = new GlyphGrabber(hardwareMap, this);
        gamepad = new ListenableGamepad();

        gamepad.getButton(GamepadData.Button.B).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                telemetry.addData("Glyph Grabber", "Clamped");
                glyphGrabber.clamp();
            }
        });

        gamepad.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                telemetry.addData("Glyph Grabber", "Released");
                glyphGrabber.release();
            }
        });

        telemetry.addData("MainTeleOp", "Init complete");
        telemetry.update();

        waitForStart();

        while(opModeIsActive()) {

            gamepad.update(new GamepadData(gamepad1));

            telemetry.update();

            idle();

        }

    }




}
