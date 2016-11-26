package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.robotclasses.mechanisms.ArmMover;
import org.pattonvillerobotics.robotclasses.mechanisms.Hopper;
import org.pattonvillerobotics.robotclasses.mechanisms.Cannon;

/**
 * Created by skaggsw on 10/4/16.
 */
@TeleOp(name = "MainTeleOp", group = OpModeGroups.MAIN)

public class MainTeleOp extends LinearOpMode {

    private EncoderDrive drive;
    private ListenableGamepad gamepad;
    private ArmMover armMover;
    private Hopper hopper;
    private Cannon particleLauncher;
    private boolean hopperOn = false;
    private Direction currentDirection;

    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.setMsTransmissionInterval(16);
        final Telemetry.Item particle_Launcher = telemetry.addData("Particle Launcher: ", "N/A").setRetained(true);
        waitForStart();

        while (opModeIsActive()) {
            particle_Launcher.setValue(particleLauncher.getCannon().getCurrentPosition());
            doLoop();
            idle();
        }
    }

    public void initialize() {
        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        gamepad = new ListenableGamepad();
        armMover = new ArmMover(hardwareMap, this);
        particleLauncher = new Cannon(hardwareMap, this);
        hopper = new Hopper(hardwareMap, this);
        currentDirection = Direction.IN;

        telemetry.setMsTransmissionInterval(16);
        final Telemetry.Item particle_Launcher = telemetry.addData("Particle Launcher: ", "N/A").setRetained(true);

//        gamepad.getButton(GamepadData.Button.DPAD_LEFT).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
//            @Override
//            public void run() {
//                armMover.moveTo(ArmMover.Position.LEFT);
//            }
//        });
//        gamepad.getButton(GamepadData.Button.DPAD_RIGHT).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
//            @Override
//            public void run() {
//                armMover.moveTo(ArmMover.Position.RIGHT);
//            }
//        });
//        gamepad.getButton(GamepadData.Button.DPAD_DOWN).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
//            @Override
//            public void run() {
//                armMover.moveTo(ArmMover.Position.DOWN);
//            }
//        });
        gamepad.getButton(GamepadData.Button.X).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                hopperOn = !hopperOn;
            }
        });

        gamepad.getButton(GamepadData.Button.Y).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                switch (currentDirection) {
                    case IN:
                        currentDirection = Direction.OUT;
                        break;
                    case OUT:
                        currentDirection = Direction.IN;
                        break;
                }
            }
        });
        gamepad.getButton(GamepadData.Button.B).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {

                particleLauncher.primeLauncher();
            }
        });
        gamepad.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {

                particleLauncher.releaseLauncher();
            }
        });
    }
//        gamepad.getButton(GamepadData.Button.LEFT_BUMPER).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
//            @Override
//            public void run() {
//                armMover.decrementPosition();
//            }
//        });
//        gamepad.getButton(GamepadData.Button.RIGHT_BUMPER).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
//            @Override
//            public void run() {
//                armMover.incrementPosition();
//            }
//        });
//    }
    public void doLoop() {
        drive.moveFreely(gamepad1.left_stick_y, gamepad1.right_stick_y);
        gamepad.update(new GamepadData(gamepad1));
        hopper.update(hopperOn, currentDirection);
        telemetry.update();
    }

    public enum Direction {IN, OUT}
}