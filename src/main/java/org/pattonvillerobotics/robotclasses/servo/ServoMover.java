package org.pattonvillerobotics.robotclasses.servo;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.opmodes.autonomous.Globals;

/**
 * Created by skaggsw on 10/18/16.
 */

public class ServoMover {

    private Servo servoMover;

    public ServoMover(HardwareMap hardwareMap) {
        servoMover = hardwareMap.servo.get("servo_mover");
    }

    public void moveTo(Position position) {
        switch (position) {
            case DEFAULT:
                servoMover.setPosition(Globals.BUTTON_PRESSER_DEFAULT_POSITION);
                break;
            case LEFT:
                servoMover.setPosition(Globals.BUTTON_PRESSER_LEFT_POSITION);
                break;
            case RIGHT:
                servoMover.setPosition(Globals.BUTTON_PRESSER_RIGHT_POSITION);
                break;
        }
    }

    public enum Position {
        DEFAULT, LEFT, RIGHT
    }

}
