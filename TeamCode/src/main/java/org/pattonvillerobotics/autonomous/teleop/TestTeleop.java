package org.pattonvillerobotics.opmodes.autonomous.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

@TeleOp (name = "MainTeleop", group = OpModeGroups.TESTING)
public class TestTeleop extends LinearOpMode {

    public SimpleDrive drive;
    public ListenableGamepad gamepad;
    private double rotationFactor = 1;



    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        while (opModeIsActive()) {

            gamepad.update(new GamepadData(gamepad1));
            drive.moveFreely(gamepad1.left_stick_y, gamepad1.left_stick_x);
            idle();

        }


    }

    private void initialize() {
        hookLifter = new org.pattonvillerobotics.opmodes.autonomous.robotclasses.HookLiftingMechanism

        


    }
}