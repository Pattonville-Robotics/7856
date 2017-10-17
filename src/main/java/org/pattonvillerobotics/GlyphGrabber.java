package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by trieud01 on 9/30/17.
 */

public class GlyphGrabber {
    private Servo leftServo;
    public Servo rightServo;

    public GlyphGrabber(HardwareMap hardwareMap, LinearOpMode opMode) {
        leftServo = hardwareMap.servo.get("glyph-grabber-left");
        rightServo = hardwareMap.servo.get("glyph-grabber-right");
    }

    public void clamp() {
        leftServo.setPosition(0.7);
        rightServo.setPosition(0);

    }
    public void release(){
        leftServo. setPosition(0);
        rightServo. setPosition(0.7);
    }

}
