package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

/**
 * Created by pieperm on 9/30/17.
 */

public class Glyphter extends AbstractMechanism {

    public static final String TAG = Glyphter.class.getSimpleName();
    private DcMotor glyphterMotor;
    private MecanumEncoderDrive mecanumEncoderDrive;
    private DcMotor.RunMode glyphterMotorRunMode;

    public Glyphter(HardwareMap hardwareMap, LinearOpMode linearOpMode) {

        super(hardwareMap, linearOpMode);
        try {
            glyphterMotor = hardwareMap.dcMotor.get("glyphter-motor");
        } catch (IllegalArgumentException e) {
            displayTelemetry(TAG, "Could not initialize glyphter", true);
        }

    }

    public Glyphter(HardwareMap hardwareMap, LinearOpMode linearOpMode, MecanumEncoderDrive mecanumEncoderDrive) {

        this(hardwareMap, linearOpMode);
        this.mecanumEncoderDrive = mecanumEncoderDrive;

    }

    public void moveUp() {
        glyphterMotor.setPower(0.5);
    }

    public void moveDown() {
        glyphterMotor.setPower(-0.5);
    }

    public void stop() {
        glyphterMotor.setPower(0);
    }

    public void moveTo(int currentRow, int targetRow, int currentColumn, int targetColumn) {

        moveToColumn(currentColumn, targetColumn);
        moveToRow(currentRow, targetRow);

    }

    public void moveToColumn(int currentColumn, int targetColumn) {

        int columnVector = targetColumn - currentColumn;
        double inchesVector = columnVector * Globals.COLUMN_WIDTH;

        if(inchesVector > 0) {
            mecanumEncoderDrive.moveInches(Direction.RIGHT, Math.abs(inchesVector), 0.5);
        }
        else if(inchesVector < 0) {
            mecanumEncoderDrive.moveInches(Direction.LEFT, Math.abs(inchesVector), 0.5);
        }

    }

    public void moveToRow(int currentRow, int targetRow) {

        glyphterMotorRunMode = glyphterMotor.getMode();
        glyphterMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        int rowVector = targetRow - currentRow; // The number and direction of rows to travel
        double inchesVector = rowVector * Globals.ROW_HEIGHT; // Converts the number of rows into inches
        int targetPosition = (int) Math.round(inchesToTicks(inchesVector)); // Obtains a target position for the motor

        if(glyphterMotorRunMode != DcMotor.RunMode.RUN_TO_POSITION) {
            glyphterMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        glyphterMotor.setTargetPosition(targetPosition);

        while(glyphterMotor.isBusy() || !reachedTarget(glyphterMotor.getCurrentPosition(), targetPosition)) {
            Thread.yield();
        }

        glyphterMotor.setPower(0);
        glyphterMotor.setMode(glyphterMotorRunMode);

    }

    private void changeRow(double inches) {

        displayTelemetry(TAG, "Glyphter activated", true);

        int targetPosition = (int) Math.round(inchesToTicks(inches));
        glyphterMotor.setTargetPosition(targetPosition);

        displayTelemetry(TAG, "Set target position", true);

        while(glyphterMotor.isBusy() || !reachedTarget(glyphterMotor.getCurrentPosition(), targetPosition)) {
            displayTelemetry("isBusy", glyphterMotor.isBusy(), true);
            displayTelemetry("Current Position", glyphterMotor.getCurrentPosition(), true);
            displayTelemetry("Target Position", targetPosition, true);
            displayTelemetry("Reached target", reachedTarget(glyphterMotor.getCurrentPosition(), targetPosition), true);
            Thread.yield();
        }
        displayTelemetry(TAG, "Reached position", true);
        glyphterMotor.setPower(0);

    }

    private void changeColumn(double inches) {

        try {
            mecanumEncoderDrive.moveInches(inches > 0 ? Direction.RIGHT : Direction.LEFT, Math.abs(inches), 0.5);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }

    }

    public void moveOneSpace(Direction direction) {

        switch (direction) {
            case UP:
                changeRow(Globals.ROW_HEIGHT);
                break;
            case DOWN:
                changeRow(-Globals.ROW_HEIGHT);
                break;
            case LEFT:
                changeColumn(-Globals.COLUMN_WIDTH);
                break;
            case RIGHT:
                changeColumn(Globals.COLUMN_WIDTH);
                break;
            default:
                throw new IllegalArgumentException("Direction must be UP, DOWN, LEFT, or RIGHT");
        }

    }

    /**
     * converts linear distance of the glyphter into encoder ticks
     *
     * comes from the equation linear distance = radius * angle
     *
     * @param inches the linear distance to travel
     * @return the number of encoder ticks
     */
    private double inchesToTicks(double inches) {
        return (inches / Globals.CYLINDER_RADIUS) * (RobotParameters.TICKS_PER_REVOLUTION / (2 * Math.PI));
    }

    private boolean reachedTarget(int currentPosition, int targetPosition) {
        return Math.abs(currentPosition - targetPosition) < EncoderDrive.TARGET_REACHED_THRESHOLD;
    }

    public DcMotor getMotor() {
        return glyphterMotor;
    }

}
