package org.pattonvillerobotics.opmodes.autonomous;

import android.util.Log;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.robotclasses.vuforia.VuforiaNav;

/**
 * Created by murphyk01 on 10/1/16.
 */

public class AutoMethods {

    private static EncoderDrive drive;
    private static AllianceColor allianceColor;
    private static final String TAG = "AutoMethods";
    private static final String ERROR_MESSAGE = "Alliance color must either be red or blue.";

    AutoMethods(EncoderDrive drive) {
        this.drive = drive;
    }

    public static void init(EncoderDrive newDrive, AllianceColor newAllianceColor) {
        allianceColor = newAllianceColor;
        drive = newDrive;
    }

    public static void driveToCapball() {

        drive.moveInches(Direction.FORWARD, Globals.DISTANCE1_TO_CAPBALL, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.FORWARD, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.BACKWARD, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

        drive.moveInches(Direction.FORWARD, Globals.DISTANCE2_TO_CAPBALL, Globals.MAX_MOTOR_POWER);

    }

    public static void driveToBeacon() {

        drive.moveInches(Direction.FORWARD, Globals.DISTANCE1_TO_BEACON, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.FORWARD, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.BACKWARD, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

        drive.moveInches(Direction.FORWARD, Globals.DISTANCE2_TO_BEACON, Globals.MAX_MOTOR_POWER);

    }

    public static void alignToBeacon() {

        OpenGLMatrix lastLocation = VuforiaNav.getNearestBeaconLocation();

        while(VuforiaNav.getDistance(lastLocation) > Globals.MINIMUM_DISTANCE_TO_BEACON) {

            lastLocation = VuforiaNav.getNearestBeaconLocation();
            double offset = VuforiaNav.getxPos(lastLocation);

            if(offset > Globals.BEACON_MAXIMUM_OFFSET) {
                drive.rotateDegrees(Direction.BACKWARD, Globals.BEACON_ALIGN_TURN, Globals.HALF_MOTOR_POWER);
            }
            else if(offset < Globals.BEACON_MINIMUM_OFFSET) {
                drive.rotateDegrees(Direction.FORWARD, Globals.BEACON_ALIGN_TURN, Globals.HALF_MOTOR_POWER);
            }
            else {
                Log.e(TAG, ERROR_MESSAGE);
            }

        }

    }

    public static void driveToNextBeacon() {

        drive.moveInches(Direction.BACKWARD, Globals.BEACON_BACKUP_DISTANCE, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.FORWARD, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.BACKWARD, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

        drive.moveInches(Direction.FORWARD, Globals.DISTANCE_TO_NEXT_BEACON, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.BACKWARD, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.FORWARD, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

    }

    public static void driveToCornerVortex() {

        drive.moveInches(Direction.BACKWARD, Globals.BEACON_BACKUP_DISTANCE, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.FORWARD, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.BACKWARD, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

        drive.moveInches(Direction.FORWARD, Globals.DISTANCE_TO_CORNER_VORTEX, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.BACKWARD, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.FORWARD, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }

    }

    public static void climbCornerVortex() {

        drive.moveInches(Direction.FORWARD, Globals.DISTANCE_TO_CLIMB_CORNER_VORTEX, Globals.MAX_MOTOR_POWER);

    }

}
