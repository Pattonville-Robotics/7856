package org.pattonvillerobotics.robotClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.autonomous.robotclasses.AbstractMechanism;

public class LinearSlides extends AbstractMechanism {
    public DcMotor forwardMotor;
    public DcMotor backwardMotor;

    public LinearSlides(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        forwardMotor = hardwareMap.dcMotor.get("forward_Motor");
        forwardMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        backwardMotor = hardwareMap.dcMotor.get("backward_Motor");
        backwardMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setMotorPower(double power) {
        forwardMotor.setPower(power);
        backwardMotor.setPower(power);
    }
}