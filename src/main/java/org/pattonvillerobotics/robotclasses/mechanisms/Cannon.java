package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.opmodes.autonomous.Globals;

/**
 * Created by skaggsw on 10/27/16.
 */

public class Cannon extends AbstractMechanism {

    private DcMotor cannon;

    public Cannon(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        cannon = hardwareMap.dcMotor.get("particle_launcher");
        if (CustomizedRobotParameters.ROBOT_PARAMETERS.areEncodersEnabled()) {
            this.cannon.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void setToggle(boolean toggle) {
        cannon.setDirection(DcMotorSimple.Direction.REVERSE);
        if(toggle) {
            cannon.setPower(Globals.CANNON_POWER);
        }
        else {
            cannon.setPower(0);
        }
    }

    public void fire() {
        int targetPosition = 3000;
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
