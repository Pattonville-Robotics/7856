package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ArmMechanism extends AbstractMechanism {

    private DcMotor armMotor;

    // THIS CLASS IS STILL IN ALPHA DEV. DO NOT USE IT!

    public ArmMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(linearOpMode, hardwareMap);

        armMotor = hardwareMap.dcMotor.get("arm_base");
        armMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void moveArm(double power) {
        armMotor.setPower(power);
    }

    public String getPosition() {
        return "Arm Motor: " + armMotor.getCurrentPosition();
    }
}
