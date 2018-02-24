package org.pattonvillerobotics.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

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
    private double rotationFactor = 1;

    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        while (opModeIsActive()) {

            gamepad.update(new GamepadData(gamepad1));
            simpleMecanumDrive. driveWithJoysticks(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x / rotationFactor);
            glyphter.getMotor().setPower(gamepad1.right_trigger - gamepad1.left_trigger / 2);

            idle();

        }

    }

    private void initialize() {

        addTelemetry("Initializing MainTeleOp...");

        glyphGrabber = new GlyphGrabber(hardwareMap, this, Globals.GrabberState.RELEASED);
        gamepad = new ListenableGamepad();
        simpleMecanumDrive = new SimpleMecanumDrive(this, hardwareMap);
        glyphter = new Glyphter(hardwareMap, this);
        gyro = new REVGyro(hardwareMap, this);
        jewelWhopper = new JewelWhopper(hardwareMap, this, JewelWhopper.Position.UP);

        bindGamepadButtons();
        glyphter.getMotor().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        addTelemetry("Done");

    }

    private void bindGamepadButtons() {

        gamepad.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_PRESSED, () -> {
            switch (glyphGrabber.getGrabberBottomState()) {
                        case CLAMPED:
                            glyphGrabber.releaseBottom();
                            break;
                        case RELEASED:
                            glyphGrabber.clampBottom();
                            break;
                    }
        });

        gamepad.getButton(GamepadData.Button.Y).addListener(ListenableButton.ButtonState.JUST_PRESSED, () -> {
            switch (glyphGrabber.getGrabberTopState()) {
                case CLAMPED:
                    glyphGrabber.releaseTop();
                    break;
                case RELEASED:
                    glyphGrabber.clampTop();
                    break;
            }
        });

        gamepad.getButton(GamepadData.Button.B).addListener(ListenableButton.ButtonState.JUST_PRESSED, () -> {
            switch (jewelWhopper.getPosition()) {
                case UP:
                    jewelWhopper.moveDown();
                    break;
                case DOWN:
                    jewelWhopper.moveUp();
                    break;
            }
        });

        gamepad.getButton(GamepadData.Button.X).addListener(ListenableButton.ButtonState.JUST_PRESSED, () -> {
            switch (glyphGrabber.getGrabberBottomState()) {
                case CLAMPED:
                    glyphGrabber.releaseBoth();
                    break;
                case RELEASED:
                    glyphGrabber.clampBoth();
                    break;
            }
        });

        gamepad.getButton(GamepadData.Button.DPAD_UP).addListener(ListenableButton.ButtonState.JUST_PRESSED, () -> {

         });

        gamepad.getButton(GamepadData.Button.DPAD_LEFT).addListener(ListenableButton.ButtonState.JUST_PRESSED, () -> {
            glyphGrabber.TestBottomRightIncrease();
            addTelemetry("Bottom Right Servo Value: " + glyphGrabber.servo_value);
        });

        gamepad.getButton(GamepadData.Button.DPAD_RIGHT).addListener(ListenableButton.ButtonState.JUST_PRESSED, () -> {
            glyphGrabber.TestBottomRightDecrease();
            addTelemetry("Bottom Right Servo Value: " + glyphGrabber.servo_value);
        });

        gamepad.getButton(GamepadData.Button.STICK_BUTTON_RIGHT).addListener(ListenableButton.ButtonState.JUST_PRESSED, () -> {
            if (rotationFactor == 1) {
                rotationFactor = 2;
            } else {
                rotationFactor = 1;
            }
        });

    }

    public void addTelemetry(Object value) {
        telemetry.addData(TAG, value).setRetained(true);
        telemetry.update();
    }

    public void addTelemetry(String caption, Object value) {
        telemetry.addData(caption, value).setRetained(true);
        telemetry.update();
    }

}