package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.commoncode.enums.Direction;

public class HookLiftingMechanism extends AbstractMechanism{

    public DcMotor motorSuper;

    public HookLiftingMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        motorSuper = hardwareMap.dcMotor.get("lifting_motor");
        motorSuper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorSuper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void move(double power) {
        motorSuper.setPower(power);
    }

    public void lower(double power) {
        motorSuper.setTargetPosition(motorSuper.getCurrentPosition()+ (1440*6));
        while (Math.abs(motorSuper.getCurrentPosition()-motorSuper.getTargetPosition() ) > 16)  {
            motorSuper.setDirection(DcMotorSimple.Direction.FORWARD);
            motorSuper.setPower(0.5);
        }
        motorSuper.setPower(power);
    }

    public void raise(double power) {
        motorSuper.setTargetPosition(motorSuper.getCurrentPosition() - (1440*6));
        while (Math.abs(motorSuper.getCurrentPosition()-motorSuper.getTargetPosition() ) > 16)  {
            motorSuper.setDirection(DcMotorSimple.Direction.REVERSE);
            motorSuper.setPower(0.5);
        }
         motorSuper.setPower(power);
    }

    public String getPosition() {
        return motorSuper.getCurrentPosition() + "";
    }

    }