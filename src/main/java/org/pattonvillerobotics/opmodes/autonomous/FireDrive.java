package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by bahrg on 11/12/16.
 */

@Autonomous(name="FireDriveBackup", group="Autonomous")
public class FireDrive extends LinearOpMode {

    private EncoderDrive drive;
    private AutoMethods autoMethods;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        autoMethods = new AutoMethods(drive, AllianceColor.BLUE, StartPosition.LINE, EndPosition.CENTER_VORTEX, hardwareMap, this);
        waitForStart();

        autoMethods.fireCannon();
        drive.moveInches(Direction.BACKWARD, 67, Globals.MAX_MOTOR_POWER);

    }
}