package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.ImageProcessor;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.roverruckus.minerals.MineralDetector;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.robotclasses.mechanisms.HookLiftingMechanism;
import org.pattonvillerobotics.robotclasses.mechanisms.TeamMarkerMechanism;
import org.pattonvillerobotics.robotclasses.misc.CommonMethods;
import org.pattonvillerobotics.robotclasses.misc.CustomizedRobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.ImageProcessor;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.roverruckus.minerals.MineralDetector;


@Autonomous (name = "SilverAutonomous", group = OpModeGroups.MAIN)
public class SilverAutonomous extends LinearOpMode {

    private MecanumEncoderDrive drive;
    private HookLiftingMechanism hookLifter;
    private VuforiaNavigation vuforia;
    private MineralDetector mineralDetector;
    private TeamMarkerMechanism teamMarker;
    private BNO055IMU imu;
    private boolean orientedDriveMode;
    private CommonMethods runner;
    private TeamMarkerMechanism getTeamMarker;


    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        waitForStart();
        hookLifter.move(-0.5);
        //runner.dropFromLander();
        this.sleep(9000);
        hookLifter.move(0);
        drive.moveInches(Direction.LEFT, 5, 0.5);
        drive.moveInches(Direction.FORWARD, 2, 0.01);
        this.sleep(250);
        hookLifter.move(-0.5);
        this.sleep(2000);
        drive.moveInches(Direction.FORWARD, 14, 0.5);
        drive.rotateDegrees(Direction.RIGHT, 90, 0.5);
        drive.moveInches(Direction.FORWARD, 38, 0.5);
        //getTeamMarker.moveTeamMarker(0.5);
        this.sleep(500);
        getTeamMarker.stopTeamMarker(0);
        //drive.rotateDegrees(Direction.RIGHT, 45, 0.5);
        //drive.moveInches(Direction.FORWARD, 98, 0.5);
        /* Write your autonomous here:

            You should check the CommonMethods.java class in
            pattonvillerobotics/robotclasses/misc for useful
            methods. There should be a few methods there to
            help with sensing the mineral color, automatically
            dropping from the lander (this may need revision),
            and other stuff.

            To use these methods, say

            runner.<whatever method you're using here>(<whatever parameters it takes>);
            For example:
                runner.dropFromLander();
                runner.senseMineral();
         */

        idle();

    }

    public void initialize() {
        ImageProcessor.initOpenCV(hardwareMap, this);
        drive = new MecanumEncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        hookLifter = new HookLiftingMechanism(this, hardwareMap);
        mineralDetector = new MineralDetector(CustomizedRobotParameters.PHONE_ORIENTATION, true);
        vuforia = new VuforiaNavigation(CustomizedRobotParameters.VUFORIA_PARAMETERS);
        ImageProcessor.initOpenCV(hardwareMap, this);
        teamMarker = new TeamMarkerMechanism(hardwareMap, this);
        runner = new CommonMethods(hardwareMap, this, drive, hookLifter, imu, mineralDetector, vuforia, teamMarker);
        runner.initAutonomous();
    }

}