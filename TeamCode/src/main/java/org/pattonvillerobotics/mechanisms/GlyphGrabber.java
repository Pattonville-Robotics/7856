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
    public static final double LEFT_TOP_CLAMP = 0.8;
    public static final double LEFT_TOP_RELEASE = 0.3;
    public static final double RIGHT_TOP_CLAMP = 0.3;
    public static final double RIGHT_TOP_RELEASE = 0.8;
    public static final double LEFT_BOTTOM_CLAMP = 0.2;
    public static final double LEFT_BOTTOM_RELEASE = 0.1;
    public static final double RIGHT_BOTTOM_CLAMP = 0.4;
    public static final double RIGHT_BOTTOM_RELEASE = 0.3;

    private Servo leftTopServo, rightTopServo, leftBottomServo, rightBottomServo;
    private Globals.GrabberState topState, bottomState;

    public GlyphGrabber(HardwareMap hardwareMap, LinearOpMode linearOpMode, Globals.GrabberState initialPosition) {
        super(hardwareMap, linearOpMode);
        try {
            leftTopServo = hardwareMap.servo.get("glyph-grabber-top-left");
            rightTopServo = hardwareMap.servo.get("glyph-grabber-top-right");
            leftBottomServo = hardwareMap.servo.get("glyph-grabber-bottom-left");
            rightBottomServo = hardwareMap.servo.get("glyph-grabber-bottom-right");
        } catch (IllegalArgumentException e) {
            linearOpMode.telemetry.addData(TAG, e.getMessage());
            linearOpMode.telemetry.update();
        }

        topState = initialPosition;
        bottomState = initialPosition;
    }

    public void clampTop() {
        leftTopServo.setPosition(LEFT_TOP_CLAMP);
        rightTopServo.setPosition((RIGHT_TOP_CLAMP));
        topState = Globals.GrabberState.CLAMPED;
    }

    public void clampBottom() {
        leftBottomServo.setPosition(LEFT_BOTTOM_CLAMP);
        rightBottomServo.setPosition(RIGHT_BOTTOM_CLAMP);
        bottomState = Globals.GrabberState.CLAMPED;
    }

    public void releaseTop() {
        leftTopServo.setPosition(LEFT_TOP_RELEASE);
        rightTopServo.setPosition(RIGHT_TOP_RELEASE);
        topState = Globals.GrabberState.RELEASED;
    }

    public void releaseBottom() {
        leftBottomServo.setPosition(LEFT_BOTTOM_RELEASE);
        rightBottomServo.setPosition(RIGHT_BOTTOM_RELEASE);
        bottomState = Globals.GrabberState.RELEASED;
    }

    public void clampBoth() {
        clampBottom();
        clampTop();
    }

    public void releaseBoth() {
        releaseBottom();
        releaseTop();
    }

    public void slightRelease() {
        leftTopServo.setPosition(0.70);
        rightTopServo.setPosition(0.35);
    }

    public Globals.GrabberState getGrabberTopState() {
        return topState;
    }

    public Globals.GrabberState getGrabberBottomState() {
        return bottomState;
    }

    public Servo getLeftTopServo() {
        return leftTopServo;
    }

    public Servo getRightTopServo() {
        return rightTopServo;
    }

    public Servo getLeftBottomServo() {
        return leftBottomServo;
    }

    public Servo getRightBottomServo() {
        return rightBottomServo;
    }

}