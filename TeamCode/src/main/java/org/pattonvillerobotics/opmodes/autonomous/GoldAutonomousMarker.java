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
import org.pattonvillerobotics.robotclasses.mechanisms.ArmMechanism;
import org.pattonvillerobotics.robotclasses.mechanisms.HookLiftingMechanism;
import org.pattonvillerobotics.robotclasses.misc.CommonMethods;
import org.pattonvillerobotics.robotclasses.misc.CustomizedRobotParameters;


@Autonomous (name = "Gold Side Autonomous - Center & marker", group = OpModeGroups.MAIN)
public class GoldAutonomousMarker extends LinearOpMode {

    private MecanumEncoderDrive drive;
    private VuforiaNavigation vuforia;
    private MineralDetector mineralDetector;
    private ArmMechanism armMechanism;
    private BNO055IMU imu;
    private CommonMethods runner;

    @Override
    public void runOpMode() {
        initialize();
        waitForStart();

        double speed = 3.0;
        int numberOfPicsToTake = 7;

        runner.dropFromLander();


        drive.moveInches(Direction.BACKWARD, 20, speed);
        drive.moveInches(Direction.FORWARD, 5.5, speed);
        drive.rotateDegrees(Direction.CLOCKWISE, 150, speed);
        drive.moveInches(Direction.BACKWARD, 30,speed);
        drive.rotateDegrees(Direction.CLOCKWISE, 90, speed);
        drive.moveInches(Direction.BACKWARD, 60,speed);
        armMechanism.move(1);
        sleep(5000);
        armMechanism.move(0);
        telemetry.addLine("Would have dropped marker!");
        telemetry.update();
        idle();
    }

    private void initialize() {
        ImageProcessor.initOpenCV(hardwareMap, this);
        drive = new MecanumEncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        HookLiftingMechanism hookLifter = new HookLiftingMechanism(this, hardwareMap);
        mineralDetector = new MineralDetector(CustomizedRobotParameters.PHONE_ORIENTATION, true);
        vuforia = new VuforiaNavigation(CustomizedRobotParameters.VUFORIA_PARAMETERS);
        ImageProcessor.initOpenCV(hardwareMap, this);
        armMechanism = new ArmMechanism(this, hardwareMap);
        runner = new CommonMethods(hardwareMap, this, drive, hookLifter, imu, mineralDetector, vuforia, armMechanism);
        runner.initAutonomous();
        telemetry.addLine("Initialized Autonomous!\nReady To Start √");
        telemetry.update();
    }


}