package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.Globals;

/**
 * Created by martint08 on 9/30/17.
 */

public class GlyphGrabber extends AbstractMechanism {

    public static final String TAG = GlyphGrabber.class.getSimpleName();
    public static final double LEFT_MAX = 0.8;
    public static final double LEFT_MIN = 0.3;
    public static final double RIGHT_MAX = 0.8;
    public static final double RIGHT_MIN = 0.3;
    private Servo leftServo, rightServo;
    private CRServo leftCRServo, rightCRServo;
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

    public void collect() {

    }

    public void eject() {

    }

    public void clamp() {
        leftServo.setPosition(LEFT_MAX);
        rightServo.setPosition((RIGHT_MIN));
        position = Globals.GrabberPosition.CLAMPED;
    }

    public void release() {
        leftServo.setPosition(LEFT_MIN);
        rightServo.setPosition(RIGHT_MAX);
        position = Globals.GrabberPosition.RELEASED;
    }

    public void slightRelease() {
        leftServo.setPosition(0.70);
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

    public boolean inBounds() {
        return leftServo.getPosition() > LEFT_MIN
                && leftServo.getPosition() < LEFT_MAX
                && rightServo.getPosition() > RIGHT_MIN
                && rightServo.getPosition() < RIGHT_MAX;
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