package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.opmodes.autonomous.Globals;
import org.pattonvillerobotics.opmodes.teleop.TeleOpTest;

/**
 * Created by murphyk01 on 12/6/16.
 */

public class newArmMover extends AbstractMechanism {

    private Servo armMoverRight;
    private Servo armMoverLeft;

    public newArmMover(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        armMoverLeft = hardwareMap.servo.get("arm_left");
        armMoverRight = hardwareMap.servo.get("arm_right");

    }

    public void update(boolean toggle, TeleOpTest.Position position) {
        switch(position) {
            case RIGHT:
                if (toggle) {
                    armMoverRight.setPosition(Globals.BUTTON_PRESSOR_OUT_POSITION);
                } else {
                    armMoverRight.setPosition(Globals.BUTTON_PRESSOR_IN_POSITION);
                }
                break;
            case LEFT:
                if(toggle){
                    armMoverLeft.setPosition(Globals.BUTTON_PRESSOR_OUT_POSITION);
                } else {
                    armMoverLeft.setPosition(Globals.BUTTON_PRESSOR_IN_POSITION);
                }
                break;
        }
    }
}