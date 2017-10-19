package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.Globals;

/**
 * Created by martint08 on 9/30/17.
 */

public class GlyphGrabber {

    private Servo leftServo;
    private Servo rightServo;
    private Globals.GrabberPosition position;

    public GlyphGrabber(HardwareMap hardwareMap, Globals.GrabberPosition initialPosition) {
        leftServo = hardwareMap.servo.get("glyph-grabber-left");
        rightServo = hardwareMap.servo.get("glyph-grabber-right");
        position = initialPosition;
    }

    public void clamp() {
        leftServo.setPosition(0.7);
        rightServo.setPosition((0.7));
        position = Globals.GrabberPosition.CLAMPED;
    }

    public void release() {
        leftServo.setPosition(0);
        rightServo.setPosition(0);
        position = Globals.GrabberPosition.RELEASED;
    }

    public Globals.GrabberPosition getPosition() {
        return position;
    }

}