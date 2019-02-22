package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by wrightk03 on 12/8/18.
 * <p>
 * Purpouse:
 */
public class ArmMechanism extends AbstractMechanism {

    public DcMotor ArmMotor;

    public ArmMechanism(LinearOpMode linearOpMode, HardwareMap hardwareMap) {
        super(linearOpMode, hardwareMap);

        ArmMotor = hardwareMap.dcMotor.get("arm_Base");
        ArmMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ArmMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);


    }

    public void moveArm(double power) {
        ArmMotor.setPower(power);
    }

    public String getPosition() {
        return ArmMotor.getCurrentPosition() + "ArmMotor";
    }
}
