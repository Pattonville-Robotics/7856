package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.robotclasses.mechanisms.ArmMechanism;
import org.pattonvillerobotics.robotclasses.mechanisms.HookLiftingMechanism;

/**
 * Created by wrightk03 on 3/5/19.
 * <p>
 * Purpouse:
 */
public class ArmTeleOp extends LinearOpMode {

    private ArmMechanism armMechanism;
    private ListenableGamepad armGamepad;
    private DcMotor baseMotor, spoolMotor;

    @Override
    public void runOpMode() {
        baseMotor.setPower(gamepad1.right_stick_y);
        spoolMotor.setPower(gamepad1.left_trigger + gamepad1.right_trigger);
    }

    private void initialize() {
        armGamepad = new ListenableGamepad();
        armMechanism = new ArmMechanism(this, hardwareMap);
        baseMotor = hardwareMap.dcMotor.get("base_motor");
        spoolMotor = hardwareMap.dcMotor.get("spool_motor");
    }

}
