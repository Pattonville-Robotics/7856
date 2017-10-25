package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

/**
 * Created by pieperm on 9/30/17.
 */

public class Glyphter {

    private DcMotor glyphterMotor;
    private static final double CYLINDER_RADIUS = 1;
    private MecanumEncoderDrive mecanumEncoderDrive;

    public Glyphter(HardwareMap hardwareMap) {

        glyphterMotor = hardwareMap.dcMotor.get("glyphter-motor");

    }

    public Glyphter(HardwareMap hardwareMap, MecanumEncoderDrive mecanumEncoderDrive) {

        this(hardwareMap);
        this.mecanumEncoderDrive = mecanumEncoderDrive;

    }

    public void moveTo(int currentRow, int targetRow, int currentColumn, int targetColumn) {

        moveToColumn(currentColumn, targetColumn);
        moveToRow(currentRow, targetRow);

    }

    public void moveToColumn(int currentColumn, int targetColumn) {

        int columnVector = targetColumn - currentColumn;
        double inchesVector = columnVector * Globals.COLUMN_WIDTH;

        if(inchesVector > 0) {
            mecanumEncoderDrive.moveInches(Direction.RIGHT, FastMath.abs(inchesVector), 0.5);
        }
        else if(inchesVector < 0) {
            mecanumEncoderDrive.moveInches(Direction.LEFT, FastMath.abs(inchesVector), 0.5);
        }

    }

    public void moveToRow(int currentRow, int targetRow) {

        int rowVector = targetRow - currentRow; // The number and direction of rows to travel
        double inchesVector = rowVector * Globals.ROW_HEIGHT; // Converts the number of rows into inches
        int targetPosition = (int) FastMath.round(inchesToTicks(inchesVector)); // Obtains a target position for the motor
        glyphterMotor.setTargetPosition(targetPosition);

        while(glyphterMotor.isBusy() || !reachedTarget(glyphterMotor.getCurrentPosition(), targetPosition)) {
            Thread.yield();
        }
        glyphterMotor.setPower(0);

    }

    private void changeRow(double inches) {

        int targetPosition = (int) FastMath.round(inchesToTicks(inches));
        glyphterMotor.setTargetPosition(targetPosition);

        while(glyphterMotor.isBusy() || !reachedTarget(glyphterMotor.getCurrentPosition(), targetPosition)) {
            Thread.yield();
        }
        glyphterMotor.setPower(0);

    }

    private void changeColumn(double inches) {

        try {
            mecanumEncoderDrive.moveInches(inches > 0 ? Direction.RIGHT : Direction.LEFT, FastMath.abs(inches), 0.5);
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
        return (inches / CYLINDER_RADIUS) * (RobotParameters.TICKS_PER_REVOLUTION / (2 * FastMath.PI));
    }

    private boolean reachedTarget(int currentPosition, int targetPosition) {
        return FastMath.abs(currentPosition - targetPosition) < EncoderDrive.TARGET_REACHED_THRESHOLD;
    }

    public DcMotor getMotor() {
        return glyphterMotor;
    }

}
