package org.pattonvillerobotics.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.mechanisms.GlyphGrabber;
import org.pattonvillerobotics.mechanisms.Glyphter;
import org.pattonvillerobotics.mechanisms.JewelWhopper;
import org.pattonvillerobotics.mechanisms.REVGyro;

/**
 * Created by martint08 on 9/30/17.
 */
@TeleOp(name = "MainTeleop", group = OpModeGroups.MAIN)
public class MainTeleop extends LinearOpMode {

    private static final String TAG = MainTeleop.class.getSimpleName();
    private GlyphGrabber glyphGrabber;
    private SimpleMecanumDrive simpleMecanumDrive;
    private ListenableGamepad gamepad;
    private REVGyro gyro;
    private Glyphter glyphter;
    private JewelWhopper jewelWhopper;

    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        while (opModeIsActive()) {

            gamepad.update(new GamepadData(gamepad1));
            simpleMecanumDrive.driveWithJoysticks(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            glyphter.getMotor().setPower(gamepad1.right_trigger / 2 - gamepad1.left_trigger / 4);
            idle();

        }

    }

    private void initialize() {

        addTelemetry("Initializing MainTeleOp...");

        glyphGrabber = new GlyphGrabber(hardwareMap, this, Globals.GrabberPosition.RELEASED);
        gamepad = new ListenableGamepad();
        simpleMecanumDrive = new SimpleMecanumDrive(this, hardwareMap);
        glyphter = new Glyphter(hardwareMap, this);
        gyro = new REVGyro(hardwareMap, this);
        jewelWhopper = new JewelWhopper(hardwareMap, this, JewelWhopper.Position.UP);

        bindGamepadButtons();

        addTelemetry("Done");

    }

    private void bindGamepadButtons() {

        gamepad.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_PRESSED, () -> {
                    switch (glyphGrabber.getPosition()) {
                        case CLAMPED:
                            glyphGrabber.release();
                            break;
                        case RELEASED:
                            glyphGrabber.clamp();
                            break;
                    }
        });

         gamepad.getButton(GamepadData.Button.X).addListener(ListenableButton.ButtonState.JUST_PRESSED, () -> {
             switch (jewelWhopper.getPosition()) {
                 case UP:
                     jewelWhopper.moveDown();
                     break;
                 case DOWN:
                     jewelWhopper.moveUp();
                     break;
             }
         });

         gamepad.getButton(GamepadData.Button.DPAD_UP).addListener(ListenableButton.ButtonState.JUST_PRESSED, () -> {
            jewelWhopper.incrementPosition();
            telemetry.addData("Servo pos", jewelWhopper.getServo().getPosition()).setRetained(true);
            telemetry.update();
         });

        gamepad.getButton(GamepadData.Button.DPAD_DOWN).addListener(ListenableButton.ButtonState.JUST_PRESSED, () -> {
            jewelWhopper.decrementPosition();
            telemetry.addData("Servo pos", jewelWhopper.getServo().getPosition()).setRetained(true);
            telemetry.update();
        });

    }

    public void addTelemetry(Object value) {

        telemetry.addData(TAG, value).setRetained(true);
        telemetry.update();

    }

}