package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by wrightk03 on 11/17/18.
 * <p>
 * Purpouse:
 */
public class TeamMarkerMechanism extends AbstractMechanism {
    public CRServo servo;

    public TeamMarkerMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        servo = hardwareMap.crservo.get("servo");
    }

    public void moveTeamMarker (double power){
        servo.setPower(0.5);
    }
}