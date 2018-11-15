package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ShovelMechanism extends AbstractMechanism{
    public DcMotor baseMotor;
    public Servo servo1;
    public Servo servo2;

    public ShovelMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode){
        super(hardwareMap, linearOpMode);
        baseMotor = hardwareMap.dcMotor.get("base_motor");
        baseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        servo1 = hardwareMap.servo.get("servo_1");
        servo2 = hardwareMap.servo.get("servo_2");
    }
    public void moveBaseArm(double power){

    }


}
