package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.Globals;

/**
 * Created by martint08 on 9/30/17.
 */

public class GlyphGrabber extends AbstractMechanism {

    public static final String TAG = GlyphGrabber.class.getSimpleName();
    private Servo leftServo;
    private Servo rightServo;
    private Globals.GrabberPosition position;

    public GlyphGrabber(HardwareMap hardwareMap, LinearOpMode linearOpMode, Globals.GrabberPosition initialPosition) {
        super(hardwareMap, linearOpMode);
        try {
            leftServo = hardwareMap.servo.get("glyph-grabber-left");
            rightServo = hardwareMap.servo.get("glyph-grabber-right");
        } catch (IllegalArgumentException e) {
            linearOpMode.telemetry.addData(TAG, e.getMessage());
            linearOpMode.telemetry.update();
        }

        position = initialPosition;
    }

    public void clamp() {
        leftServo.setPosition(0.8);
        rightServo.setPosition((0.3));
        position = Globals.GrabberPosition.CLAMPED;
    }

    public void release() {
        leftServo.setPosition(0.3);
        rightServo.setPosition(0.8);
        position = Globals.GrabberPosition.RELEASED;
    }

    public void slightRelease() {
        leftServo.setPosition(0.7);
        rightServo.setPosition(0.35);
    }

    public void incrementLeftPosition() {
        leftServo.setPosition(leftServo.getPosition() + 0.05);
    }

    public void incrementRightPosition() {
        rightServo.setPosition(rightServo.getPosition() + 0.05);
    }

    public void decrementLeftPosition() {
        leftServo.setPosition(leftServo.getPosition() - 0.05);
    }

    public void decrementRightPosition() {
        rightServo.setPosition(rightServo.getPosition() - 0.05);
    }

    public Globals.GrabberPosition getGrabberPosition() {
        return position;
    }

    public Servo getLeftServo() {
        return leftServo;
    }

    public Servo getRightServo() {
        return rightServo;
    }

}