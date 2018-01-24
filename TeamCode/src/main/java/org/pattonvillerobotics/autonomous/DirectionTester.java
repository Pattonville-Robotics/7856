package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;

/**
 * Created by pieperm on 1/24/18.
 */

@Autonomous(name = "DirectionTester", group = OpModeGroups.TESTING)
public class DirectionTester extends LinearOpMode {

    private MecanumEncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new MecanumEncoderDrive(hardwareMap, this, CustomRobotParameters.ROBOT_PARAMETERS);
        drive.leftDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.leftRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        drive.rightRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        telemetry.addData("Moving", Direction.FORWARD).setRetained(true);
        telemetry.update();
        drive.moveInches(Direction.FORWARD, 10, 0.5);

        sleep(500);

        telemetry.addData("Moving", Direction.BACKWARD).setRetained(true);
        telemetry.update();
        drive.moveInches(Direction.FORWARD, 10, 0.5);

        sleep(500);

        telemetry.addData("Rotating", Direction.LEFT).setRetained(true);
        telemetry.update();
        drive.rotateDegrees(Direction.LEFT, 90, 0.5);

        sleep(500);
        telemetry.addData("Rotating", Direction.RIGHT).setRetained(true);
        telemetry.update();
        drive.rotateDegrees(Direction.RIGHT, 90, 0.5);


    }


}
