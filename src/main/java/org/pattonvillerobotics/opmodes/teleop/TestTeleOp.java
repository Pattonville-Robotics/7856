package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;

/**
 * Created by bahrg on 9/27/16.
 */

@TeleOp(name="candyBot", group="Test")
public class TestTeleOp extends LinearOpMode {

    private AbstractDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        while(opModeIsActive()) {
            doLoop();
            idle();
        }
    }

    public void initialize() {
        drive = new SimpleDrive(this, hardwareMap);
        telemetry.addData("Init", "Initialized.");
    }

    public void doLoop() {
        drive.moveFreely(gamepad1.left_stick_y, -gamepad1.right_stick_y);
    }
}
