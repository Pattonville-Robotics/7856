package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by wrightk03 on 11/17/18.
 * <p>
 * Purpouse:
 */

public class TeamMarkerMechanism extends AbstractMechanism {

    private Servo servo;

    public TeamMarkerMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(linearOpMode, hardwareMap);
        servo = hardwareMap.servo.get("team_marker_servo");
    }

    public void move(double position){
        servo.setPosition(servo.getPosition() + position);
    }
}