package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by wrightk03 on 12/8/18.
 * <p>
 * Purpouse:
 */
public class ArmMechanism extends AbstractMechanism {

    private DcMotor BaseMotor, Spool;

    public ArmMechanism(LinearOpMode linearOpMode, HardwareMap hardwareMap) {
        super(linearOpMode, hardwareMap);

        BaseMotor = hardwareMap.dcMotor.get("base_motor");
        BaseMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BaseMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Spool = hardwareMap.dcMotor.get("spool_motor");
        Spool.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Spool.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void move(double power) {
        if(power < 0){
            BaseMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        } else {
            BaseMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        }
        BaseMotor.setPower(power);
    }

    public int getPosition() {
        return BaseMotor.getCurrentPosition();
    }
}
