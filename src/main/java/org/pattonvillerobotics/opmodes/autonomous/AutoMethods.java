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
import org.pattonvillerobotics.opmodes.teleop.MainTeleOp;
import org.pattonvillerobotics.robotclasses.mechanisms.Cannon;
import org.pattonvillerobotics.robotclasses.mechanisms.Hopper;

/**
 * Created by murphyk01 on 10/1/16.
 */

public class AutoMethods {

    private final String TAG = "AutoMethods";
    private final String ERROR_MESSAGE = "Invalid parameter passed";
    private EncoderDrive drive;
    private AllianceColor allianceColor;
    private EndPosition endPosition;
    private AllianceColor beaconLeftColor;
    private AllianceColor beaconRightColor;
    private BeaconColorDetection beaconColorDetection;
    private Direction defaultTurnDirection;
    private Direction oppositeTurnDirection;
    private VuforiaNav vuforia;
    private LinearOpMode opMode;
    private HardwareMap hardwareMap;
    private Cannon cannon;
    private Hopper hopper;

    public AutoMethods(EncoderDrive newDrive, AllianceColor newAllianceColor, EndPosition newEndPosition, HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        this.hardwareMap = hardwareMap;
        opMode = linearOpMode;
        setAllianceColor(newAllianceColor);
        drive = newDrive;
        endPosition = newEndPosition;
        beaconColorDetection = new BeaconColorDetection(hardwareMap);
        vuforia = new VuforiaNav(CustomizedRobotParameters.VUFORIA_PARAMETERS);
        vuforia.activate();
        cannon = new Cannon(hardwareMap, opMode);
        hopper = new Hopper(hardwareMap, opMode);
        linearOpMode.telemetry.addData("Init", "Complete");
    }


    private void setAllianceColor(AllianceColor newAllianceColor) {
        allianceColor = newAllianceColor;
        if(newAllianceColor == AllianceColor.BLUE) {
            defaultTurnDirection = Direction.RIGHT;
            oppositeTurnDirection = Direction.LEFT;
        } else {
            defaultTurnDirection = Direction.LEFT;
            oppositeTurnDirection = Direction.RIGHT;
        }
        opMode.telemetry.addData("Setup", "Setting default turn direction to:" + defaultTurnDirection).setRetained(true);
    }


    public void detectColor() {

        opMode.telemetry.addData("Auto Methods", "Detecting Colors...").setRetained(true);
        Bitmap bm = null;
        while(bm == null) {
            bm = vuforia.getImage();
        }
        beaconColorDetection.analyzeFrame(bm, ScreenOrientation.LANDSCAPE_REVERSE);
        bm.recycle();
        beaconLeftColor = beaconColorDetection.getLeftColor();
        beaconRightColor = beaconColorDetection.getRightColor();
        opMode.telemetry.addData("Color", beaconLeftColor + " " + beaconRightColor).setRetained(true);
        opMode.telemetry.update();

    }


    public void alignToBeacon() {

        opMode.telemetry.addData("Drive", "Attempting to align to beacon.").setRetained(true);
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
        double angleToTurn = FastMath.toDegrees(FastMath.atan(y/x) - FastMath.atan((y-Q)/x));
        double adjustmentAngle = FastMath.toDegrees(FastMath.atan( x/(y-Q) ));
        //double adjustmentAngle = FastMath.toDegrees(FastMath.asin(x/d));

        opMode.telemetry.addData("Angle to Turn", angleToTurn).setRetained(true);
        opMode.telemetry.addData("Adjustment Angle", adjustmentAngle).setRetained(true);
        opMode.telemetry.addData("d", d).setRetained(true);
        opMode.telemetry.addData("x", x).setRetained(true);
        opMode.telemetry.addData("y", y).setRetained(true);
        opMode.telemetry.update();

        if (angleToBeacon > 0) {
            drive.rotateDegrees(oppositeTurnDirection, angleToTurn, Globals.MAX_MOTOR_POWER);
        }
        else {
            drive.rotateDegrees(defaultTurnDirection, -angleToTurn, Globals.MAX_MOTOR_POWER);
        }

        drive.moveInches(Direction.BACKWARD, d + 5.5, Globals.MAX_MOTOR_POWER);
        drive.rotateDegrees(defaultTurnDirection, adjustmentAngle, Globals.MAX_MOTOR_POWER);

        if(vuforia.getHeading() > 5) {
            drive.rotateDegrees(defaultTurnDirection, vuforia.getHeading(), Globals.MAX_MOTOR_POWER);
        }
        else if(vuforia.getHeading() < -5) {
            drive.rotateDegrees(oppositeTurnDirection, vuforia.getHeading(), Globals.MAX_MOTOR_POWER);
        }

    }


    public void driveToFarBeacon() {
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
        opMode.telemetry.addData("Drive", "Driving to corner vortex");

        if(allianceColor.equals(AllianceColor.BLUE)) {
            drive.rotateDegrees(Direction.RIGHT, Globals.RIGHT_TURN, Globals.MAX_MOTOR_POWER);
        }
        else if(allianceColor.equals(AllianceColor.RED)) {
            drive.rotateDegrees(Direction.LEFT, Globals.RIGHT_TURN, Globals.MAX_MOTOR_POWER);
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }
        drive.moveInches(Direction.BACKWARD, 48, Globals.MAX_MOTOR_POWER);
    }


    public void driveToCenterVortex() {
        opMode.telemetry.addData("Drive", "Driving to center vortex");

        drive.moveInches(Direction.FORWARD, 25, Globals.MAX_MOTOR_POWER);
        opMode.sleep(3000);
        drive.moveInches(Direction.FORWARD, 5, Globals.MAX_MOTOR_POWER);
    }


    public void fireCannon() {

        opMode.telemetry.addData("Cannon", "Firing cannon.");
        cannon.update(true);
        opMode.sleep(1200);
        cannon.update(false);
    }


    public void fireParticles() {

        opMode.telemetry.addData("Cannon", "Firing particles");
        drive.moveInches(Direction.BACKWARD, 7.5, Globals.MAX_MOTOR_POWER);
        fireCannon();
        hopper.update(true, MainTeleOp.Direction.IN);
        opMode.sleep(3500);
        hopper.update(false, MainTeleOp.Direction.IN);
        fireCannon();

    }


    public void driveToNearBeacon() {
        opMode.telemetry.addData("Drive", "Driving to near beacon").setRetained(true);
        drive.moveInches(Direction.BACKWARD, 4, Globals.MAX_MOTOR_POWER);
        drive.rotateDegrees(defaultTurnDirection, 35, Globals.HALF_MOTOR_POWER);
        drive.moveInches(Direction.BACKWARD, 50, Globals.MAX_MOTOR_POWER);
    }


    public void driveToEndPosition() {
        if(endPosition.equals(EndPosition.CENTER_VORTEX)) {
            driveToCenterVortex();
        }
        else if(endPosition.equals(EndPosition.CORNER_VORTEX)) {
            driveToCornerVortex();
        }
        else {
            Log.e(TAG, ERROR_MESSAGE);
        }
    }

    public void moveLeft() {
        drive.rotateDegrees(Direction.LEFT, Globals.RIGHT_TURN, Globals.MAX_MOTOR_POWER);
        drive.moveInches(Direction.BACKWARD, 4, Globals.MAX_MOTOR_POWER);
        drive.rotateDegrees(Direction.RIGHT, Globals.RIGHT_TURN, Globals.MAX_MOTOR_POWER);
        drive.moveInches(Direction.BACKWARD, 29, Globals.MAX_MOTOR_POWER);
    }

    public void moveRight() {
        drive.rotateDegrees(Direction.RIGHT, Globals.RIGHT_TURN, Globals.MAX_MOTOR_POWER);
        drive.moveInches(Direction.BACKWARD, 4, Globals.MAX_MOTOR_POWER);
        drive.rotateDegrees(Direction.LEFT, Globals.RIGHT_TURN, Globals.MAX_MOTOR_POWER);
        drive.moveInches(Direction.BACKWARD, 29, Globals.MAX_MOTOR_POWER);
    }

    public void pressBeacon() {
        detectColor();
        if(beaconLeftColor.equals(allianceColor)) {
            opMode.telemetry.addData("Press beacon", "Pressing left side...");
            opMode.telemetry.update();
            moveLeft();

        }
        else if(beaconRightColor.equals(allianceColor)) {
            opMode.telemetry.addData("Press beacon", "Pressing right side...");
            opMode.telemetry.update();
            moveRight();
        }
        else {
            opMode.telemetry.addData("Press beacon", "Error detected");
            opMode.telemetry.update();
        }
    }

    public void ramBeacon(){

        opMode.telemetry.addData("Driving", "Attempting to press button");
        OpenGLMatrix lastLocation = vuforia.getNearestBeaconLocation();
        while(lastLocation == null) {
            lastLocation = vuforia.getNearestBeaconLocation();
            Thread.yield();
        }
//        while(beaconLeftColor != allianceColor || beaconRightColor != allianceColor) {
//            drive.moveInches(Direction.BACKWARD, vuforia.getDistance(), Globals.HALF_MOTOR_POWER);
//            drive.moveInches(Direction.FORWARD, 24, Globals.HALF_MOTOR_POWER);
//            detectColor();
//        }
        drive.moveInches(Direction.BACKWARD, vuforia.getDistance(), 0.2);
        opMode.sleep(1000);
        drive.moveInches(Direction.FORWARD, Globals.MINIMUM_DISTANCE_TO_BEACON, Globals.HALF_MOTOR_POWER);
    }


    public void runAutonomousProcess() {
        fireParticles();
        opMode.sleep(500);
        driveToNearBeacon();
        opMode.sleep(1000);
        alignToBeacon();
        opMode.sleep(1000);
        pressBeacon();
        //ramBeacon();
        opMode.sleep(1000);
        driveToEndPosition();

    }

}