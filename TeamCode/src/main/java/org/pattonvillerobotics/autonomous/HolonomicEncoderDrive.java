package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.QuadEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

/**
 * Created by pieperm on 10/6/17.
 */

/**
 * @deprecated use MecanumEncoderDrive instead
 */
@Deprecated
public class HolonomicEncoderDrive extends QuadEncoderDrive {

    private DcMotor leftFrontDriveMotor, rightFrontDriveMotor, leftBackDriveMotor, rightBackDriveMotor;

    public HolonomicEncoderDrive(HardwareMap hardwareMap, LinearOpMode linearOpMode, RobotParameters robotParameters) {

        super(hardwareMap, linearOpMode, robotParameters);
        this.leftFrontDriveMotor = hardwareMap.dcMotor.get("left_front_drive_motor");
        this.rightFrontDriveMotor = hardwareMap.dcMotor.get("right_front_drive_motor");
        this.leftBackDriveMotor = hardwareMap.dcMotor.get("left_back_drive_motor");
        this.rightBackDriveMotor = hardwareMap.dcMotor.get("right_back_drive_motor");

    }

    @Override
    public void moveInches(Direction direction, double inches, double power) {

        switch (direction) {

            case FORWARD:
                moveInches(90, inches, power);
                break;
            case BACKWARD:
                moveInches(270, inches, power);
                break;
            case LEFT:
                moveInches(180, inches, power);
                break;
            case RIGHT:
                moveInches(0, inches, power);
                break;
            default:
                throw new IllegalArgumentException("Direction must be FORWARD, BACKWARD, LEFT, or RIGHT");

        }
    }

    public void moveInches(double angle, double inches, double power) {

        int targetPositionLeft, targetPositionRight;

        super.storeMotorModes();
        super.resetMotorEncoders();

        int deltaPosition = (int) FastMath.round(super.inchesToTicks(inches));

        double iVectorLeft = deltaPosition * FastMath.pow(FastMath.cos(angle), 2) * FastMath.cos(45) + deltaPosition * FastMath.cos(angle) * FastMath.sin(angle) * FastMath.sin(45);
        double jVectorLeft;

    }

}
