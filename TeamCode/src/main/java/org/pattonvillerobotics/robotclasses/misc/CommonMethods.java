package org.pattonvillerobotics.robotclasses.misc;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.commoncode.enums.ColorSensorColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.roverruckus.minerals.MineralDetector;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.robotclasses.mechanisms.HookLiftingMechanism;
import org.pattonvillerobotics.robotclasses.mechanisms.TeamMarkerMechanism;

public class CommonMethods {
    private HardwareMap hardwareMap;
    private LinearOpMode linearOpMode;
    private MecanumEncoderDrive drive;
    private HookLiftingMechanism hookLiftingMechanism;
    private BNO055IMU imu;
    private MineralDetector mineralDetector;
    private VuforiaNavigation vuforia;
    private TeamMarkerMechanism teamMarker;

    public CommonMethods(HardwareMap hardwareMap, LinearOpMode linearOpMode, MecanumEncoderDrive drive, HookLiftingMechanism hookLiftingMechanism, BNO055IMU imu) {
        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;
        this.drive = drive;
        this.hookLiftingMechanism = hookLiftingMechanism;
        this.imu = imu;
    }

    public CommonMethods(HardwareMap hardwareMap, LinearOpMode linearOpMode, MecanumEncoderDrive drive, HookLiftingMechanism hookLiftingMechanism, BNO055IMU imu, MineralDetector mineralDetector, VuforiaNavigation vuforia, TeamMarkerMechanism teamMarker) {
        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;
        this.drive = drive;
        this.hookLiftingMechanism = hookLiftingMechanism;
        this.imu = imu;
        this.mineralDetector = mineralDetector;
        this.vuforia = vuforia;
        this.teamMarker = teamMarker;
    }

    public void initTeleop() {
        drive.leftRearMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        drive.leftDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        drive.leftRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.leftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.rightRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.rightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);
    }

    public void initAutonomous() {
        drive.leftRearMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        drive.leftDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        drive.leftRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.leftDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.rightRearMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        drive.rightDriveMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);

    }

    public void dropFromLander() {
        hookLiftingMechanism.move(-.9);
        sleep(5000);
        hookLiftingMechanism.move(0);
        sleep(100);
        drive.moveInches(Direction.RIGHT, 2, 0.4);
        sleep(100);
        drive.moveInches(Direction.FORWARD, 5, 0.4);
        sleep(100);
        drive.moveInches(Direction.LEFT, 2, 0.4);
    }

    public int senseMineral() {
        drive.rotateDegrees(Direction.RIGHT, 45, 0.4);
        if(sense(12) == 0) {
            if(sense(16) == 0) {
                sense(12);
                return 3;
            } else {
                return 2;
            }
        } else {
            return 1;
        }
    }

    public int sense(int inches) {
        mineralDetector.process(vuforia.getImage());
        if(mineralDetector.getAnalysis() == ColorSensorColor.YELLOW) {
            drive.moveInches(Direction.FORWARD, inches, 0.5);
            return 1;
        } else {
            drive.rotateDegrees(Direction.LEFT, 45, 0.4);
            return 0;
        }
    }

    /*To be used with senseMineral, like so:
        runner.dropMarker(senseMineral());
     */
    public void dropMarker(int placementValue) {
        switch(placementValue) {
            case 1:
                drive.rotateDegrees(Direction.RIGHT, 90, .4);
                drive.moveInches(Direction.FORWARD, 24, .5);
                break;
            case 2:
                drive.rotateDegrees(Direction.LEFT, 45, .4);
                drive.moveInches(Direction.BACKWARD, 27, .5);
                break;
            case 3:
                drive.rotateDegrees(Direction.LEFT, 90, .4);
                drive.moveInches(Direction.FORWARD, 24, .5);
                break;
        }
    }

    private void sleep(int milliseconds) {
        linearOpMode.sleep(milliseconds);
    }
}
