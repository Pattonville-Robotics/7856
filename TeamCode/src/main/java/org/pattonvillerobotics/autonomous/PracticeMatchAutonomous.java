package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.mechanisms.GlyphGrabber;

/**
 * Created by trieud01 on 10/26/17.
 */

@Autonomous(name = "PracticeMatchAutonomius", group = OpModeGroups.TESTING)
public class PracticeMatchAutonomous extends LinearOpMode {

    private MecanumEncoderDrive drive;


    @Override
    public void runOpMode() throws InterruptedException {

        //AutoMethods autoMethods = new AutoMethods(hardwareMap, this, AllianceColor.BLUE);
        MecanumEncoderDrive drive = new MecanumEncoderDrive(hardwareMap, this, CustomRobotParameters.ROBOT_PARAMETERS);
        GlyphGrabber glyphGrabber = new GlyphGrabber(hardwareMap, this, Globals.GrabberPosition.CLAMPED);

//        drive.moveInches(Direction.LEFT,35, .3);
//
//        drive.moveInches(Direction.FORWARD, 11 , .2);
        waitForStart();

        while (opModeIsActive()) {

            drive.moveInches(Direction.RIGHT, 35, 0.5);
            sleep(1000);
            drive.moveInches(Direction.FORWARD, 10, 0.3);
            sleep(1000);
            glyphGrabber.release();

            idle();

        }



    }
}
