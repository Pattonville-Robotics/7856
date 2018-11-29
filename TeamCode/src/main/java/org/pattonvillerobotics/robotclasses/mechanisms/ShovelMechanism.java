package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ShovelMechanism extends AbstractMechanism {
    private Servo base_servo;
    private Servo elbow_servo;
    private Servo wrist_servo;

    // THIS CLASS IS STILL IN ALPHA DEV. DO NOT USE IT!

    public ShovelMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(linearOpMode, hardwareMap);
        base_servo = hardwareMap.servo.get("base_servo");
        elbow_servo = hardwareMap.servo.get("elbow_servo");
        wrist_servo = hardwareMap.servo.get("wrist_servo");
    }

    public void moveBaseArm(double position) {
        base_servo.setPosition(.5);
    }

    public void moveElbow_servo(double position) {
        elbow_servo.setPosition(.5);

    }
    public void moveWrist_servo(double position){
        wrist_servo.setPosition(.5);
}}
