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
import org.pattonvillerobotics.commoncode.robotclasses.opencv.ImageProcessor;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.roverruckus.minerals.MineralDetector;


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
//    private ArmMechanism armMechansim;
    @Override
    public void runOpMode() {
        initialize();

        runner.dropFromLander();
        drive.moveInches(Direction.FORWARD, 35, 0.5);
        teamMarker.move(0.5);
        sleep(10000);




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