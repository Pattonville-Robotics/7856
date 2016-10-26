package org.pattonvillerobotics.opmodes.autonomous;

import android.util.Log;

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

    private static final String TAG = "AutoMethods";
    private static final String ERROR_MESSAGE = "Alliance color must either be red or blue.";

    AutoMethods(EncoderDrive drive) {
        this.drive = drive;
    }

    public static void init(EncoderDrive newDrive, AllianceColor newAllianceColor, StartPosition newStartPosition) {
        allianceColor = newAllianceColor;
        drive = newDrive;
        startPosition = newStartPosition;
    }

    public static void driveToCapball() {

        if(startPosition.equals(StartPosition.LINE)) {

            drive.moveInches(Direction.FORWARD, Globals.DISTANCE1_TO_CAPBALL, Globals.MAX_MOTOR_POWER);

            if (allianceColor.equals(AllianceColor.BLUE)) {
                drive.rotateDegrees(Direction.RIGHT, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
            } else if (allianceColor.equals(AllianceColor.RED)) {
                drive.rotateDegrees(Direction.LEFT, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
            } else {
                Log.e(TAG, ERROR_MESSAGE);
            }

            drive.moveInches(Direction.FORWARD, Globals.DISTANCE2_TO_CAPBALL, Globals.MAX_MOTOR_POWER);

        }

        else if(startPosition.equals(StartPosition.VORTEX)) {

            drive.moveInches(Direction.FORWARD, Globals.STRAIGHT_DISTANCE_TO_CAPBALL, Globals.MAX_MOTOR_POWER);

        }

        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

    }

    public static void driveToBeacon() {

        drive.moveInches(Direction.FORWARD, Globals.DISTANCE1_TO_BEACON, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.RIGHT, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.LEFT, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

        drive.moveInches(Direction.FORWARD, Globals.DISTANCE2_TO_BEACON, Globals.MAX_MOTOR_POWER);

    }

    public static void detectColor() {

        leftColor = beaconColorDetection.getLeftColor();
        rightColor = beaconColorDetection.getRightColor();

    }

    public static void pressBeacon() {

        if(leftColor.equals(ColorSensorColor.BLUE) && allianceColor.equals(AllianceColor.BLUE)) {
            armMover.moveTo(ArmMover.Position.LEFT);
        }
        else if(leftColor.equals(ColorSensorColor.RED) && allianceColor.equals(AllianceColor.RED)) {
            armMover.moveTo(ArmMover.Position.LEFT);
        }
        else if(rightColor.equals(ColorSensorColor.BLUE) && allianceColor.equals(AllianceColor.BLUE)) {
            armMover.moveTo(ArmMover.Position.RIGHT);
        }
        else if(rightColor.equals(ColorSensorColor.RED) && allianceColor.equals(AllianceColor.RED)) {
            armMover.moveTo(ArmMover.Position.RIGHT);
        }
        else {
            armMover.moveTo(ArmMover.Position.DEFAULT);
        }

    }

    public static void alignToBeacon() {

        OpenGLMatrix lastLocation = VuforiaNav.getNearestBeaconLocation();

        while(VuforiaNav.getDistance(lastLocation) > Globals.MINIMUM_DISTANCE_TO_BEACON) {

            lastLocation = VuforiaNav.getNearestBeaconLocation();
            double offset = VuforiaNav.getxPos(lastLocation);

            if(offset > Globals.BEACON_MAXIMUM_OFFSET) {
                drive.rotateDegrees(Direction.LEFT, Globals.BEACON_ALIGN_TURN, Globals.HALF_MOTOR_POWER);
            }
            else if(offset < Globals.BEACON_MINIMUM_OFFSET) {
                drive.rotateDegrees(Direction.RIGHT, Globals.BEACON_ALIGN_TURN, Globals.HALF_MOTOR_POWER);
            }
            else {
                Log.e(TAG, ERROR_MESSAGE);
            }

        }

    }

    public static void driveToNextBeacon() {

        drive.moveInches(Direction.BACKWARD, Globals.BEACON_BACKUP_DISTANCE, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.RIGHT, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.LEFT, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

        drive.moveInches(Direction.FORWARD, Globals.DISTANCE_TO_NEXT_BEACON, Globals.MAX_MOTOR_POWER);

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

        drive.moveInches(Direction.BACKWARD, Globals.BEACON_BACKUP_DISTANCE, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.RIGHT, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.LEFT, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

        drive.moveInches(Direction.FORWARD, Globals.DISTANCE_TO_CORNER_VORTEX, Globals.MAX_MOTOR_POWER);

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

        drive.moveInches(Direction.FORWARD, Globals.DISTANCE_TO_CLIMB_CORNER_VORTEX, Globals.MAX_MOTOR_POWER);

    }

    public static void driveToNearBeacon() {

        drive.rotateDegrees(Direction.RIGHT, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        drive.moveInches(Direction.FORWARD, Globals.DISTANCE_TO_NEAR_BEACON, Globals.MAX_MOTOR_POWER);

    }

    public static void runProcessCBV() {

        AutoMethods.driveToCapball();
        AutoMethods.driveToNearBeacon();
        AutoMethods.detectColor();
        AutoMethods.alignToBeacon();
        AutoMethods.pressBeacon();
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
