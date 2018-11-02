package org.pattonvillerobotics.robotclass;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.autonomous.robotclasses.AbstractMechanism;
public class liftingmechanism extends AbstractMechanism{

    public DcMotor MotorSuper;

    public liftingmechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap,linearOpMode);
        MotorSuper = hardwareMap.dcMotor.get("motor2");
    }
    public void lower() {
        MotorSuper.setTargetPosition(MotorSuper.getCurrentPosition()-(1440*6));
        while(Math.abs(MotorSuper.getCurrentPosition()-MotorSuper.getTargetPosition()) > 16) {
            MotorSuper.setPower(0.5);
        }
        MotorSuper.setPower(0);
    }

    public void raise() {
        MotorSuper.setTargetPosition(MotorSuper.getCurrentPosition()-(1440*6));
        while(Math.abs(MotorSuper.getCurrentPosition()-MotorSuper.getTargetPosition()) > 16) {
            MotorSuper.setPower(0.7);
        }
        MotorSuper.setPower(1); //set the motor power to 1
    }



}