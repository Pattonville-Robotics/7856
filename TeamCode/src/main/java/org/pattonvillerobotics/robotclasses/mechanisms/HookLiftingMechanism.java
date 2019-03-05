package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HookLiftingMechanism extends AbstractMechanism {

    private DcMotor motorSuper;

    public HookLiftingMechanism(LinearOpMode linearOpMode, HardwareMap hardwareMap) {
        super(linearOpMode, hardwareMap);
        motorSuper = hardwareMap.dcMotor.get("lifting_motor");
        motorSuper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorSuper.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void move(double power) {
        motorSuper.setPower(power);
    }

    public int getPosition() {
        return motorSuper.getCurrentPosition();
    }

}