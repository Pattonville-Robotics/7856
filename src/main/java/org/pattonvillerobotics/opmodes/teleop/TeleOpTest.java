package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;

/**
 * Created by skaggsw on 10/4/16.
 */

@TeleOp(name="Test TeleOp", group = OpModeGroups.TESTING)
public class TeleOpTest extends LinearOpMode {

    private AbstractDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            doLoop();
            telemetry.update();
            idle();
        }
    }

    public void initialize() {
        drive = new SimpleDrive(this, hardwareMap);
        telemetry.addData("Init", "Initialized.");
    }

    public void doLoop() {
        drive.moveFreely(-gamepad1.left_stick_y, -gamepad1.right_stick_y);
        telemetry.addData("Looping", "Running");
    }
}
