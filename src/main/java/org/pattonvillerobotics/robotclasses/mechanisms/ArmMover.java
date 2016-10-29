package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.opmodes.autonomous.Globals;

/**
 * Created by skaggsw on 10/18/16.
 */

public class ArmMover {

    private Servo armMover;

    public ArmMover(HardwareMap hardwareMap) {
        armMover = hardwareMap.servo.get("arm_mover");
    }

    public void moveTo(Position position) {
        switch (position) {
            case DOWN:
                armMover.setPosition(Globals.BUTTON_PRESSER_DOWN_POSITION);
                break;
            case LEFT:
                armMover.setPosition(Globals.BUTTON_PRESSER_LEFT_POSITION);
                break;
            case UP:
                armMover.setPosition(Globals.BUTTON_PRESSER_UP_POSITION);
            case RIGHT:
                armMover.setPosition(Globals.BUTTON_PRESSER_RIGHT_POSITION);
                break;
        }
    }

    public enum Position {
        DOWN, LEFT, UP, RIGHT
    }

}
