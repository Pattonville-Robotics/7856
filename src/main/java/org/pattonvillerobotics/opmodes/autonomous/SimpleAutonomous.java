package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by pieperm on 11/19/16.
 */

@Autonomous(name = "SimpleAutonomous", group = OpModeGroups.MAIN)
public class SimpleAutonomous extends LinearOpMode {

    private EncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        waitForStart();

        drive.moveInches(Direction.BACKWARD, 67, Globals.MAX_MOTOR_POWER);
    }

}
