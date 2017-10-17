package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.commoncode.robotclasses.drive.AbstractDrive;

/**
 * Created by pieperm on 10/6/17.
 */

/**
 * @deprecated use SimpleMecanumDrive instead
 */
@Deprecated
public class HolonomicDrive extends AbstractDrive {

    private DcMotor leftFrontDriveMotor, rightFrontDriveMotor, leftBackDriveMotor, rightBackDriveMotor;

    public HolonomicDrive(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(linearOpMode, hardwareMap);
        this.leftBackDriveMotor = hardwareMap.dcMotor.get("left_front_drive_motor");
        this.rightFrontDriveMotor = hardwareMap.dcMotor.get("right_front_drive_motor");
        this.leftBackDriveMotor = hardwareMap.dcMotor.get("left_back_drive_motor");
        this.rightBackDriveMotor = hardwareMap.dcMotor.get("right_back_drive_motor");
    }

    public double getMotorMagnitude(double angle, double power, MotorPosition motorPosition) {

        double iVector, jVector;

        switch (motorPosition) {

            case LEFT_FRONT:
            case RIGHT_BACK:
                iVector = 0.5 * power * FastMath.cos(angle) + 0.5 * power * FastMath.sin(angle);
                jVector = 0.5 * power * FastMath.cos(angle) + 0.5 * power * FastMath.sin(angle);
                break;
            case LEFT_BACK:
            case RIGHT_FRONT:
                iVector = 0.5 * power * FastMath.cos(angle) - 0.5 * power * FastMath.sin(angle);
                jVector = 0.5 * power * FastMath.cos(angle) - 0.5 * power * FastMath.sin(angle);
                break;
            default:
                throw new IllegalArgumentException("MotorPosition must be LEFT_FRONT, RIGHT_BACK, RIGHT_FRONT, or LEFT_BACK.");

        }

        return FastMath.hypot(iVector, jVector);

    }

    public void move(double angle, double power) {

        double leftFrontPower = getMotorMagnitude(angle, power, MotorPosition.LEFT_FRONT);
        double rightFrontPower = getMotorMagnitude(angle, power, MotorPosition.RIGHT_FRONT);
        double leftBackPower = getMotorMagnitude(angle, power, MotorPosition.LEFT_BACK);
        double rightBackPower = getMotorMagnitude(angle, power, MotorPosition.RIGHT_BACK);

        leftFrontDriveMotor.setPower(leftFrontPower);
        leftBackDriveMotor.setPower(leftBackPower);
        rightFrontDriveMotor.setPower(rightFrontPower);
        rightBackDriveMotor.setPower(rightBackPower);

    }

    public void drive(double x, double y) {

        double angle = FastMath.atan2(y, x);
        double power = FastMath.hypot(x, y);

        move(angle, power);

    }

    public enum MotorPosition {
        LEFT_FRONT, RIGHT_FRONT, LEFT_BACK, RIGHT_BACK;
    }

}
