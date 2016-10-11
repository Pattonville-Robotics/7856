package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;


/**
 * Created by skaggsw on 9/29/16.
 */

@Autonomous(name = "TestAuto", group = "Test")

public class TestAuto extends LinearOpMode {

    private EncoderDrive drive;

    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        drive.moveInches(Direction.FORWARD, 12, 0.5);
        drive.rotateDegrees(Direction.LEFT, 90, 0.5);
        drive.moveInches(Direction.FORWARD, 12, 0.5);
        drive.rotateDegrees(Direction.RIGHT, 360, 0.5);
        drive.stop();
    }

    public void initialize() {
        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        telemetry.addData("Init", "Initialized.");
    }
}
