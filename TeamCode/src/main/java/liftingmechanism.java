package org.pattonvillerobotics.robotclass;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.pattonvillerobotics.robotclass.AbstractMechanism;

public class liftingmechanism extends AbstractMechanism{

    public DcMotor motor1;
    public DcMotor motorr2;

    public liftingmechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap,linearOpMode);
        motorr2 = hardwareMap.dcMotor.get("motor2");
    }
    public void lower() {
        motorr2.setTargetPosition(motorr2.getCurrentPosition()-(1440*6)
        while(Math.abs(motorr2.getCurrentPosition()-motorr2.getTargetPosition()) > 16) {
            motorr2.setPower(0.5);
        }
        motorr2.setPower(0);
    }

    public void raise() {
        motorr2.setTargetPosition(motorr2.getCurrentPosition()-(1440*6)');'
        while(Math.abs(motorr2.getCurrentPosition()-motorr2.getTargetPosition()) > 16) {
            motorr2.setPower(0.7);
    }
    motorr2.setPower(0);
    }



}
