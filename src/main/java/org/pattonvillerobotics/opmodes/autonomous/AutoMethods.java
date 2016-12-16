package org.pattonvillerobotics.opmodes.autonomous;

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
    private double motorPowerLeft;
    private double motorPowerRight;

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
        linearOpMode.telemetry.addData("Init", "Complete").setRetained(true);
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

        opMode.telemetry.addData("Angle to Beacon", angleToBeacon).setRetained(true);
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

    public void alignToBeaconTest() {

        opMode.telemetry.addData("Drive", "Attempting to align to beacon.").setRetained(true);
        OpenGLMatrix lastLocation = vuforia.getNearestBeaconLocation();
        while(lastLocation == null) {
            lastLocation = vuforia.getNearestBeaconLocation();
            Thread.yield();
        }

        while(vuforia.getDistance() > 12 || vuforia.getxPos() > 5 || vuforia.getHeading() > 5) {

            double y = vuforia.getDistance();
            double x = vuforia.getxPos();
            double W = Globals.BEACON_HALF_WIDTH;

            opMode.telemetry.addData("x", x);
            opMode.telemetry.addData("y", y);

            double MPLnumerator = -2 * Math.sqrt(Math.pow(x,4) + 2*Math.pow(x,3)*W + Math.pow(W,2)*Math.pow(x,2));
            double MPLdenominator = 4*Math.pow(x,3) + 6*W*Math.pow(x,2) + 2*Math.pow(W,2)*x;
            double MPRnumerator = -2 * Math.sqrt(Math.pow(x,4)/4);
            double MPRdenominator = Math.pow(x,3);

            double rawMotorPowerLeft = MPLnumerator / MPLdenominator;
            double rawMotorPowerRight = MPRnumerator / MPRdenominator;

            double powerRatio = rawMotorPowerLeft / rawMotorPowerRight;

            motorPowerLeft = (rawMotorPowerLeft * powerRatio) / 2;
            motorPowerRight = 1 / 2;

            opMode.telemetry.addData("motorPowerLeft", motorPowerLeft);
            opMode.telemetry.addData("motorPowerRight", motorPowerRight);

            drive.leftDriveMotor.setPower(motorPowerLeft);
            drive.rightDriveMotor.setPower(motorPowerRight);

        }

    }


    public void driveToFarBeacon() {
        opMode.telemetry.addData("Auto Methods", "Driving to next "+allianceColor+" side beacon.").setRetained(true);

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
        opMode.telemetry.addData("Drive", "Driving to corner vortex").setRetained(true);

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
        opMode.telemetry.addData("Drive", "Driving to center vortex").setRetained(true);

        drive.moveInches(Direction.FORWARD, 25, Globals.MAX_MOTOR_POWER);
        opMode.sleep(3000);
        drive.moveInches(Direction.FORWARD, 5, Globals.MAX_MOTOR_POWER);
    }


    public void fireCannon() {

        opMode.telemetry.addData("Cannon", "Firing cannon.").setRetained(true);
        cannon.update(true);
        opMode.sleep(1200);
        cannon.update(false);
    }


    public void fireParticles() {

        opMode.telemetry.addData("Cannon", "Firing particles").setRetained(true);
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
        drive.rotateDegrees(defaultTurnDirection, 35, Globals.MAX_MOTOR_POWER);
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

    public void pressBeacon() {

        opMode.telemetry.addData("pressBeacon", "Detecting colors...");

        beaconLeftColor = beaconColorDetection.getLeftColor();
        beaconRightColor = beaconColorDetection.getRightColor();

        opMode.telemetry.addData("pressBeacon", "Detecting colors...");
        if(beaconLeftColor == allianceColor) {
            opMode.telemetry.addData("pressBeacon", "Moving left arm");
            // Move left arm
        }
        else if(beaconRightColor == allianceColor) {
            opMode.telemetry.addData("pressBeacon", "Moving right arm");
            // Move right arm
        }

        while(beaconLeftColor != allianceColor && beaconRightColor != allianceColor) {
            drive.moveInches(Direction.BACKWARD, 12, Globals.MAX_MOTOR_POWER);
            drive.moveInches(Direction.FORWARD, 12, Globals.MAX_MOTOR_POWER);
        }

        opMode.telemetry.update();

    }


    public void runAutonomousProcess() {

        fireParticles();
        opMode.sleep(500);
        driveToNearBeacon();
        opMode.sleep(500);
        alignToBeacon();
        opMode.sleep(500);
        pressBeacon();
        opMode.sleep(500);
        driveToFarBeacon();
        opMode.sleep(500);
        alignToBeacon();
        opMode.sleep(500);
        pressBeacon();
        //driveToEndPosition();

    }
}