package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
//import org.pattonvillerobotics.robotclasses.mechanisms.ShovelMechanism;
import org.pattonvillerobotics.robotclasses.mechanisms.LinearSlideStackMechanism;
import org.pattonvillerobotics.robotclasses.misc.CustomizedRobotParameters;

@TeleOp(name = "MainTeleOp", group = OpModeGroups.MAIN)
public class MainTeleOp extends LinearOpMode {

    private SimpleDrive drive;
    private ListenableGamepad driveGamepad, armGamepad;
    private LinearSlideStackMechanism liftingMechanism;
    //public ShovelMechanism shovelArm;

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        while (opModeIsActive()) {

            driveGamepad.update(new GamepadData(gamepad1));
            armGamepad.update(new GamepadData(gamepad2));
            drive.moveFreely(gamepad1.right_stick_y, gamepad1.left_stick_y);
            //shovelArm.getClass(gamepad2.right_stick_x, gamepad2.left_stick_x);
            liftingMechanism.moveFreely(gamepad2.left_trigger-gamepad2.right_trigger, false);
        }
    }

    private void initialize() {
        liftingMechanism = new LinearSlideStackMechanism(10, hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS, false);
        driveGamepad = new ListenableGamepad();
        armGamepad = new ListenableGamepad();

        armGamepad.addButtonListener(GamepadData.Button.Y, ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                liftingMechanism.moveFully(Direction.FORWARD, 0.6, false);
            }
        });

        armGamepad.addButtonListener(GamepadData.Button.B, ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                liftingMechanism.moveFully(Direction.BACKWARD, 0.6, false);
            }
        });
/*
        armGamepad.addButtonListener(GamepadData.Button.A, ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                shovelArm.base_servo.setPosition(0.5);
                shovelArm.elbow_servo.setPosition(0.5);
                shovelArm.wrist_servo.setPosition(0.5);

            }
        });
        armGamepad.addButtonListener(GamepadData.Button.Y, ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                shovelArm.base_servo.setPosition(0);
                shovelArm.elbow_servo.setPosition(0);
                shovelArm.wrist_servo.setPosition(0);

            }
        });
        */

        drive = new SimpleDrive(this, hardwareMap);
    }
}
