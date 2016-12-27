package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.enums.ArmPosition;

/**
 * Created by murphyk01 on 12/6/16.
 */

public class ArmMover extends AbstractMechanism {

    private Servo armMoverRight;
    private Servo armMoverLeft;

    public ArmMover(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        armMoverLeft = hardwareMap.servo.get("arm_left");
        armMoverRight = hardwareMap.servo.get("arm_right");

    }

    public void setPosition(Servo arm, ArmPosition position) {
        arm.setPosition(position.getValue());
    }

    public Servo getArmMoverRight() {
        return armMoverRight;
    }

    public Servo getArmMoverLeft() {
        return armMoverLeft;
    }
}