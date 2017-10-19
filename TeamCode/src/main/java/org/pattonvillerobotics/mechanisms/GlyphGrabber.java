package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by martint08 on 9/30/17.
 */

public class GlyphGrabber {
    private Servo leftServo;
    private Servo rightServo;

    public GlyphGrabber(HardwareMap hardwareMap, LinearOpMode opMode) {
        leftServo = hardwareMap.servo.get("glyph-grabber-left");
        rightServo = hardwareMap.servo.get("glyph-grabber-right");
    }

    public void clamp() {
        leftServo.setPosition(0.7);
        rightServo.setPosition((0.7));
    }

    public void release()

    {
        leftServo.setPosition(0);
        rightServo.setPosition(0);
    }
}