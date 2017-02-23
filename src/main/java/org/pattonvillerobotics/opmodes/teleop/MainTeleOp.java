package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.opmodes.autonomous.Globals;
import org.pattonvillerobotics.robotclasses.mechanisms.BallQueue;
import org.pattonvillerobotics.robotclasses.mechanisms.ButtonPresser;
import org.pattonvillerobotics.robotclasses.mechanisms.Cannon;
import org.pattonvillerobotics.robotclasses.mechanisms.Hopper;

/**
 * Created by skaggsw on 10/4/16.
 */
@TeleOp(name = "MainTeleOp", group = OpModeGroups.MAIN)

public class MainTeleOp extends LinearOpMode {

    private EncoderDrive drive;
    private ListenableGamepad gamepad;
    private ButtonPresser buttonPresser;
    private boolean cannonOn, hopperOn, leftServoToggle, rightServoToggle;
    private Hopper hopper;
    private Cannon cannon;
    private Hopper.Direction currentDirection;
    private BallQueue ballQueue;

    public void runOpMode() throws InterruptedException {
        telemetry.setMsTransmissionInterval(33);
        initialize();
        final Telemetry.Item leftMotorPowerData = telemetry.addData("Left Motor Power: ", "N/A").setRetained(true);
        final Telemetry.Item rightMotorPowerData = telemetry.addData("Right Motor Power: ", "N/A").setRetained(true);
        telemetry.addData("Init:", "Completed!");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            gamepad.update(new GamepadData(gamepad1));
            if(!gamepad1.dpad_up || !gamepad1.dpad_down) {
                drive.moveFreely(Range.clip(gamepad1.left_stick_y * FastMath.abs(gamepad1.left_stick_y), -Globals.MAX_MOTOR_POWER, Globals.MAX_MOTOR_POWER),
                        Range.clip(gamepad1.right_stick_y * FastMath.abs(gamepad1.right_stick_y), -Globals.MAX_MOTOR_POWER, Globals.MAX_MOTOR_POWER));
            }
            leftMotorPowerData.setValue(drive.leftDriveMotor.getPower());
            rightMotorPowerData.setValue(drive.rightDriveMotor.getPower());
            telemetry.update();
            idle();
        }
    }

    public void initialize() {
        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        gamepad = new ListenableGamepad();
        buttonPresser = new ButtonPresser(hardwareMap, this);
        cannon = new Cannon(hardwareMap, this);
        hopper = new Hopper(hardwareMap, this);
        ballQueue = new BallQueue(hardwareMap, this);
        ballQueue.setBallQueueOut();
        currentDirection = Hopper.Direction.IN;

        final Telemetry.Item leftServoData = telemetry.addData("Left Servo: ", "IN").setRetained(true);
        final Telemetry.Item rightServoData = telemetry.addData("Right Servo: ", "IN").setRetained(true);
        final Telemetry.Item ballQueueData = telemetry.addData("Ball Queue: ", "OUT").setRetained(true);

        gamepad.getButton(GamepadData.Button.X).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                hopperOn = !hopperOn;
                if (hopperOn) {
                    hopper.activate();
                } else {
                    hopper.deactivate();
                }
            }
        });

        gamepad.getButton(GamepadData.Button.Y).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                switch (currentDirection) {
                    case IN:
                        currentDirection = Hopper.Direction.OUT;
                        break;
                    case OUT:
                        currentDirection = Hopper.Direction.IN;
                        break;
                }
                hopper.setDirection(currentDirection);
            }
        });
        gamepad.getButton(GamepadData.Button.B).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                cannon.fire();
            }
        });

        gamepad.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                cannonOn = !cannonOn;
                cannon.setToggle(cannonOn);
            }
        });

        gamepad.getButton(GamepadData.Button.LEFT_BUMPER).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                leftServoToggle = !leftServoToggle;
                if (leftServoToggle) {
                    buttonPresser.setLeftOut();
                    leftServoData.setValue("OUT");
                } else {
                    buttonPresser.setLeftIn();
                    leftServoData.setValue("IN");
                }
            }
        });
        gamepad.getButton(GamepadData.Button.RIGHT_BUMPER).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                rightServoToggle = !rightServoToggle;
                if (rightServoToggle) {
                    buttonPresser.setRightOut();
                    rightServoData.setValue("OUT");
                } else {
                    buttonPresser.setRightIn();
                    rightServoData.setValue("IN");
                }
            }
        });

        gamepad.getButton(GamepadData.Button.DPAD_UP).addListener(ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                drive.move(Direction.FORWARD, .5);
            }
        });

        gamepad.getButton(GamepadData.Button.DPAD_DOWN).addListener(ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                drive.move(Direction.BACKWARD, .5);
            }
        });

        gamepad.getButton(GamepadData.Button.STICK_BUTTON_RIGHT).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                ballQueue.setBallQueueOut();
                ballQueueData.setValue("OUT");
            }
        });

        gamepad.getButton(GamepadData.Button.STICK_BUTTON_LEFT).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                ballQueue.setBallQueueIn();
                ballQueueData.setValue("IN");
            }
        });
    }
}