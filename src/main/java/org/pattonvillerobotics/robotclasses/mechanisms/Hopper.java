package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.opmodes.autonomous.Globals;

/**
 * Created by pieperm on 10/20/16.
 */

public class Hopper extends AbstractMechanism {

    private DcMotor hopper;

    public Hopper(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        hopper = hardwareMap.dcMotor.get("hopper");
    }
    public void update(boolean toggle) {
        if (toggle) {
            hopper.setPower(-(Globals.MAX_MOTOR_POWER));
        } else {
            hopper.setPower(0);
        }
    }
}