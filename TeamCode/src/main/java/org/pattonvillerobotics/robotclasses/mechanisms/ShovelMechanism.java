package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ShovelMechanism extends AbstractMechanism {
    public Servo base_servo;
    public Servo elbow_servo;
    public Servo wrist_servo;

    // THIS CLASS IS STILL IN ALPHA DEV. DO NOT USE IT!

    public ShovelMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(linearOpMode, hardwareMap);
        base_servo = hardwareMap.servo.get("base_motor");
        elbow_servo = hardwareMap.servo.get("elbow_servo");
        wrist_servo = hardwareMap.servo.get("wrist_servo");
    }

    public void moveBaseArm(double power) {
        base_servo.setPosition(.5);
    }

    public void moveElbow_servo(double power) {
        elbow_servo.setPosition(.5);

    }
    public void moveWrist_servo(double power){
        wrist_servo.setPosition(.5);
}}
