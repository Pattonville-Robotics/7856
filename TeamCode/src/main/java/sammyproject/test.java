package sammyproject;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;



@Autonomous(name = "Test", group = "superMario")
public class test extends LinearOpMode {
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

           private DcMotor motor1;
           private DcMotor motor2;


    @Override
    public void runOpMode() throws InterruptedException {
        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("Motor2");

        motor1.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart(); //wait for the play to click the start button from the  phone before the robot start going


        motor1.setPower(5);
        motor2.setPower(5);

        //direction
        motor1.setDirection();


    }

    public void initialize() {
        drive = new MecanumEncoderDrive(hardwareMap, this, parameters);
    }
}
