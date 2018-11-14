package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HookLiftingMechanism extends AbstractMechanism{

    public DcMotor motorSuper;

    public HookLiftingMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {

        super(hardwareMap, linearOpMode);
        motorSuper = hardwareMap.dcMotor.get("lifting_motor");
        motorSuper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void move(double power) {

        motorSuper.setPower(power);

    }

    public String getPosition() {

        return motorSuper.getCurrentPosition() + "";

    }
}