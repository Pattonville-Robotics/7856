package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.Globals;

/**
 * Created by pieperm on 10/19/17.
 */

public class RelicGrabber {

    private Servo servoClampOut, servoClampIn,servoMoveTop;
    private Globals.GrabberPosition position;

    public RelicGrabber(HardwareMap hardwareMap, Globals.GrabberPosition  initialPosition) {

        servoClampOut = hardwareMap.servo.get("servo-clamp-out");
        servoClampIn = hardwareMap.servo.get("servo-clamp-in");
        servoMoveTop= hardwareMap.servo.get("servo-move-top");

    }

    public void liftRelic(){
        servoMoveTop.setPosition(1);
    }


    public void clamp() {
        position = Globals.GrabberPosition.CLAMPED;
        servoClampIn.setPosition(0.7);
        servoClampOut.setPosition(0.7);

    }

    public void release() {
        position = Globals.GrabberPosition.RELEASED;
        servoClampOut.setPosition(0);
        servoClampIn.setPosition(0);
    }

    public Globals.GrabberPosition getPosition() {
        return position;
    }

}
