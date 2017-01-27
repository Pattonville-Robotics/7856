package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.Range;

import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.opmodes.autonomous.Globals;

/**
 * Created by skaggsw on 1/27/17.
 */

public class SpeedsterTeleOp extends LinearOpMode {

    private EncoderDrive drive;

    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.setMsTransmissionInterval(16);
        waitForStart();

        while (opModeIsActive()) {
            doLoop();
            idle();
        }
    }

    public void initialize() {
        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);

        telemetry.setMsTransmissionInterval(16);
    }

    public void doLoop() {
        drive.moveFreely(Range.clip(gamepad1.left_stick_y, -Globals.MAX_MOTOR_POWER, Globals.MAX_MOTOR_POWER), Range.clip(gamepad1.right_stick_y, -Globals.MAX_MOTOR_POWER, Globals.MAX_MOTOR_POWER));
        telemetry.addData("Left Motor Power", drive.leftDriveMotor.getPower());
        telemetry.addData("Right Motor Power", drive.rightDriveMotor.getPower());
        telemetry.update();
    }
}
