package org.pattonvillerobotics.opmodes.autonomous;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.BeaconColorSensor;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav;
import org.pattonvillerobotics.enums.EndPosition;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.robotclasses.mechanisms.BallQueue;
import org.pattonvillerobotics.robotclasses.mechanisms.ButtonPresser;
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
    private BeaconColorSensor beaconColorSensor;
    private AllianceColor beaconColor;
    private Direction defaultTurnDirection;
    private Direction oppositeTurnDirection;
    private VuforiaNav vuforia;
    private LinearOpMode opMode;
    private HardwareMap hardwareMap;
    private Cannon cannon;
    private Hopper hopper;
    private ButtonPresser buttonPresser;
    private BallQueue ballQueue;
    private double motorPowerLeft;
    private double motorPowerRight;

    /**
     * sets up an AutoMethods object for performing various autonomous processes
     *
     * @param newDrive         an {@see EncoderDrive}
     * @param newAllianceColor an {@see AllianceColor}
     * @param newEndPosition   an {@see EndPosition}
     * @param hardwareMap      a hardwaremap
     * @param linearOpMode     a linearopmode
     */
    public AutoMethods(EncoderDrive newDrive, AllianceColor newAllianceColor, EndPosition newEndPosition, HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        this.hardwareMap = hardwareMap;
        opMode = linearOpMode;
        setAllianceColor(newAllianceColor);
        drive = newDrive;
        endPosition = newEndPosition;
        vuforia = new VuforiaNav(CustomizedRobotParameters.VUFORIA_PARAMETERS);
        vuforia.activate();
        beaconColorSensor = new BeaconColorSensor(hardwareMap.colorSensor.get("color_sensor"));
        cannon = new Cannon(hardwareMap, opMode);
        hopper = new Hopper(hardwareMap, opMode);
        buttonPresser = new ButtonPresser(hardwareMap, opMode);
        ballQueue = new BallQueue(hardwareMap, opMode);
        ballQueue.setBallQueueIn();
        buttonPresser.setLeftIn();
        buttonPresser.setRightIn();
        telemetry("INIT", "Completed");
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
        telemetry("Setup", "Setting default turn direction to:" + defaultTurnDirection);
    }


    public void alignToBeacon() {
        if(vuforia == null) {
            throw new IllegalStateException("Must be using vuforia in order to use alignToBeacon.");
        }

        telemetry("Drive", "Attempting to align to beacon.");
        OpenGLMatrix lastLocation = vuforia.getNearestBeaconLocation();
        while(lastLocation == null) {
            lastLocation = vuforia.getNearestBeaconLocation();
            Thread.yield();
        }

        // Vuforia calculations documented in notebook
        double x = FastMath.abs(vuforia.getYPos());
        double y = vuforia.getXPos();
        double Q = Globals.MINIMUM_DISTANCE_TO_BEACON;
        double d = Math.sqrt(Math.pow(x, 2) + Math.pow((y - Q), 2));
        double angleToTurn = (FastMath.toDegrees(FastMath.atan(y/x) - FastMath.atan((y-Q)/x)))/2;
        double adjustmentAngle = FastMath.toDegrees(FastMath.atan( x/(y-Q) ));

        telemetry("Angle to Turn", angleToTurn);
        telemetry("Adjustment Angle", adjustmentAngle);
        telemetry("First Heading", vuforia.getOrientation());
        telemetry("d", d);
        telemetry("x", x);
        opMode.telemetry.addData("y", y).setRetained(true);
        opMode.telemetry.addData("angle", FastMath.toDegrees(FastMath.atan(x/y))).setRetained(true);
        opMode.telemetry.update();

        drive.rotateDegrees(oppositeTurnDirection, angleToTurn, Globals.ALIGN_MOTOR_POWER);

        if(allianceColor == AllianceColor.BLUE) {
            drive.moveInches(Direction.BACKWARD, d + (Globals.ROBOT_LENGTH / 2)+3, Globals.ALIGN_MOTOR_POWER);
        } else {
            drive.moveInches(Direction.BACKWARD, d + (Globals.ROBOT_LENGTH / 2) + 3.5, Globals.ALIGN_MOTOR_POWER);
        }

        drive.rotateDegrees(oppositeTurnDirection, FastMath.abs(adjustmentAngle), Globals.ALIGN_MOTOR_POWER);
        opMode.sleep(1000);

        turnCorrection();
    }



    public void alignToBeaconTest() {

        opMode.telemetry.addData("Drive", "Attempting to align to beacon.").setRetained(true);
        opMode.telemetry.update();
        OpenGLMatrix lastLocation = vuforia.getNearestBeaconLocation();
        while(lastLocation == null) {
            lastLocation = vuforia.getNearestBeaconLocation();
            Thread.yield();
        }

        while(vuforia.getXPos() > 12 || vuforia.getYPos() > 5) {

            lastLocation = null;
            while(lastLocation == null) {
                lastLocation = vuforia.getNearestBeaconLocation();
                Thread.yield();
            }

            double y = vuforia.getXPos();
            double x = vuforia.getYPos();
            double W = Globals.BEACON_HALF_WIDTH;

            opMode.telemetry.addData("x", x);
            opMode.telemetry.addData("y", y);
            opMode.telemetry.update();

            double MPLnumerator = -2 * Math.sqrt(Math.pow(x,4) + 2*Math.pow(x,3)*W + Math.pow(W,2)*Math.pow(x,2));
            double MPLdenominator = 4*Math.pow(x,3) + 6*W*Math.pow(x,2) + 2*Math.pow(W,2)*x;
            double MPRnumerator = -2 * Math.sqrt(Math.pow(x,4)/4);
            double MPRdenominator = Math.pow(x,3);

            double rawMotorPowerLeft = MPLnumerator / MPLdenominator;
            double rawMotorPowerRight = MPRnumerator / MPRdenominator;

            double powerRatio = rawMotorPowerLeft / rawMotorPowerRight;

            motorPowerLeft = -.1 + (motorPowerLeft * powerRatio);
            motorPowerRight = -.1;

            opMode.telemetry.addData("motorPowerLeft", motorPowerLeft);
            opMode.telemetry.addData("motorPowerRight", motorPowerRight);

            opMode.telemetry.addData("rawMotorPowerLeft", rawMotorPowerLeft);
            opMode.telemetry.addData("rawMotorPowerRight", rawMotorPowerRight);
            opMode.telemetry.update();

            drive.moveFreely(motorPowerLeft, motorPowerRight);
        }

        opMode.telemetry.addData("Angle", vuforia.getOrientation());

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
        cannon.setToggle(true);
        opMode.sleep(1500);
        cannon.setToggle(false);
    }


    public void fireParticles() {
        opMode.telemetry.addData("Cannon", "Firing particles").setRetained(true);
        cannon.fire();
        ballQueue.setBallQueueOut();
        opMode.sleep(500);
        cannon.fire();
    }

    public void driveToNearBeacon() {
        opMode.telemetry.addData("Drive", "Driving to near "+allianceColor+" side beacon.\"").setRetained(true);
        drive.rotateDegrees(oppositeTurnDirection, 60, Globals.TURNING_SPEED);
        drive.moveInches(Direction.BACKWARD, 65, Globals.LOW_MOTOR_POWER);
        drive.rotateDegrees(oppositeTurnDirection, 30, Globals.TURNING_SPEED);
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


    public void checkBeaconColor() {

        beaconColorSensor.determineColor(allianceColor, new Runnable() {
            @Override
            public void run() {
                telemetry("Color Detection", "Found AllianceColor On Left");
            }
        }, new Runnable() {
            @Override
            public void run() {
                telemetry("Color Detection", "Found AllianceColor on Right");
                buttonPresser.setRightOut();
                buttonPresser.setLeftOut();
            }
        }, new Runnable() {
            @Override
            public void run() {
                drive.moveInches(Direction.BACKWARD, 3, Globals.LOW_MOTOR_POWER);
                checkBeaconColor();
            }
        });

    }


    public void detectBeaconColor() {

        beaconColorSensor.determineColor(allianceColor, new Runnable() {
            @Override
            public void run() {
                telemetry("Color Detection", "Found AllianceColor " + allianceColor + " On Left");
                buttonPresser.setLeftOut();
            }
        }, new Runnable() {
            @Override
            public void run() {
                telemetry("Color Detection", "Found AllianceColor " + allianceColor + " on Right");
                buttonPresser.setRightOut();
            }
        }, new Runnable() {
            @Override
            public void run() {
                telemetry("Color Detection", "Color not detected");
                drive.moveInches(Direction.BACKWARD, 3.5, .2);
                detectBeaconColor();
            }
        });
    }


    public void pressBeacon() {
        // Reset both arms
        buttonPresser.setLeftIn();
        buttonPresser.setRightIn();
        detectBeaconColor();
        opMode.sleep(500);
        drive.moveInches(Direction.BACKWARD, 8, Globals.LOW_MOTOR_POWER);

        telemetry("pressBeacon", "Done");
    }


    public void driveToFarBeacon() {
        telemetry("Auto Methods", "Driving to far "+allianceColor+" side beacon.");
        drive.moveInches(Direction.FORWARD, 8, Globals.LOW_MOTOR_POWER);
        drive.rotateDegrees(oppositeTurnDirection, 90, Globals.TURNING_SPEED);
        drive.moveInches(Direction.FORWARD, 55, Globals.LOW_MOTOR_POWER);
        drive.rotateDegrees(defaultTurnDirection, 90, Globals.TURNING_SPEED);
    }

    public void turnCorrection() {
        if(vuforia != null) {
            telemetry("turnCorrection", "Starting turnCorrection");
            while(vuforia.getNearestBeaconLocation() == null && !opMode.isStopRequested()) {
                Thread.yield();
                opMode.telemetry.update();
            }
            double angleToBeacon = vuforia.angleToBeacon();
            if(angleToBeacon > 5)
                drive.rotateDegrees(oppositeTurnDirection, angleToBeacon, Globals.TURNING_SPEED);
        }
    }


    public void telemetry(String caption, Object value) {
        opMode.telemetry.addData(caption, value).setRetained(true);
        opMode.telemetry.update();
    }

    public void runAutonomousProcess() {
        drive.moveInches(Direction.BACKWARD, 24, Globals.LOW_MOTOR_POWER);
        fireParticles();

        driveToNearBeacon(); // drive to near beacon
        turnCorrection(); // correct angle
        pressBeacon(); // read color, extend arm, press beacon
        driveToFarBeacon(); // back up and drive to next beacon
        turnCorrection(); // correct angle
        pressBeacon(); // read color, extend arm, press beacon
        drive.moveInches(Direction.FORWARD, 3, Globals.LOW_MOTOR_POWER);
//        hopper.setDirection(Hopper.Direction.OUT);
//        hopper.activate();
//        drive.moveInches(Direction.FORWARD, 90, Globals.MAX_MOTOR_POWER);
//        hopper.deactivate();
        telemetry("Autonomous", "Done");
    }

}