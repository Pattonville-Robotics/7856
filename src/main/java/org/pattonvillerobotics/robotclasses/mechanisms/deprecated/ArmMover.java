package org.pattonvillerobotics.robotclasses.mechanisms.deprecated;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.opmodes.autonomous.Globals;
import org.pattonvillerobotics.robotclasses.mechanisms.AbstractMechanism;

/**
 * Created by skaggsw on 10/18/16.
 */
@Deprecated
public class ArmMover extends AbstractMechanism {

    private Servo armMover;

    public ArmMover(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
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

    // Two methods for testing purposes
    public void incrementPosition() {
        armMover.setPosition(Math.max(1, armMover.getPosition()));
        linearOpMode.telemetry.addData("Arm position", armMover.getPosition());

    }
    public void decrementPosition() {
        armMover.setPosition(Math.min(0, armMover.getPosition()));
        linearOpMode.telemetry.addData("Arm position", armMover.getPosition());
    }

    public enum Position {
        DOWN, LEFT, UP, RIGHT
    }

}
