package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.robotclasses.mechanisms.Hopper;
import org.pattonvillerobotics.robotclasses.servo.ServoMover;

/**
 * Created by skaggsw on 10/4/16.
 */
@TeleOp(name = "WilliamTeleOp", group = OpModeGroups.TESTING)

public class WilliamTeleOp extends LinearOpMode {

    private EncoderDrive drive;
    private ListenableGamepad gamepad;
    private ServoMover servoMover;
    private Hopper hopper;
    private boolean hopperOn = false;

    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        while (opModeIsActive()) {
            doLoop();
            idle();
        }
    }

    public void initialize() {
        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        gamepad = new ListenableGamepad();
        servoMover = new ServoMover(hardwareMap);
        gamepad.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                servoMover.moveTo(ServoMover.Position.LEFT);
            }
        });
        gamepad.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_RELEASED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                servoMover.moveTo(ServoMover.Position.RIGHT);
            }
        });
        gamepad.getButton(GamepadData.Button.X).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                hopperOn = !hopperOn;
            }
        });

        telemetry.addData("Init", "Initialized.");
    }

    public void doLoop() {
        drive.moveFreely(gamepad1.left_stick_y, gamepad1.right_stick_y);
        gamepad.update(new GamepadData(gamepad1));
        if (hopperOn) {

            hopper.collect();

        } else {

            hopper.stopHopper();

        }
    }
}
