package org.pattonvillerobotics.opmodes.autonomous;

import android.graphics.Bitmap;
import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.colordetection.BeaconColorDetection;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav;
import org.pattonvillerobotics.commoncode.vision.util.ScreenOrientation;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.robotclasses.mechanisms.ArmMover;
import org.pattonvillerobotics.robotclasses.mechanisms.Cannon;

/**
 * Created by murphyk01 on 10/1/16.
 */

public class AutoMethods {

    private final String TAG = "AutoMethods";
    private final String ERROR_MESSAGE = "Invalid parameter passed";
    private EncoderDrive drive;
    private AllianceColor allianceColor;
    private StartPosition startPosition;
    private EndPosition endPosition;
    private ArmMover armMover;
    private AllianceColor beaconLeftColor;
    private AllianceColor beaconRightColor;
    private BeaconColorDetection beaconColorDetection;
    private Direction defaultTurnDirection;
    private VuforiaNav vuforia;
    private LinearOpMode opMode;
    private HardwareMap hardwareMap;
    private Cannon cannon;

    public AutoMethods(EncoderDrive newDrive, AllianceColor newAllianceColor, StartPosition newStartPosition, EndPosition newEndPosition, HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        this.hardwareMap = hardwareMap;
        opMode = linearOpMode;
        setAllianceColor(newAllianceColor);
        drive = newDrive;
        startPosition = newStartPosition;
        endPosition = newEndPosition;
        beaconColorDetection = new BeaconColorDetection(hardwareMap);
        vuforia = new VuforiaNav(CustomizedRobotParameters.VUFORIA_PARAMETERS);
        vuforia.activate();
        armMover = new ArmMover(hardwareMap, linearOpMode);
        cannon = new Cannon(hardwareMap, opMode);
        linearOpMode.telemetry.addData("Init", "Complete");
    }

    private void setAllianceColor(AllianceColor newAllianceColor) {
        allianceColor = newAllianceColor;
        if(newAllianceColor == AllianceColor.BLUE) {
            defaultTurnDirection = Direction.RIGHT;
        } else {
            defaultTurnDirection = Direction.LEFT;
        }
        opMode.telemetry.addData("Setup", "Setting default turn direction to:" + defaultTurnDirection);
    }

    public void detectColor() {

        opMode.telemetry.addData("Auto Methods", "Detecting Colors...");
        Bitmap bm = vuforia.getImage();
        if(bm != null) {
            beaconColorDetection.analyzeFrame(bm, ScreenOrientation.LANDSCAPE_REVERSE);
            bm.recycle();
            beaconLeftColor = beaconColorDetection.getLeftColor();
            beaconRightColor = beaconColorDetection.getRightColor();
        }

    }




    public void alignToBeacon() {

        opMode.telemetry.addData("Auto Methods", "Attempting to align to beacon.");
        OpenGLMatrix lastLocation = vuforia.getNearestBeaconLocation();
        while(lastLocation == null) {
            lastLocation = vuforia.getNearestBeaconLocation();
            Thread.yield();
        }
        double angleToBeacon = vuforia.getAngle();

        double x = vuforia.getxPos();
        double y = vuforia.getDistance();
        double Q = Globals.MINIMUM_DISTANCE_TO_BEACON;
        double d = Math.sqrt(Math.pow(x, 2) + Math.pow((y - Q), 2));
        double angleToTurn = FastMath.toDegrees(FastMath.atan(y/x) - FastMath.atan((y-Q)/x) );

        opMode.telemetry.addData("Angle to Turn", angleToTurn);
        opMode.telemetry.addData("d", d);
        opMode.telemetry.addData("x", x);
        opMode.telemetry.addData("y", y);
        opMode.telemetry.update();
        opMode.sleep(15000);

        if (angleToBeacon > 0) {
            drive.rotateDegrees(Direction.LEFT, angleToTurn, Globals.MAX_MOTOR_POWER);
            opMode.sleep(5000);
        } else {
            drive.rotateDegrees(Direction.RIGHT, -angleToTurn, Globals.MAX_MOTOR_POWER);
            opMode.sleep(5000);
        }
        drive.moveInches(Direction.BACKWARD, d, Globals.MAX_MOTOR_POWER);
        opMode.sleep(5000);
        double adjustmentAngle = vuforia.getHeading();
        opMode.telemetry.addData("Adjustment Angle", adjustmentAngle);
        drive.rotateDegrees(defaultTurnDirection, adjustmentAngle, Globals.MAX_MOTOR_POWER);
        opMode.sleep(5000);
    }


    public void driveToNextBeacon() {

        opMode.telemetry.addData("Auto Methods", "Driving to next "+allianceColor+" side beacon.");

        drive.moveInches(Direction.FORWARD, Globals.BEACON_BACKUP_DISTANCE, Globals.MAX_MOTOR_POWER);
        drive.rotateDegrees(defaultTurnDirection, Globals.RIGHT_TURN, Globals.MAX_MOTOR_POWER);
        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE_TO_NEXT_BEACON, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.LEFT, Globals.RIGHT_TURN, Globals.MAX_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.RIGHT, Globals.RIGHT_TURN, Globals.MAX_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }
    }

    public void driveToCornerVortex() {
        drive.moveInches(Direction.FORWARD, Globals.BEACON_BACKUP_DISTANCE, Globals.MAX_MOTOR_POWER);
        drive.rotateDegrees(defaultTurnDirection, Globals.RIGHT_TURN, Globals.MAX_MOTOR_POWER);
        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE_TO_CORNER_VORTEX, Globals.MAX_MOTOR_POWER);

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.LEFT, Globals.HALF_TURN, Globals.MAX_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.RIGHT, Globals.HALF_TURN, Globals.MAX_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }
    }

    public void driveToCenterVortex() {
        // drive from far beacon to center
    }

    public void fireCannon() {

        opMode.telemetry.addData("Cannon", "Firing cannon.");
        cannon.update(true);
        opMode.sleep(2000);
        cannon.update(false);
    }

    public void climbCornerVortex() {
        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE_TO_CLIMB_CORNER_VORTEX, Globals.MAX_MOTOR_POWER);
    }

    public void driveToNearBeacon() {
        opMode.telemetry.addData("Drive to Near Beacon", "Driving");
        drive.rotateDegrees(defaultTurnDirection, Globals.HALF_TURN, Globals.MAX_MOTOR_POWER);
        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE_TO_NEAR_BEACON, Globals.MAX_MOTOR_POWER);
    }

    public void driveToFarBeacon() {
        // drive to far beacon
    }

    public void driveToEndPosition() {
        if(endPosition.equals(EndPosition.CENTER_VORTEX)) {
            // drive from far beacon to center vortex
        }
        else if(endPosition.equals(EndPosition.CORNER_VORTEX)) {
            driveToCornerVortex();
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }
    }

    public void runAutonomousProcess() {

        fireCannon();
        driveToNearBeacon();
        alignToBeacon();
        pressBeacon();
        driveToFarBeacon();
        alignToBeacon();
        pressBeacon();
        driveToEndPosition();

    }
}