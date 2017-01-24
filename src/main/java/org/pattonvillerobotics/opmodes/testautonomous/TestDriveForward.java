package org.pattonvillerobotics.opmodes.testautonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.opmodes.autonomous.Globals;
import org.pattonvillerobotics.robotclasses.drive.ComplexEncoderDrive;

/**
 * Created by bahrg on 12/29/16.
 */

@Autonomous(name="TestDrive", group= OpModeGroups.TESTING)
public class TestDriveForward extends LinearOpMode {
    private EncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new ComplexEncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        waitForStart();

        drive.moveInches(Direction.FORWARD, 20, Globals.HALF_MOTOR_POWER);



        while(opModeIsActive()) {
            telemetry.update();
            idle();
        }

    }
}
