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
    public CRServo servo;
    LinearOpMode linearOpMode;


    public TeamMarkerMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(linearOpMode, hardwareMap);
        servo = hardwareMap.crservo.get("servo");
        this.linearOpMode = linearOpMode;
    }

    public void moveTeamMarker (double power){
        servo.setPower(0.5);
        linearOpMode.sleep(5000);
    }
    public void stopTeamMarker(double power){
        servo.setPower(0);
        linearOpMode.sleep(5000);
    }
}