package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.enums.ArmPosition;

/**
 * Created by murphyk01 on 12/6/16.
 */

public class ButtonPresser extends AbstractMechanism {

    private Servo buttonPresserRight;
    private Servo buttonPresserLeft;

    public ButtonPresser(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        buttonPresserLeft = hardwareMap.servo.get("arm_left");
        buttonPresserRight = hardwareMap.servo.get("arm_right");
        setRightIn();
        setLeftIn();

    }

    public void setPosition(Servo arm, ArmPosition position) {
        arm.setPosition(position.getValue());
    }

    public void toggle(Servo arm) {
        if(arm.getPosition() == ArmPosition.OUT.getValue()) {
            setPosition(arm, ArmPosition.IN);
        } else {
            setPosition(arm, ArmPosition.OUT);
        }
    }

    public ButtonPresser setLeftOut() {
        setPosition(buttonPresserLeft, ArmPosition.IN);
        return this;
    }

    public ButtonPresser setLeftIn() {
        setPosition(buttonPresserLeft, ArmPosition.OUT);
        return this;
    }

    public ButtonPresser setRightOut() {
        setPosition(buttonPresserRight, ArmPosition.OUT);
        return this;
    }

    public ButtonPresser setRightIn() {
        setPosition(buttonPresserRight, ArmPosition.IN);
        return this;
    }

    public Servo getButtonPresserRight() {
        return buttonPresserRight;
    }

    public Servo getButtonPresserLeft() {
        return buttonPresserLeft;
    }
}