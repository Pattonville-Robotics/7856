package org.pattonvillerobotics.robotclasses.drive;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

/**
 * Created by pieperm on 1/19/17.
 */

public class TestEncoderDrive extends EncoderDrive {

    public static final int TARGET_REACHED_THRESHOLD = 16;
    private static final String TAG = "EncoderDrive";
    private static final int MAX_STALL_COUNT = 10;

    /**
     * sets up Drive object with custom RobotParameters useful for doing calculations with encoders
     *
     * @param hardwareMap     a hardwaremap
     * @param linearOpMode    a linearopmode
     * @param robotParameters a RobotParameters containing robot specific calculations for
     *                        wheel radius and wheel base radius
     */
    public TestEncoderDrive(HardwareMap hardwareMap, LinearOpMode linearOpMode, RobotParameters robotParameters) {
        super(hardwareMap, linearOpMode, robotParameters);
    }

    /**
     * drives a specific number of inches in a given direction
     *
     * @param direction the direction (forward or backward) to drive in
     * @param inches    the number of inches to drive
     * @param power     the power with which to drive
     */
    @Override
    public void moveInches(Direction direction, double inches, double power) {
        //Move Specified Inches Using Motor Encoders

        int targetPositionLeft;
        int targetPositionRight;

        int startPositionLeft = leftDriveMotor.getCurrentPosition();
        int startPositionRight = rightDriveMotor.getCurrentPosition();

        int deltaPosition = (int) FastMath.round(inchesToTicks(inches));

        switch (direction) {
            case FORWARD: {
                targetPositionLeft = startPositionLeft + deltaPosition;
                targetPositionRight = startPositionRight + deltaPosition;
                break;
            }
            case BACKWARD: {
                targetPositionLeft = startPositionLeft - deltaPosition;
                targetPositionRight = startPositionRight - deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be Direction.FORWARDS or Direction.BACKWARDS!");
        }

        Log.e(TAG, "Getting motor modes");
        DcMotor.RunMode leftDriveMotorMode = leftDriveMotor.getMode();
        DcMotor.RunMode rightDriveMotorMode = rightDriveMotor.getMode();

        Log.e(TAG, "Setting motor modes");
        leftDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Log.e(TAG, "Setting motor power high");
        move(Direction.FORWARD, power); // To keep power in [0.0, 1.0]. Encoders control direction

        Log.e(TAG, "Setting target position");
        leftDriveMotor.setTargetPosition(targetPositionLeft);
        rightDriveMotor.setTargetPosition(targetPositionRight);

        telemetry("Moving " + inches + " inches at power " + power);
        telemetry("LMotorT: " + targetPositionLeft);
        telemetry("RMotorT: " + targetPositionRight);
        telemetry("EncoderDelta: " + deltaPosition);
        Telemetry.Item distance = telemetry("DistanceL: N/A DistanceR: N/A");

        int stallCountLeft = 0;
        int stallCountRight = 0;
        int prevPosLeft = 0;
        int prevPosRight = 0;
        while (!reachedTarget(leftDriveMotor.getCurrentPosition(), targetPositionLeft, rightDriveMotor.getCurrentPosition(), targetPositionRight) && !linearOpMode.isStopRequested()) {
            Thread.yield();

            int currentPosLeft = leftDriveMotor.getCurrentPosition();
            if(currentPosLeft == prevPosLeft) {
                stallCountLeft++;
                if(stallCountLeft > MAX_STALL_COUNT) {
                    leftDriveMotor.setTargetPosition(targetPositionLeft);
                    leftDriveMotor.setPower(power);
                }
                else {
                    stallCountLeft = 0;
                }
                prevPosLeft = currentPosLeft;
            }

            int currentPosRight = rightDriveMotor.getCurrentPosition();
            if(currentPosRight == prevPosRight) {
                stallCountRight++;
                if(stallCountRight > MAX_STALL_COUNT) {
                    rightDriveMotor.setTargetPosition(targetPositionRight);
                    rightDriveMotor.setPower(power);
                }
                else {
                    stallCountLeft = 0;
                }
                prevPosLeft = currentPosLeft;
            }

            distance.setValue("DistanceL: " + leftDriveMotor.getCurrentPosition() + " DistanceR: " + rightDriveMotor.getCurrentPosition());
            linearOpMode.telemetry.update();
        }
        Log.e(TAG, "Setting motor power low");
        stop();

        Log.e(TAG, "Restoring motor mode");
        leftDriveMotor.setMode(leftDriveMotorMode); // Restore the prior mode
        rightDriveMotor.setMode(rightDriveMotorMode);
    }

    /**
     * turns the robot a certain number of degrees in a given direction
     *
     * @param direction the direction (left or right) to turn in
     * @param degrees   the number of degrees to turn
     * @param speed     the speed at which to turn
     */
    @Override
    public void rotateDegrees(Direction direction, double degrees, double speed) {
        //Move specified degrees using motor encoders

        int targetPositionLeft;
        int targetPositionRight;

        int startPositionLeft = leftDriveMotor.getCurrentPosition();
        int startPositionRight = rightDriveMotor.getCurrentPosition();

        double inches = degreesToInches(degrees);
        int deltaPosition = (int) FastMath.round(inchesToTicks(inches));

        switch (direction) {
            case LEFT: {
                targetPositionLeft = startPositionLeft - deltaPosition;
                targetPositionRight = startPositionRight + deltaPosition;
                break;
            }
            case RIGHT: {
                targetPositionLeft = startPositionLeft + deltaPosition;
                targetPositionRight = startPositionRight - deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be Direction.LEFT or Direction.RIGHT!");
        }

        DcMotor.RunMode leftDriveMotorMode = leftDriveMotor.getMode();
        DcMotor.RunMode rightDriveMotorMode = rightDriveMotor.getMode();

        leftDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftDriveMotor.setTargetPosition(targetPositionLeft);
        rightDriveMotor.setTargetPosition(targetPositionRight);

        Telemetry.Item[] items = new Telemetry.Item[]{
                telemetry("Rotating " + degrees + " degrees at speed " + speed).setRetained(true),
                telemetry("LMotorT: " + targetPositionLeft).setRetained(true),
                telemetry("RMotorT: " + targetPositionRight).setRetained(true),
                telemetry("EncoderDelta: " + deltaPosition).setRetained(true),
                telemetry("DistanceL: DistanceR:")
        };
        Telemetry.Item distance = items[4];

        move(Direction.FORWARD, speed); // To keep speed in [0.0, 1.0]. Encoders control direction
        while (!reachedTarget(leftDriveMotor.getCurrentPosition(), targetPositionLeft, rightDriveMotor.getCurrentPosition(), targetPositionRight) && !linearOpMode.isStopRequested()) {
            Thread.yield();
            distance.setValue("DistanceL: " + leftDriveMotor.getCurrentPosition() + " DistanceR: " + rightDriveMotor.getCurrentPosition());
            linearOpMode.telemetry.update();
        }
        stop();

        leftDriveMotor.setMode(leftDriveMotorMode); // Restore the prior mode
        rightDriveMotor.setMode(rightDriveMotorMode);

        for (Telemetry.Item i : items)
            i.setRetained(false);
    }

}
