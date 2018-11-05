package sammyproject;

import com.qualcomm.ftcrobotcontroller.opmodes;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

  /*Programmed by Caleb Fahlgren
    FTC Autonomous Robot Program
	Please Do NOT edit without letting me know
    Used thru 2015/2016 Academic Year */


public class Autonomous extends LinearOpMode {
    DcMotor motorLeft;
    DcMotor motorRight;
    DcMotor motorArm;
    Servo claw;


    double clawPosition;
    double clawDelta = 0.01;

    public void runOpMode() throws InterruptedException {

        // Gets left and right motors from the configuration file
        MotorLeft = hardwareMap.dcMotor.get("motorLeft");
        rightMotor = hardwareMap.dcMotor.get("motorRight");
        claw = hardwareMap.servo.get("servo_1");


        rightMotor.setDirection(DcMotor.Direction.REVERSE);

        // Motors are set to run without encoders
        motorLeft.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
        motorRight.setChannelMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);

        // Variable showing that the claw position starts at 0.5
        clawPosition = 0;

        // Will keep the claw closed during Autonomous
        clawPosition -= clawDelta;

        sleep(5000);
        // Drive forward for 3 seconds at half speed
        leftMotor.setPower(0.5);
        rightMotor.setPower(0.5);

        sleep(3000);

        // Turns right for 1.5 seconds at half speed
        leftMotor.setPower(0.5);
        rightMotor.setPower(-0.5);

        sleep(1500);


        // Drives backward for 3 seconds at half speed
        leftMotor.setPower(-0.5);
        rightMotor.setPower(-0.5);

        sleep(3000);

        // Raises Arm for 5 seconds
        motorArm.setPower(0.2);

        sleep(5500);


    }
}
