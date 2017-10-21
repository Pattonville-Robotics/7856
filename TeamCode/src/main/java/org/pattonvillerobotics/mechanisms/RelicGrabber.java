package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.Globals;

/**
 * Created by pieperm on 10/19/17.
 */

public class RelicGrabber {

    private Servo servo;
    private Globals.GrabberPosition position;

    public RelicGrabber(HardwareMap hardwareMap, Globals.GrabberPosition initialPosition) {

        servo = hardwareMap.servo.get("relic-grabber-motor");
        position = initialPosition;

    }

    public void clamp() {
        position = Globals.GrabberPosition.CLAMPED;
    }

    public void release() {
        position = Globals.GrabberPosition.RELEASED;
    }

    public Globals.GrabberPosition getPosition() {
        return position;
    }

}
