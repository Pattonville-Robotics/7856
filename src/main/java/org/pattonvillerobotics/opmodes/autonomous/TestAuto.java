package org.pattonvillerobotics.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;

/**
 * Created by skaggsw on 9/29/16.
 */

@Autonomous(name = "TestAuto", group = "Test")

public class TestAuto extends LinearOpMode {

    private AbstractDrive drive;

    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        drive.moveFreely(1, 1);
        drive.sleep(5000);
        drive.stop();
    }

    public void initialize() {
        drive = new SimpleDrive(this, hardwareMap);
        telemetry.addData("Init", "Initialized.");
    }
}
