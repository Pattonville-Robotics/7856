package sammyproject;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

  /*Programmed by Caleb Fahlgren
    FTC Autonomous Robot Program
	Please Do NOT edit without letting me know
    Used thru 2015/2016 Academic Year */


public class autonomous extends LinearOpMode {
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorArm;
    Servo claw;


    double clawPosition;
    double clawDelta = 0.01;

    public void runOpMode() throws InterruptedException {

        // Gets left and right motors from the configuration file
        motorLeft = hardwareMap.dcMotor.get("motorLeft");
        motorRight = hardwareMap.dcMotor.get("motorRight");
        claw = hardwareMap.servo.get("servo_1");


        motorRight.setDirection(DcMotor.Direction.REVERSE);

        // Motors are set to run without encoders
        motorLeft.setPower(5);
        motorRight.setPower(5);

        // Variable showing that the claw position starts at 0.5
        clawPosition = 0;

        // Will keep the claw closed during Autonomous
        clawPosition -= clawDelta;

        sleep(5000);
        // Drive forward for 3 seconds at half speed
        motorLeft.setPower(0.5);
        motorRight.setPower(0.5);

        sleep(3000);

        // Turns right for 1.5 seconds at half speed
        motorLeft.setPower(0.5);
        motorRight.setPower(-0.5);

        sleep(1500);


        // Drives backward for 3 seconds at half speed
       motorLeft.setPower(-5);
        motorRight.setPower(-0.5);

        sleep(3000);

        // Raises Arm for 5 seconds
        motorArm.setPower(0.2);

        sleep(5500);


    }
}
