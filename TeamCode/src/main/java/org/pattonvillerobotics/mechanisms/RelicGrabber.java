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
    private Globals.GrabberState position;

    public RelicGrabber(HardwareMap hardwareMap, LinearOpMode linearOpMode, Globals.GrabberState initialPosition) {

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
        position = Globals.GrabberState.CLAMPED;
    }

    public void release() {
        position = Globals.GrabberState.RELEASED;
    }

    public Globals.GrabberState getPosition() {
        return position;
    }

}
