package org.pattonvillerobotics.OpModes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.robotClasses.HookLiftingMechanism;


@Autonomous (name = "TestAutonomous", group = OpModeGroups.TESTING)
public class TestAutonomous extends LinearOpMode {
    public EncoderDrive drive;
    public HookLiftingMechanism hookLifter;



    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        telemetry.addData("hook position", hookLifter);

    }
    public void initialize() {
/*
        drive = new EncoderDrive(hardwareMap, this, )
*/

        hookLifter.raise();
        drive.moveInches(Direction.LEFT, 5, 0.5);

    }

}
