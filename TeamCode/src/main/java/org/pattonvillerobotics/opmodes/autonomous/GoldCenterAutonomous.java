package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.roverruckus.minerals.MineralDetector;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.robotclasses.mechanisms.HookLiftingMechanism;
import org.pattonvillerobotics.robotclasses.mechanisms.TeamMarkerMechanism;
import org.pattonvillerobotics.robotclasses.misc.CustomizedRobotParameters;


@Autonomous (name = "GoldCenterAutonomous", group = OpModeGroups.MAIN)
public class GoldCenterAutonomous extends LinearOpMode {


    public EncoderDrive drive;
    public HookLiftingMechanism hookLifter;
    public VuforiaNavigation vuforia;
    public MineralDetector mineralDetector;
    public TeamMarkerMechanism teamMarker;


    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        //vuforia for jewels here

//        telemetry.addData("hook position", hookLifter.getPosition());
//        hookLifter.raise(0.1);
//        sleep(500);
//        hookLifter.move(0);
//        drive.moveInches(Direction.LEFT, 5, 0.5);
//        sleep(150);
//        hookLifter.lower(0.1);
//        sleep(100);
//        hookLifter.move(0);
//        drive.rotateDegrees(Direction.RIGHT, 45, 0.3);
//        drive.moveInches(Direction.FORWARD, 36, 0.5);
        drive.moveInches(Direction.BACKWARD, 60, 0.6);
        idle();




    }
    public void initialize() {

        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        hookLifter = new HookLiftingMechanism(hardwareMap, this);
    }

}
