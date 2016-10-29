package org.pattonvillerobotics.opmodes.autonomous;

import android.graphics.Bitmap;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.ColorSensorColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.robotclasses.colordetection.BeaconColorDetection;
import org.pattonvillerobotics.robotclasses.mechanisms.ArmMover;
import org.pattonvillerobotics.robotclasses.vuforia.VuforiaNav;

/**
 * Created by murphyk01 on 10/1/16.
 */

public class AutoMethods {

    private static EncoderDrive drive;
    private static AllianceColor allianceColor;
    private static StartPosition startPosition;
    private static ArmMover armMover;
    private static ColorSensorColor leftColor;
    private static ColorSensorColor rightColor;
    private static BeaconColorDetection beaconColorDetection;
    private static LinearOpMode opMode;

    private static final String TAG = "AutoMethods";
    private static final String ERROR_MESSAGE = "Alliance color must either be red or blue.";

    AutoMethods(EncoderDrive drive) {
        this.drive = drive;
    }

    public static void init(EncoderDrive newDrive, AllianceColor newAllianceColor, StartPosition newStartPosition, LinearOpMode linearOpMode, HardwareMap hardwareMap) {
        allianceColor = newAllianceColor;
        drive = newDrive;
        startPosition = newStartPosition;
        opMode = linearOpMode;
        beaconColorDetection = new BeaconColorDetection(hardwareMap);
    }

    public static void driveToCapball() {

        if(startPosition.equals(StartPosition.LINE)) {

            drive.moveInches(Direction.BACKWARD, Globals.DISTANCE1_TO_CAPBALL, Globals.MAX_MOTOR_POWER);

            if (allianceColor.equals(AllianceColor.BLUE)) {
                drive.rotateDegrees(Direction.RIGHT, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
            } else if (allianceColor.equals(AllianceColor.RED)) {
                drive.rotateDegrees(Direction.LEFT, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
            } else {
                Log.e(TAG, ERROR_MESSAGE);
            }

            drive.moveInches(Direction.BACKWARD, Globals.DISTANCE2_TO_CAPBALL, Globals.MAX_MOTOR_POWER);

        }

        else if(startPosition.equals(StartPosition.VORTEX)) {

            drive.moveInches(Direction.BACKWARD, Globals.STRAIGHT_DISTANCE_TO_CAPBALL, Globals.MAX_MOTOR_POWER);

        }

        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

    }

    public static void driveToBeacon() {

        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE1_TO_BEACON, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.RIGHT, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.LEFT, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE2_TO_BEACON, Globals.MAX_MOTOR_POWER);

    }

    public static void detectColor() {
        Bitmap bm = VuforiaNav.getImage();
        if(bm != null) {
            opMode.telemetry.addData("Colors", beaconColorDetection.analyzeFrame(bm).getColorString());
            bm.recycle();
            leftColor = beaconColorDetection.getLeftColor();
            rightColor = beaconColorDetection.getRightColor();
        }
    }

    public static void pressBeacon() {

//        if(leftColor.equals(ColorSensorColor.BLUE) && allianceColor.equals(AllianceColor.BLUE)) {
//            armMover.moveTo(ArmMover.Position.LEFT);
//        }
//        else if(leftColor.equals(ColorSensorColor.RED) && allianceColor.equals(AllianceColor.RED)) {
//            armMover.moveTo(ArmMover.Position.LEFT);
//        }
//        else if(rightColor.equals(ColorSensorColor.BLUE) && allianceColor.equals(AllianceColor.BLUE)) {
//            armMover.moveTo(ArmMover.Position.RIGHT);
//        }
//        else if(rightColor.equals(ColorSensorColor.RED) && allianceColor.equals(AllianceColor.RED)) {
//            armMover.moveTo(ArmMover.Position.RIGHT);
//        }
//        else {
//            armMover.moveTo(ArmMover.Position.DEFAULT);
//        }

    }

    public static void alignToBeacon() {

        boolean aligned = false;
        OpenGLMatrix lastLocation;

        while (!aligned) {

            lastLocation = VuforiaNav.getNearestBeaconLocation();
            double angleToBeacon = VuforiaNav.getAngle(lastLocation);

            double x = VuforiaNav.getxPos(lastLocation);
            double y = VuforiaNav.getDistance(lastLocation);
            double Q = Globals.Q_DISTANCE;
            double d = Math.sqrt( Math.pow(x, 2) + Math.pow((y - Q), 2) );
            double c = Math.sqrt( Math.pow(x, 2) + Math.pow(y, 2) );
            double angleToTurn = Math.acos( (Math.pow(d, 2) + Math.pow(c, 2) + Math.pow(Q, 2)) / (2*d*c) );

            if(angleToBeacon > 0) {
                drive.rotateDegrees(Direction.LEFT, angleToTurn, Globals.HALF_MOTOR_POWER);
            }
            else if(angleToBeacon < 0) {
                drive.rotateDegrees(Direction.RIGHT, angleToTurn, Globals.HALF_MOTOR_POWER);
            }
            else {
                drive.rotateDegrees(Direction.RIGHT, 0, Globals.HALF_MOTOR_POWER);
            }

            if (lastLocation != null) {
                double offset = VuforiaNav.getxPos(lastLocation);

                if (offset > Globals.BEACON_MAXIMUM_OFFSET) {
                    drive.rightDriveMotor.setPower(Globals.HALF_MOTOR_POWER);
                } else if (offset < Globals.BEACON_MINIMUM_OFFSET) {
                    drive.leftDriveMotor.setPower(Globals.HALF_MOTOR_POWER);
                } else {
                    aligned = true;
                }

            }

        }

    }

    public static void driveToNextBeacon() {

        drive.moveInches(Direction.FORWARD, Globals.BEACON_BACKUP_DISTANCE, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.RIGHT, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.LEFT, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE_TO_NEXT_BEACON, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.LEFT, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.RIGHT, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

    }

    public static void driveToCornerVortex() {

        drive.moveInches(Direction.FORWARD, Globals.BEACON_BACKUP_DISTANCE, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.RIGHT, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.LEFT, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE_TO_CORNER_VORTEX, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.LEFT, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.RIGHT, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

    }

    public static void climbCornerVortex() {

        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE_TO_CLIMB_CORNER_VORTEX, Globals.MAX_MOTOR_POWER);

    }

    public static void driveToNearBeacon() {

        drive.rotateDegrees(Direction.RIGHT, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE_TO_NEAR_BEACON, Globals.MAX_MOTOR_POWER);

    }

    public static void runProcessCBV() {

        AutoMethods.driveToCapball();
        AutoMethods.driveToNearBeacon();
        //AutoMethods.detectColor();
        AutoMethods.alignToBeacon();
/*        AutoMethods.pressBeacon();
        AutoMethods.driveToCornerVortex();
        AutoMethods.climbCornerVortex();

        /*
        AutoMethods.driveToCapball();
        AutoMethods.driveToBeacon();
        AutoMethods.detectColor();
        AutoMethods.alignToBeacon();
        AutoMethods.pressBeacon();
        armMover.moveTo(ArmMover.Position.LEFT);
        AutoMethods.driveToNextBeacon();
        AutoMethods.detectColor();
        AutoMethods.alignToBeacon();
        AutoMethods.pressBeacon();
        AutoMethods.driveToCornerVortex();
        AutoMethods.climbCornerVortex();
        */
    }

}
