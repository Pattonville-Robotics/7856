package org.pattonvillerobotics.robotClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HookLiftingMechanism extends AbstractMechanism{

    public DcMotor motor1;
    public DcMotor motor2;

    public HookLiftingMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        motor1 = hardwareMap.dcMotor.get("hook_lifting_mechanism") ;
        motor2 = hardwareMap.dcMotor.get("hook_lifting_mechanism") ;
        //linearOpMode.telemetry.addData("hook position", hook.motor1.getC);
        linearOpMode.telemetry.update() ;

    }

    public void lower() {
        motor1.setTargetPosition(motor1.getCurrentPosition()+ (1440*6));
        while (Math.abs(motor1.getCurrentPosition()-motor1.getTargetPosition() ) > 16)  {
            motor1.setPower(0.5) ;
        }
        motor1.setPower(0);


    }

    public void raise() {
        motor1.setTargetPosition(motor1.getCurrentPosition() - (1440*6));
        while (Math.abs(motor1.getCurrentPosition()-motor1.getTargetPosition() ) > 16) ; {
            motor1.setPower(-0.5) ;

    }
        motor1.setPower(0);
    }
}
