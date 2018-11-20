package org.pattonvillerobotics.robotclasses;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.robocol.TelemetryMessage;
import com.qualcomm.robotcore.robot.Robot;

import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.robotclasses.mechanisms.AbstractMechanism;
import org.pattonvillerobotics.robotclasses.misc.CustomizedRobotParameters;

/**
 * Created by wrightk03 on 11/19/18.
 * <p>
 * Purpouse:
 */
public class AbstractLinearSlideStackMechanism extends AbstractMechanism {

    private DcMotor main_winch, secondary_winch;
    private int extension_distance;
    private LinearOpMode linearOpMode;
    private RobotParameters robotParameters;
    private WinchOreientation main_orientation, secondary_orientation;
    private DcMotor.RunMode mainWinchSavedMotorMode, secondaryWinchSavedMotorMode;

    public AbstractLinearSlideStackMechanism(int extension_distance, HardwareMap hardwareMap, LinearOpMode linearOpMode, RobotParameters parameters, boolean useBothWinches) {
        super(hardwareMap, linearOpMode);
        this.linearOpMode = linearOpMode;
        this.robotParameters = parameters;
        this.extension_distance = extension_distance;
        main_orientation = WinchOreientation.UP;
        secondary_orientation = WinchOreientation.UP;
        main_winch = hardwareMap.dcMotor.get("main_winch");
       if(useBothWinches) {
            secondary_winch = hardwareMap.dcMotor.get("secondary_winch");
        }
    }

    public enum WinchOreientation {
        LEFT,RIGHT,UP,DOWN;
    }

    public enum Winch {
        MAIN, SECONDARY;
    }

    /**
     * This method is used to specify the orientation of the winch motor.
     * This is necessary only if you are using the secondary winch. The main
     * winch will be automatically initialized to the UP orientation and this
     * will have no effect no matter what direction the winch is actually
     * oriented on the physical robot. The Secondary winch orientation is
     * also initialized to the UP orientation will rotate the same way as the
     * main winch unless the orientation is different from the main winch.
     *
     * @param orientation
     * @param winch
     */
    public void setWinchOrientation(WinchOreientation orientation, Winch winch){
        if(winch.equals(Winch.MAIN)){
            main_orientation = orientation;
        } else {
            secondary_orientation= orientation;
        }
    }

    /**
     * This method is used to automatically extend or retract the linear
     * slide stacks the full distance in which they can be extended. For
     * this to work you MUST set the extention distance to be the correct
     * distance IN INCHES that the stacks can extend ROUNDING DOWN to the
     * nearest inch. The power specifies the speed of the extension, and
     * the boolean for using both winches is ONLY NECESSARY IF YOU ACTUALLY
     * HAVE TWO WINCHES, otherwise it should be false.
     *
     * @param direction
     * @param power
     * @param useBothWinches
     */
    public void moveFully(Direction direction, double power, boolean useBothWinches) {
        if (useBothWinches) {
            main_winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            secondary_winch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            main_winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            secondary_winch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
            moveWinchInches(extension_distance, power, direction, useBothWinches);
        } else {
           moveWinchInches(extension_distance, power, direction, false);
        }
    }

    //Motor movement functions

    /**
     * Custom version of moveInches method from EncoderDrive
     * that allows user to specify WHICH motor moves x inches.
     *
     * @param inches
     * @param power
     * @param direction
     */
    public void moveWinchInches(int inches, double power, Direction direction, boolean useBothWinches){
        final String TAG = "EncoderDrive";
        //Move Specified Inches Using Motor Encoders

        int targetPositionLeft = 0, targetPositionRight = 0;

        Log.e(TAG, "Getting motor modes");
        storeMotorModes(useBothWinches);

        resetMotorEncoders(useBothWinches);

        int deltaPosition = (int) FastMath.round(inchesToTicks(inches));

        switch (direction) {
            case FORWARD: {
                targetPositionLeft = deltaPosition;
                if (useBothWinches) {
                    targetPositionRight = deltaPosition;
                }
                break;
            }
            case BACKWARD: {
                targetPositionLeft = -deltaPosition;
                if (useBothWinches) {
                    targetPositionRight = -deltaPosition;
                }
                break;
            }
            default:
                throw new IllegalArgumentException("Direction must be Direction.FORWARDS or Direction.BACKWARDS!");
        }

        Log.e(TAG, "Setting motor modes");
        setMotorsRunToPosition(useBothWinches);

        Log.e(TAG, "Setting motor power high");
        move(Direction.FORWARD, power, useBothWinches); // To keep power in [0.0, 1.0]. Encoders control direction

        Log.e(TAG, "Setting target position");
        setMotorTargets(targetPositionLeft, targetPositionRight, useBothWinches);

        telemetry("Moving " + inches + " inches at power " + power);
        telemetry("LMotorT: " + targetPositionLeft);
        telemetry("RMotorT: " + targetPositionRight);
        telemetry("EncoderDelta: " + deltaPosition);
        Telemetry.Item distance = telemetry("DistanceL: N/A DistanceR: N/A");

        //int oldLeftPosition = leftDriveMotor.getCurrentPosition();
        //int oldRightPosition = rightDriveMotor.getCurrentPosition();
        if(useBothWinches) {
            while ((main_winch.isBusy() || secondary_winch.isBusy()) || !reachedTarget(main_winch.getCurrentPosition(), targetPositionLeft, secondary_winch.getCurrentPosition(), targetPositionRight, true) && !linearOpMode.isStopRequested() && linearOpMode.opModeIsActive()) {
                Thread.yield();
                distance.setValue("DistanceL: " + main_winch.getCurrentPosition() + " DistanceR: " + secondary_winch.getCurrentPosition());
                linearOpMode.telemetry.update();
            }
        } else {
            while ((main_winch.isBusy() || !reachedTarget(main_winch.getCurrentPosition(), targetPositionLeft, secondary_winch.getCurrentPosition(), targetPositionRight, false) && !linearOpMode.isStopRequested() && linearOpMode.opModeIsActive())) {
                Thread.yield();
                distance.setValue("DistanceL: " + main_winch.getCurrentPosition() + " DistanceR: " + secondary_winch.getCurrentPosition());
                linearOpMode.telemetry.update();
            }
        }
        Log.e(TAG, "Setting motor power low");
        stop(useBothWinches);

        Log.e(TAG, "Restoring motor mode");
        restoreMotorModes(useBothWinches);

        sleep(100);

    }

    private boolean reachedTarget(int currentPositionLeft, int targetPositionLeft, int currentPositionRight, int targetPositionRight, boolean bothWinches) {
        if(bothWinches) {
            return FastMath.abs(currentPositionLeft - targetPositionLeft) < EncoderDrive.TARGET_REACHED_THRESHOLD && FastMath.abs(currentPositionRight - targetPositionRight) < EncoderDrive.TARGET_REACHED_THRESHOLD;
        }  else {
            return FastMath.abs(currentPositionLeft - targetPositionLeft) < EncoderDrive.TARGET_REACHED_THRESHOLD;
        }
    }

    private Telemetry.Item telemetry(String message) {
        return this.linearOpMode.telemetry.addData("EncoderDrive", message);
    }

    protected boolean isMovingToPosition(boolean useBothWinches) {
        if(useBothWinches){
            return main_winch.isBusy() || secondary_winch.isBusy();
        } else {
            return main_winch.isBusy();
        }
    }

    private void restoreMotorModes(boolean useBothWinches) {
        main_winch.setMode(mainWinchSavedMotorMode);
        if(useBothWinches) {
            secondary_winch.setMode(secondaryWinchSavedMotorMode);
        }
    }

    private void storeMotorModes(boolean useBothWinches) {
        mainWinchSavedMotorMode =   main_winch.getMode();
        if(useBothWinches) {
            secondaryWinchSavedMotorMode = secondary_winch.getMode();
        }
    }

    private void resetMotorEncoders(boolean useBothWinches) {
        main_winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        if(useBothWinches) {
            secondary_winch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }

    private void setMotorsRunToPosition(boolean useBothWinches) {
        if (main_winch.getMode() != DcMotor.RunMode.RUN_TO_POSITION)
             main_winch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        if (secondary_winch.getMode() != DcMotor.RunMode.RUN_TO_POSITION && useBothWinches)
            secondary_winch.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    private void setMotorTargets(int targetPositionLeft, int targetPositionRight, boolean useBothWinches) {
        main_winch.setTargetPosition(targetPositionLeft);
        if(useBothWinches) {
            secondary_winch.setTargetPosition(targetPositionRight);
        }
    }

    private double inchesToTicks(double inches) {
        return robotParameters.getAdjustedTicksPerRevolution() * inches / robotParameters.getWheelCircumference();
    }

    private void move(Direction direction, double power, boolean useBothWinches) {
        double motorPower;

        switch (direction) {
            case FORWARD:
                motorPower = power;
                break;
            case BACKWARD:
                motorPower = -power;
                break;
            default:
                throw new IllegalArgumentException("Direction must be Backward or Forwards");
        }

        moveFreely(motorPower, useBothWinches);
    }

    public void moveFreely(double leftPower, boolean useBothWinches) {
        main_winch.setPower(leftPower);
        if(useBothWinches) {
            secondary_winch.setPower(leftPower);
        }
    }

    private void stop(boolean useBothWinches) {
        moveFreely(0, useBothWinches);
    }

    private void sleep(long milli) {
        this.linearOpMode.sleep(milli);
    }

}

