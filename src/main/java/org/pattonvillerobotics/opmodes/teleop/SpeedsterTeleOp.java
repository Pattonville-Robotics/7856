package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;

/**
 * Created by skaggsw on 1/27/17.
 */

@TeleOp(name = "Speedster")
public class SpeedsterTeleOp extends LinearOpMode {

    private EncoderDrive drive;
    private DcMotor motorLeft;
    private DcMotor motorRight;

    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        while (opModeIsActive()) {
            doLoop();
            idle();
        }
    }

    public void initialize() {
        //drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        motorLeft = hardwareMap.dcMotor.get("motor_left");
        motorRight = hardwareMap.dcMotor.get("motor_right");

        motorRight.setDirection(DcMotorSimple.Direction.REVERSE);
        motorLeft.setDirection(DcMotorSimple.Direction.FORWARD);

        motorRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //telemetry.setMsTransmissionInterval(16);
    }

    public void doLoop() {
        //drive.moveFreely(Range.clip(gamepad1.left_stick_y, -Globals.MAX_MOTOR_POWER, Globals.MAX_MOTOR_POWER), Range.clip(gamepad1.right_stick_y, -Globals.MAX_MOTOR_POWER, Globals.MAX_MOTOR_POWER));

        motorLeft.setPower(-gamepad1.left_stick_y);
        motorRight.setPower(-gamepad1.right_stick_y);

        //telemetry.addData("Left Motor Power", drive.leftDriveMotor.getPower());
        //telemetry.addData("Right Motor Power", drive.rightDriveMotor.getPower());
        //telemetry.update();
    }
}
