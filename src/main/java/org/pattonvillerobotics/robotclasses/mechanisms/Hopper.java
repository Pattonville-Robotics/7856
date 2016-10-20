package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.opmodes.autonomous.Globals;

/**
 * Created by pieperm on 10/20/16.
 */

public class Hopper {

    private DcMotor hopper;

    private Hopper(HardwareMap hardwareMap) {
        hopper = hardwareMap.dcMotor.get("name");
    }

    public void collect() {
        hopper.setPower(Globals.MAX_MOTOR_POWER);
    }

    public void expel() {
        hopper.setPower(Globals.MAX_MOTOR_POWER);
    }

    public void stop() {
        hopper.setPower(0);
    }

}
