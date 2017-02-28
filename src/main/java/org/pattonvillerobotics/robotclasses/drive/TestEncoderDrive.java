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
 * Created by bahrg on 1/31/17.
 */

public class TestEncoderDrive extends EncoderDrive {

    private static final String TAG = "EncoderDrive";
    private static final double SPEED_INCREMENT = 0.01;
    private static final double MIN_SPEED = .15;

    /**
     * sets up Drive object with custom RobotParameters useful for doing calculations with encoders
     *
     * @param hardwareMap     a hardwaremap
     * @param linearOpMode    a linearopmode
     * @param robotParameters a RobotParameters containing robot specific calculations for
     */
    public TestEncoderDrive(HardwareMap hardwareMap, LinearOpMode linearOpMode, RobotParameters robotParameters) {
        super(hardwareMap, linearOpMode, robotParameters);
    }

    @Override
    public void moveInches(Direction direction, double inches, double power) {
        //Move Specified Inches Using Motor Encoders

        int targetPositionLeft;
        int targetPositionRight;
        double currentSpeed = MIN_SPEED;

        Log.e(TAG, "Getting motor modes");
        DcMotor.RunMode leftDriveMotorMode = leftDriveMotor.getMode();
        DcMotor.RunMode rightDriveMotorMode = rightDriveMotor.getMode();

        leftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int deltaPosition = (int) FastMath.round(inchesToTicks(inches));

        switch (direction) {
            case FORWARD: {
                targetPositionLeft = deltaPosition;
                targetPositionRight = deltaPosition;
                break;
            }
            case BACKWARD: {
                targetPositionLeft = -deltaPosition;
                targetPositionRight = -deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be Direction.FORWARDS or Direction.BACKWARDS!");
        }



        Log.e(TAG, "Setting motor modes");
        if (leftDriveMotorMode != DcMotor.RunMode.RUN_TO_POSITION)
            leftDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (rightDriveMotorMode != DcMotor.RunMode.RUN_TO_POSITION)
            rightDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        Log.e(TAG, "Setting target position");
        leftDriveMotor.setTargetPosition(targetPositionLeft);
        rightDriveMotor.setTargetPosition(targetPositionRight);

        Log.e(TAG, "Setting motor power high");
        move(Direction.FORWARD, currentSpeed); // To keep power in [0.0, 1.0]. Encoders control direction

        telemetry("Moving " + inches + " inches at power " + power);
        telemetry("LMotorT: " + targetPositionLeft);
        telemetry("RMotorT: " + targetPositionRight);
        telemetry("EncoderDelta: " + deltaPosition);
        Telemetry.Item distance = telemetry("DistanceL: N/A DistanceR: N/A");
        Telemetry.Item motorPowerData = telemetry("Current Motor Power: N/A");

        while ((leftDriveMotor.isBusy() || rightDriveMotor.isBusy()) && !linearOpMode.isStopRequested()) {
            if(currentSpeed < power) {
                currentSpeed += SPEED_INCREMENT;
                move(Direction.FORWARD, currentSpeed);
            }

            Thread.yield();
            motorPowerData.setValue("Current Motor Power: " + currentSpeed);
            distance.setValue("DistanceL: " + leftDriveMotor.getCurrentPosition() + " DistanceR: " + rightDriveMotor.getCurrentPosition());
            linearOpMode.telemetry.update();
        }
        stop();

        if (leftDriveMotorMode != DcMotor.RunMode.RUN_TO_POSITION)
            leftDriveMotor.setMode(leftDriveMotorMode); // Restore the prior mode
        if (rightDriveMotorMode != DcMotor.RunMode.RUN_TO_POSITION)
            rightDriveMotor.setMode(rightDriveMotorMode);

        sleep(200);
    }

    @Override
    public void rotateDegrees(Direction direction, double degrees, double speed) {
        //Move specified degrees using motor encoders

        int targetPositionLeft;
        int targetPositionRight;
        double currentSpeed = MIN_SPEED;

        DcMotor.RunMode leftDriveMotorMode = leftDriveMotor.getMode();
        DcMotor.RunMode rightDriveMotorMode = rightDriveMotor.getMode();

        leftDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightDriveMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double inches = degreesToInches(degrees);
        int deltaPosition = (int) FastMath.round(inchesToTicks(inches));

        switch (direction) {
            case COUNTERCLOCKWISE:
            case LEFT: {
                targetPositionLeft = -deltaPosition;
                targetPositionRight = deltaPosition;
                break;
            }
            case CLOCKWISE:
            case RIGHT: {
                targetPositionLeft = deltaPosition;
                targetPositionRight = -deltaPosition;
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be Direction.LEFT or Direction.RIGHT!");
        }

        if (leftDriveMotorMode != DcMotor.RunMode.RUN_TO_POSITION)
            leftDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (rightDriveMotorMode != DcMotor.RunMode.RUN_TO_POSITION)
            rightDriveMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        leftDriveMotor.setTargetPosition(targetPositionLeft);
        rightDriveMotor.setTargetPosition(targetPositionRight);

        Telemetry.Item[] items = new Telemetry.Item[]{
                telemetry("Rotating " + degrees + " degrees at speed " + speed).setRetained(true),
                telemetry("LMotorT: " + targetPositionLeft).setRetained(true),
                telemetry("RMotorT: " + targetPositionRight).setRetained(true),
                telemetry("EncoderDelta: " + deltaPosition).setRetained(true),
                telemetry("DistanceL: DistanceR:"),
                telemetry("Current Motor Power:")
        };
        Telemetry.Item distance = items[4];
        Telemetry.Item motorPowerData = items[5];

        move(Direction.FORWARD, currentSpeed); // To keep speed in [0.0, 1.0]. Encoders control direction
        while ((leftDriveMotor.isBusy() || rightDriveMotor.isBusy()) && !linearOpMode.isStopRequested()) {
            if (currentSpeed < speed) {
                currentSpeed += SPEED_INCREMENT;
                move(Direction.FORWARD, currentSpeed);
            }
            Thread.yield();
            distance.setValue("DistanceL: " + leftDriveMotor.getCurrentPosition() + " DistanceR: " + rightDriveMotor.getCurrentPosition());
            motorPowerData.setValue("Current Motor Power: " + currentSpeed);
            linearOpMode.telemetry.update();
        }
        stop();

        if (leftDriveMotorMode != DcMotor.RunMode.RUN_TO_POSITION)
            leftDriveMotor.setMode(leftDriveMotorMode); // Restore the prior mode
        if (rightDriveMotorMode != DcMotor.RunMode.RUN_TO_POSITION)
            rightDriveMotor.setMode(rightDriveMotorMode);

        for (Telemetry.Item i : items)
            i.setRetained(false);

        sleep(200);
    }
}
