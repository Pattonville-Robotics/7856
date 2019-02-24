package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

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

import java.util.Timer;
import java.util.TimerTask;


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
    private Timer timer;


    @Override
    public void runOpMode() {
        initialize();
        waitForStart();

        hookLifter.move(-0.8);
        sleep(9100);
        hookLifter.move(0);
        sleep(100);
        drive.move(Direction.RIGHT, 0.5);
        sleep(1250);
        hookLifter.move(0.8);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                hookLifter.move(0);
            }
        }, 5000);
        drive.move(Direction.FORWARD, 0);
        sleep(100);
        drive.move(Direction.FORWARD, 0.5);
        sleep(2500);
        drive.move(Direction.FORWARD, 0);
        drive.moveFreely(0, 0.8, 10);
        sleep(2500);
        drive.move(Direction.FORWARD, 0);
        teamMarker.servo.setDirection(Servo.Direction.FORWARD);
        teamMarker.move(1);
        sleep(100);
        teamMarker.move(0);
        idle();
        /*
        sleep(100);
        drive.move(Direction.LEFT, 0.5);
        sleep(2150);
        drive.move(Direction.FORWARD, 0);
        sleep(100);
        drive.move(Direction.BACKWARD, 0.8);
        sleep(5350);
        drive.move(Direction.FORWARD,0);
        teamMarker.move(1);
        sleep(200);
        teamMarker.move(0);
        idle();
        /*
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
        timer = new Timer();
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