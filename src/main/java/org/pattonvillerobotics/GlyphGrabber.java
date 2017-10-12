package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;

/**
 * Created by martint08 on 9/30/17.
 */

public class GlyphGrabber {
    private Servo leftservo;
    private Servo rightservo;

    public GlyphGrabber(HardwareMap hardwareMap, LinearOpMode opMode) {
        leftservo = hardwareMap.servo.get("glyph-grabber-left");
        //rightservo = hardwareMap.servo.get("glyph-grabber-right");
    }

    public void clamp() {
        leftservo.setPosition(0.7);
        rightservo.setPosition((0.7));
    }

    public void release()

    {
        leftservo.setPosition(0);
        rightservo.setPosition(0);
    }
}