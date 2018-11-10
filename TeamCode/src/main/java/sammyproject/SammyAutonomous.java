package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;


@Autonomous(name = "SammyAutonomous", group = "SuperTeam")
public class SammyAutonomous extends LinearOpMode {
    DcMotor MotorLeft;
    DcMotor MotorRight;

    public MecanumEncoderDrive drive;

    private RobotParameters parameters = new RobotParameters.Builder()
            .encodersEnabled(true)
            .gyroEnabled(true)
            .wheelBaseRadius(8.5)
            .wheelRadius(8)
            .driveGearRatio(1.5)
            .leftDriveMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightDriveMotorDirection(DcMotorSimple.Direction.FORWARD)
            .build();

    @Override
    public void runOpMode() throws InterruptedException {

        MotorLeft = hardwareMap.dcMotor.get("left_drive_motor");
        MotorRight = hardwareMap.dcMotor.get("right_drive_motor");

        initialize();

        //wait for the game to start (driver press play from the phone)
        waitForStart();
        drive.moveInches(Direction.BACKWARD, 27, 0.5); //go back to the crater
        drive.moveInches(Direction.FORWARD, 9, 0.5); //go left then turn right
        drive.moveInches(Direction.LEFT,45,0.6);
        drive.moveInches(Direction.RIGHT,35, 0.5); //go right

        drive.rotateDegrees(Direction.FORWARD,45,0.45);
        drive.rotateDegrees(Direction.BACKWARD,90,1);

        drive.moveInches(Direction.FORWARD, 9, 0.5); //go Forward
        drive.rotateDegrees(Direction.RIGHT, 45, 0.5); //go Right

        // Park in the crater
        drive.moveInches(Direction.FORWARD, 66, 0.5);
        drive.moveInches(Direction.BACKWARD, 87, 1);
        drive.stop();
        sleep(5000); //stop you have arrive your destination
        stop(); //stop you have arrive to your destination

    }

    public void initialize() {
        drive = new MecanumEncoderDrive(hardwareMap, this, parameters);

        drive.leftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        drive.rightDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
    }
}