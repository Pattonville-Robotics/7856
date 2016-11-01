package org.pattonvillerobotics.opmodes.autonomous;

import android.graphics.Bitmap;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
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
    private static AllianceColor beaconLeftColor;
    private static BeaconColorDetection beaconColorDetection;
    private static Direction defaultTurnDirection;
    private static VuforiaNav vuforia;
    private static LinearOpMode opMode;

    private static final String TAG = "AutoMethods";
    private static final String ERROR_MESSAGE = "Alliance color must either be red or blue.";

    AutoMethods(EncoderDrive drive) {
        this.drive = drive;
    }

    public static void init(EncoderDrive newDrive, AllianceColor newAllianceColor, StartPosition newStartPosition, HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        setAllianceColor(newAllianceColor);
        drive = newDrive;
        startPosition = newStartPosition;
        beaconColorDetection = new BeaconColorDetection(hardwareMap);
        vuforia = new VuforiaNav(CustomizedRobotParameters.VUFORIA_PARAMETERS);
        armMover = new ArmMover(hardwareMap, linearOpMode);
        opMode = linearOpMode;
    }

    private static void setAllianceColor(AllianceColor newAllianceColor) {
        allianceColor = newAllianceColor;
        if(newAllianceColor == AllianceColor.BLUE) {
            defaultTurnDirection = Direction.RIGHT;
        } else {
            defaultTurnDirection = Direction.LEFT;
        }
    }

    public static void driveToCapball() {
        if(startPosition == StartPosition.LINE) {
            drive.moveInches(Direction.BACKWARD, Globals.DISTANCE1_TO_CAPBALL, Globals.MAX_MOTOR_POWER);
            drive.rotateDegrees(defaultTurnDirection, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
            drive.moveInches(Direction.BACKWARD, Globals.DISTANCE2_TO_CAPBALL, Globals.MAX_MOTOR_POWER);
        } else if(startPosition == StartPosition.VORTEX) {
            drive.moveInches(Direction.BACKWARD, Globals.STRAIGHT_DISTANCE_TO_CAPBALL, Globals.MAX_MOTOR_POWER);
        } else {
            Log.e(TAG, ERROR_MESSAGE);
        }

    }

    public static void driveToBeacon() {
        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE1_TO_BEACON, Globals.MAX_MOTOR_POWER);
        drive.rotateDegrees(defaultTurnDirection, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE2_TO_BEACON, Globals.MAX_MOTOR_POWER);
    }

    public static void detectColor() {
        Bitmap bm = vuforia.getImage();
        if(bm != null) {
            beaconColorDetection.analyzeFrame(bm);
            bm.recycle();
            beaconLeftColor = beaconColorDetection.getLeftColor();
        }
    }

    public static void pressBeacon() {
        if(beaconLeftColor == allianceColor) {
            armMover.moveTo(ArmMover.Position.LEFT);
        } else {
            armMover.moveTo(ArmMover.Position.RIGHT);
        }
        // move robot to press button
    }

    public static void alignToBeacon() {
        vuforia.getNearestBeaconLocation();
        double angleToBeacon = vuforia.getAngle();

        double x = vuforia.getxPos();
        double y = vuforia.getDistance();
        double Q = Globals.Q_DISTANCE;
        double d = Math.sqrt(Math.pow(x, 2) + Math.pow((y - Q), 2));
        double c = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
        double angleToTurn = FastMath.toDegrees(FastMath.acos((Math.pow(d, 2) + Math.pow(c, 2) - Math.pow(Q, 2)) / (2 * d * c)));

        if (angleToBeacon > 0) {
            drive.rotateDegrees(Direction.LEFT, angleToTurn, Globals.HALF_MOTOR_POWER);
        } else {
            drive.rotateDegrees(Direction.RIGHT, -angleToTurn, Globals.HALF_MOTOR_POWER);
        }
        drive.moveInches(Direction.BACKWARD, d, Globals.HALF_MOTOR_POWER);
        drive.rotateDegrees(defaultTurnDirection, 180-angleToTurn, Globals.HALF_MOTOR_POWER);
    }

    public static void driveToNextBeacon() {

        drive.moveInches(Direction.FORWARD, Globals.BEACON_BACKUP_DISTANCE, Globals.MAX_MOTOR_POWER);
        drive.rotateDegrees(defaultTurnDirection, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
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
        drive.rotateDegrees(defaultTurnDirection, Globals.RIGHT_TURN, Globals.HALF_MOTOR_POWER);
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

        drive.rotateDegrees(defaultTurnDirection, Globals.HALF_TURN, Globals.HALF_MOTOR_POWER);
        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE_TO_NEAR_BEACON, Globals.MAX_MOTOR_POWER);

    }

    public static void runProcessCBV() {
        vuforia.activate();
        AutoMethods.driveToCapball();
        AutoMethods.driveToNearBeacon();
        AutoMethods.alignToBeacon();
    }
}
