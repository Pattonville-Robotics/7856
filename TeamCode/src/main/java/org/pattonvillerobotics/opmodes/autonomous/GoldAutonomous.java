package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.Drive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.ImageProcessor;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.roverruckus.minerals.MineralDetector;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.robotclasses.mechanisms.HookLiftingMechanism;
import org.pattonvillerobotics.robotclasses.mechanisms.TeamMarkerMechanism;
import org.pattonvillerobotics.robotclasses.misc.CommonMethods;
import org.pattonvillerobotics.robotclasses.misc.CustomizedRobotParameters;


@Autonomous (name = "GoldAutonomous", group = OpModeGroups.MAIN)
public class GoldAutonomous extends LinearOpMode {

    private MecanumEncoderDrive drive;
    private HookLiftingMechanism hookLifter;
    private VuforiaNavigation vuforia;
    private MineralDetector mineralDetector;
    private TeamMarkerMechanism teamMarker;
    private BNO055IMU imu;
    private boolean orientedDriveMode;
    private CommonMethods runner;
    private TeamMarkerMechanism getTeamMarker;
    LinearOpMode linearOpMode;

    @Override
    public void runOpMode() {
        initialize();

       hookLifter.move(0.5);
       linearOpMode.sleep(7000);
       hookLifter.move(-0.5);
       linearOpMode.sleep(11000);
       drive.moveInches(Direction.FORWARD, 48, 0.5);
       getTeamMarker.moveTeamMarker(0.5);
       linearOpMode.sleep(5000);
       getTeamMarker.stopTeamMarker(0);
       drive.rotateDegrees(Direction.LEFT, 45, 0.5);
       drive.moveInches(Direction.FORWARD, 80, 0.5);
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