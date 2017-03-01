package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.opmodes.autonomous.Globals;

/**
 * Created by skaggsw on 10/27/16.
 */

public class Cannon extends AbstractMechanism {

    private DcMotor cannon;

    public Cannon(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        cannon = hardwareMap.dcMotor.get("particle_launcher");
        cannon.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setToggle(boolean toggle) {
        if(toggle) {
            cannon.setPower(Globals.CANNON_POWER);
        }
        else {
            cannon.setPower(0);
        }
    }

    public void fire() {
        int targetPosition = 3500;
        cannon.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        cannon.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        cannon.setTargetPosition(targetPosition);
        cannon.setPower(Globals.CANNON_POWER);
        while (cannon.isBusy() && !linearOpMode.isStopRequested()) {
            Thread.yield();
            linearOpMode.telemetry.update();
        }
        cannon.setPower(0);
        cannon.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public DcMotor getCannon() {
        return cannon;
    }
}
