package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.robotclasses.mechanisms.HookLiftingMechanism;
import org.pattonvillerobotics.robotclasses.misc.CustomizedRobotParameters;


@Autonomous (name = "SilverAutonomous", group = OpModeGroups.MAIN)
public class SilverAutonomous extends LinearOpMode {


    public EncoderDrive drive;
    public HookLiftingMechanism hookLifter;



    @Override
    public void runOpMode() {
        initialize();

        waitForStart(); //wait for game to start(driver press play)

        telemetry.addData("hook position", hookLifter.getPosition());

        hookLifter.raise(0.3);
        hookLifter.lower(0.5);
        drive.moveInches(Direction.LEFT,7,0.5);
        drive.rotateDegrees(Direction.FORWARD,30,0.5);

        drive.moveInches(Direction.BACKWARD,21,0.5);
        drive.moveInches(Direction.BACKWARD,2,0.2);

        //next direction
        drive.rotateDegrees(Direction.FORWARD,35,0.4);
        drive.moveInches(Direction.BACKWARD,4,0.3); //park in the crater
        drive.rotateDegrees(Direction.BACKWARD,11,0.5);

        drive.moveInches(Direction.RIGHT,3,0.5);
        drive.rotateDegrees(Direction.RIGHT,10,0.3);

        drive.rotateDegrees(Direction.FORWARD,12,0.4);
        drive.moveInches(Direction.BACKWARD,70,0.5);
        idle();
    }
    public void initialize() {

        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        hookLifter = new HookLiftingMechanism(hardwareMap, this);

    }

}