package org.pattonvillerobotics.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.REVGyro;
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
            simpleMecanumDrive.driveWithJoysticks(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.right_stick_x);
            idle();

        }

    }

    private void initialize() {

        addTelemetry("Initializing...");

        glyphGrabber = new GlyphGrabber(hardwareMap, Globals.GrabberPosition.RELEASED);
        gamepad = new ListenableGamepad();
        simpleMecanumDrive = new SimpleMecanumDrive(this, hardwareMap);

        //mecanumEncoderDrive = new MecanumEncoderDrive(hardwareMap, this, CustomRobotParameters.ROBOT_PARAMETERS);

        gyro = new REVGyro(hardwareMap);

        addTelemetry("Gyro initialized");

        //relicGrabber = new RelicGrabber(hardwareMap, Globals.GrabberPosition.RELEASED);
        glyphter = new Glyphter(hardwareMap);

        addTelemetry("Binding buttons");

        bindGamepadButtons();

        addTelemetry("Buttons bound");

        //glyphGrabber.release();

        addTelemetry("Done");

    }

    private void bindGamepadButtons() {

        gamepad.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_PRESSED,
                new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        switch(glyphGrabber.getPosition()) {
                            case CLAMPED:
                                glyphGrabber.release();
                                break;
                            case RELEASED:
                                glyphGrabber.clamp();
                                break;
                        }
                    }
                }
        );

        gamepad.getButton(GamepadData.Button.Y).addListener(ListenableButton.ButtonState.JUST_PRESSED,
                new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        switch(relicGrabber.getPosition()) {
                            case CLAMPED:
                                relicGrabber.release();
                                break;
                            case RELEASED:
                                relicGrabber.clamp();
                                break;
                        }
                    }
                }
        );

        gamepad.getButton(GamepadData.Button.X).addListener(ListenableButton.ButtonState.JUST_PRESSED,
                new ListenableButton.ButtonListener() {
                    @Override
                    public void run() {
                        gyro.balanceRobot(simpleMecanumDrive);
                    }
                }
        );

        gamepad.getButton(GamepadData.Button.DPAD_UP).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                addTelemetry("d-pad up");
                glyphter.moveOneSpace(Direction.UP);
            }
        });

        gamepad.getButton(GamepadData.Button.DPAD_DOWN).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                addTelemetry("d-pad down");
                glyphter.moveOneSpace(Direction.DOWN);
            }
        });

        gamepad.getButton(GamepadData.Button.DPAD_LEFT).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                glyphter.moveOneSpace(Direction.LEFT);
            }
        });

        gamepad.getButton(GamepadData.Button.DPAD_RIGHT).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                glyphter.moveOneSpace(Direction.RIGHT);
            }
        });

        gamepad.getButton(GamepadData.Button.RIGHT_BUMPER).addListener(ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                relicExtender.extend();
            }
        });

    }

    public void addTelemetry(Object value) {

        telemetry.addData(TAG, value);
        telemetry.update();

    }

}