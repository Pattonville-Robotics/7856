package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;

/**
 * Created by pieperm on 9/30/17.
 */

public class Glyphter {

    private DcMotor glyphterMotor;
    private MecanumEncoderDrive drive;
    private int currentRow, currentColumn;
    private static final double COLUMN_WIDTH = 7;
    private static final double ROW_HEIGHT = 6.5;
    private static final double CYLINDER_RADIUS = 1;
    private SimpleMecanumDrive mecanumDrive;

    public Glyphter(HardwareMap hardwareMap, LinearOpMode linearOpMode, MecanumEncoderDrive mecanumEncoderDrive) {

        glyphterMotor = hardwareMap.dcMotor.get("glyphter-motor");
        this.drive = mecanumEncoderDrive;
        this.currentRow = 1;
        this.currentColumn = 2;

    }

    public void moveTo(int targetRow, int targetColumn) {

        moveToColumn(targetColumn);
        moveToRow(targetRow);

    }

    public void moveToColumn(int targetColumn) {

        // TODO: Need a way to find the current column (perhaps with the pictograph)

        int columnVector = targetColumn - currentColumn;
        double inchesVector = columnVector * COLUMN_WIDTH;

        if(inchesVector > 0) {
            drive.moveInches(Direction.RIGHT, FastMath.abs(inchesVector), 0.5);
        }
        else if(inchesVector < 0) {
            drive.moveInches(Direction.LEFT, FastMath.abs(inchesVector), 0.5);
        }

        currentColumn = targetColumn;

    }

    public void moveToRow(int targetRow) {

        int rowVector = targetRow - currentRow; // The number and direction of rows to travel
        double inchesVector = rowVector * ROW_HEIGHT; // Converts the number of rows into inches
        int targetPosition = (int) FastMath.round(inchesToTicks(inchesVector)); // Obtains a target position for the motor
        glyphterMotor.setTargetPosition(targetPosition);

        while(glyphterMotor.isBusy() || !reachedTarget(glyphterMotor.getCurrentPosition(), targetPosition)) {
            Thread.yield();
        }
        glyphterMotor.setPower(0);

        currentRow = targetRow;

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

}
