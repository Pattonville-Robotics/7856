package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by murphyk01 on 12/6/16.
 */

public class ButtonPresser extends AbstractMechanism {

    private Servo buttonPresserRight, buttonPresserLeft;

    public ButtonPresser(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        buttonPresserLeft = hardwareMap.servo.get("arm_left");
        buttonPresserRight = hardwareMap.servo.get("arm_right");
        setRightIn();
        setLeftIn();

    }

    public ButtonPresser setLeftOut() {
        buttonPresserLeft.setPosition(1);
        return this;
    }

    public ButtonPresser setLeftIn() {
        buttonPresserLeft.setPosition(0);
        return this;
    }

    public ButtonPresser setRightOut() {
        buttonPresserRight.setPosition(0);
        return this;
    }

    public ButtonPresser setRightIn() {
        buttonPresserRight.setPosition(0);
        return this;
    }
}