package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.robotclasses.mechanisms.HookLiftingMechanism;
import org.pattonvillerobotics.robotclasses.misc.CustomizedRobotParameters;


@Autonomous (name = "GoldAutonomous", group = OpModeGroups.MAIN)
public class GoldAutonomous extends LinearOpMode {


    public EncoderDrive drive;
    public HookLiftingMechanism hookLifter;



    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        telemetry.addData("hook position", hookLifter.getPosition());
        hookLifter.raise(0.3);
        hookLifter.lower(0.5);
        drive.moveInches(Direction.BACKWARD,14,0.5);
        drive.rotateDegrees(Direction.LEFT, 90, 0.5);

        drive.moveInches(Direction.BACKWARD,12,0.7);
        drive.rotateDegrees(Direction.RIGHT, 43, 0.5);

        drive.rotateDegrees(Direction.FORWARD,35,0.5);
        drive.moveInches(Direction.BACKWARD,11,0.7);

        drive.rotateDegrees(Direction.FORWARD,45,0.7);
        drive.moveInches(Direction.RIGHT,15,0.5);
        //parking in the crater
        drive.moveInches(Direction.FORWARD, 66, 0.5);
        drive.moveInches(Direction.BACKWARD,35,0.6);
        idle();




    }
    public void initialize() {

        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        hookLifter = new HookLiftingMechanism(hardwareMap, this);
    }

}
