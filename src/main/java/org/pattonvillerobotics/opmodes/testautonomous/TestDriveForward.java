package org.pattonvillerobotics.opmodes.testautonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.opmodes.autonomous.Globals;

/**
 * Created by bahrg on 12/29/16.
 */

@Autonomous(name="TestDrive", group="TESTING")
public class TestDriveForward extends LinearOpMode {
    private EncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        waitForStart();
        drive.moveInches(Direction.BACKWARD, 70, Globals.HALF_MOTOR_POWER);
        drive.rotateDegrees(Direction.LEFT, 45, Globals.HALF_MOTOR_POWER);

    }
}
