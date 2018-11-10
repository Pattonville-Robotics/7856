package org.pattonvillerobotics.autonomous.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;


@Autonomous(name = "SammyMainAutonomous", group = "SuperTeam")
public class SammyMainAutonomous extends LinearOpMode {

    public MecanumEncoderDrive drive;

    private RobotParameters parameters = new RobotParameters.Builder()
            .encodersEnabled(true)
            .gyroEnabled(true)
            .wheelBaseRadius(8.5)
            .wheelRadius(2)
            .driveGearRatio(1.5)
            .leftDriveMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightDriveMotorDirection(DcMotorSimple.Direction.FORWARD)
            .build();

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        drive.moveInches(Direction.UP, 21, 0.6); //robots needs to go up with the
        drive.stop();
        sleep(400); //robots needs to rest and wait for the next direction
        drive.moveInches(Direction.BACKWARD, 14, 0.5);
        drive.rotateDegrees(Direction.LEFT, 90, 0.5);
        // Move straight
        drive.moveInches(Direction.FORWARD, 38, 0.5);
        drive.rotateDegrees(Direction.RIGHT, 45, 0.5);
        // Park in the crater
        drive.moveInches(Direction.FORWARD, 66, 0.5);
        drive.moveInches(Direction.BACKWARD, 87, 1);
        drive.stop();
        sleep(500000); //stop you have arrive your destination
    }

    public void initialize() {
        drive = new MecanumEncoderDrive(hardwareMap, this, parameters);
    }
}