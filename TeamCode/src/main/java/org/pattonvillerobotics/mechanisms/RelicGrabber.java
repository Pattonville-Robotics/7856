package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.Globals;

/**
 * Created by pieperm on 10/19/17.
 */

public class RelicGrabber extends AbstractMechanism {

    public static final String TAG = RelicGrabber.class.getSimpleName();
    private Servo servo;
    private Globals.GrabberPosition position;

    public RelicGrabber(HardwareMap hardwareMap, LinearOpMode linearOpMode, Globals.GrabberPosition initialPosition) {

        super(hardwareMap, linearOpMode);
        try {
            servo = hardwareMap.servo.get("relic-grabber-servo");
        } catch (IllegalArgumentException e) {
            linearOpMode.telemetry.addData(TAG, e.getMessage());
            linearOpMode.telemetry.update();
        }

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
