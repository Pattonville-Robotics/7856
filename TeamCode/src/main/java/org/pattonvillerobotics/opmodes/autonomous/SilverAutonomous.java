package org.pattonvillerobotics.opmodes.autonomous;

import  com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;
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
    private LinearOpMode linearOpMode;


    @Override
    public void runOpMode() {
        initialize();
        waitForStart();
        /* Douglas-Peucker algorithm
           | <- r drive      l drive -> |
           |                            |
           |       ^ Backwards ^        |
           |                            |
           |                            |
           |                            |
           |          center            |
           |                            |
           |                            |
           |       ^ Forwards  ^        |
           |                            |
           | <- right rear left rear -> |
         */
//        runner.dropFromLander();
        this.sleep(1000);
        drive.moveInches(Direction.BACKWARD, 1, 1);
        this.sleep(2000);
        drive.moveInches(Direction.BACKWARD, 0, 0);
//        sleep(100);
//        drive.rotateDegrees(Direction.CLOCKWISE, 45, 0.8);
//        drive.moveInches(Direction.BACKWARD, 10, 0.8);
//        drive.rotateDegrees(Direction.COUNTERCLOCKWISE, 15, 0.8);
//        drive.moveInches(Direction.FORWARD, 42, 0.8);
        idle();
    }

    private void initialize() {
        ImageProcessor.initOpenCV(hardwareMap, this);
        drive = new MecanumEncoderDrive(hardwareMap, this, CustomizedRobotParameters.setParams());
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        hookLifter = new HookLiftingMechanism(this, hardwareMap);
        mineralDetector = new MineralDetector(CustomizedRobotParameters.setPhoneParams(), true);
        vuforia = new VuforiaNavigation(CustomizedRobotParameters.setVuforiaParams());
        teamMarker = new TeamMarkerMechanism(hardwareMap, this);
        runner = new CommonMethods(hardwareMap, this, drive, hookLifter, imu, mineralDetector, vuforia, teamMarker);
        runner.initAutonomous();
    }

}