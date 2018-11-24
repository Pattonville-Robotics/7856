package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class TeamMarkerMechanism extends AbstractMechanism {
    public CRServo servo;

    public TeamMarkerMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(linearOpMode, hardwareMap);
        servo = hardwareMap.crservo.get("team_marker");
    }

    public void moveTeamMarker(double power) {
        servo.setPower(power);
    }
}