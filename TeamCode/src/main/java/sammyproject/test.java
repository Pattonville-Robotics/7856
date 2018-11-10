package sammyproject;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;



@Autonomous(name = "Test", group = "superMario")
public class test extends LinearOpMode {
    private MecanumEncoderDrive drive;

    private RobotParameters parameters = new RobotParameters.Builder()
            .encodersEnabled(true)
            .gyroEnabled(true)
            .wheelBaseRadius(8.5)
            .wheelRadius(2)
            .driveGearRatio(1.5)
            .leftDriveMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightDriveMotorDirection(DcMotorSimple.Direction.FORWARD)
            .build();

            DcMotor motor1;
            DcMotor motor2;


    @Override
    public void runOpMode() throws InterruptedException {
        motor1 = hardwareMap.dcMotor.get("motorLeft");
        motor2 = hardwareMap.dcMotor.get("motorRight");

        motor1.setDirection(DcMotorSimple.Direction.REVERSE);
        motor2.setDirection(DcMotorSimple.Direction.FORWARD); //motorRight direction will be Forward not Reverse

        motor1.setPower(5); //set motorLeft power to 5
        motor2.setPower(5); //set motorRight power to 5

        sleep(500); //sleep and wait for the next direction to be given to you
        waitForStart(); //wait for the play to click the start button from the  phone before the robot start going

        //direction
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
    }
}
