package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class TeamMarkerMechanism extends AbstractMechanism {

    private Servo servo;

    public TeamMarkerMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(linearOpMode, hardwareMap);
        servo = hardwareMap.servo.get("team_marker");
    }

    public void moveTeamMarker(double position) {
        servo.setPosition(position);
    }
}