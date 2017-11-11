package org.pattonvillerobotics.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.mechanisms.REVGyro;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.mechanisms.GlyphGrabber;
import org.pattonvillerobotics.mechanisms.Glyphter;
import org.pattonvillerobotics.mechanisms.RelicExtender;
import org.pattonvillerobotics.mechanisms.RelicGrabber;

/**
 * Created by martint08 on 9/30/17.
 */
@TeleOp(name = "MainTeleop", group = OpModeGroups.MAIN)
public class MainTeleop extends LinearOpMode {

    private static final String TAG = MainTeleop.class.getSimpleName();
    private GlyphGrabber glyphGrabber;
    private SimpleMecanumDrive simpleMecanumDrive;
    private MecanumEncoderDrive mecanumEncoderDrive;
    private ListenableGamepad gamepad;
    private REVGyro gyro;
    private RelicGrabber relicGrabber;
    private Glyphter glyphter;
    private RelicExtender relicExtender;

    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        while (opModeIsActive()) {

            gamepad.update(new GamepadData(gamepad1));
            simpleMecanumDrive.driveWithJoysticks(-gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            idle();

        }

    }

    private void initialize() {

        addTelemetry("Initializing...");

        glyphGrabber = new GlyphGrabber(hardwareMap, this, Globals.GrabberPosition.RELEASED);
        gamepad = new ListenableGamepad();
        simpleMecanumDrive = new SimpleMecanumDrive(this, hardwareMap);
        glyphter = new Glyphter(hardwareMap, this);
        gyro = new REVGyro(hardwareMap, this);
        //relicGrabber = new RelicGrabber(hardwareMap, Globals.GrabberPosition.RELEASED);

        addTelemetry("Binding buttons");
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

        gamepad.getButton(GamepadData.Button.DPAD_UP).addListener(ListenableButton.ButtonState.BEING_PRESSED, () -> {
            glyphter.moveUp();
        });

        gamepad.getButton(GamepadData.Button.DPAD_UP).addListener(ListenableButton.ButtonState.JUST_RELEASED, () -> {
            glyphter.stop();
        });

        gamepad.getButton(GamepadData.Button.DPAD_DOWN).addListener(ListenableButton.ButtonState.JUST_RELEASED, () -> {
           glyphter.stop();
        });

        gamepad.getButton(GamepadData.Button.DPAD_DOWN).addListener(ListenableButton.ButtonState.BEING_PRESSED, () -> {
            glyphter.moveDown();
        });

    }

    public void addTelemetry(Object value) {

        telemetry.addData(TAG, value).setRetained(true);
        telemetry.update();

    }

}