package org.pattonvillerobotics.testopmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;

/**
 * Created by pieperm on 11/11/17.
 */
@Autonomous(name = "DriveTester", group = OpModeGroups.TESTING)
public class DriveTester extends LinearOpMode {

    private MecanumEncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new MecanumEncoderDrive(hardwareMap, this, CustomRobotParameters.ROBOT_PARAMETERS);

        waitForStart();

        while(opModeIsActive()) {
            drive.moveInches(Direction.FORWARD, 10, 0.5);
            sleep(2000);
            drive.rotateDegrees(Direction.LEFT, 180, 0.5);
        }

    }
}
