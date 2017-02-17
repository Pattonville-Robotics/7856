package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.opmodes.autonomous.Globals;

/**
 * Created by skaggsw on 10/27/16.
 */

public class Cannon extends AbstractMechanism {

    private DcMotor particleLauncher;
    private int targetPosition = 0;

    public Cannon(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        particleLauncher = hardwareMap.dcMotor.get("particle_launcher");
        if (CustomizedRobotParameters.ROBOT_PARAMETERS.areEncodersEnabled()) {
            this.particleLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void launchLauncher() {


        particleLauncher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        particleLauncher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        targetPosition = particleLauncher.getCurrentPosition() + 1440;

        particleLauncher.setTargetPosition(targetPosition);

        particleLauncher.setDirection(DcMotorSimple.Direction.FORWARD);

        particleLauncher.setPower(Globals.CANNON_POWER);
        while (java.lang.Math.abs(particleLauncher.getCurrentPosition() - targetPosition) > 10) {
            if (linearOpMode.isStopRequested())
                break;
        }
        particleLauncher.setPower(0);


    }

    public void reverselaunch() {


        particleLauncher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        particleLauncher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        targetPosition = particleLauncher.getCurrentPosition() - 1440;

        particleLauncher.setTargetPosition(targetPosition);

        particleLauncher.setDirection(DcMotorSimple.Direction.REVERSE);

        particleLauncher.setPower(Globals.CANNON_POWER);
        while (java.lang.Math.abs(particleLauncher.getCurrentPosition() - targetPosition) > 10) {
            if (linearOpMode.isStopRequested())
                break;
        }
        particleLauncher.setPower(0);


    }

    public void update(boolean toggle) {

        particleLauncher.setDirection(DcMotorSimple.Direction.REVERSE);
        if(toggle) {
            particleLauncher.setPower(Globals.CANNON_POWER);
        }
        else {
            particleLauncher.setPower(0);
        }

    }

    public void fire() {
        int targetPosition = 2800;
        particleLauncher.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        particleLauncher.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        particleLauncher.setTargetPosition(targetPosition);
        particleLauncher.setPower(Globals.CANNON_POWER);
        while (!reachedTarget(particleLauncher.getCurrentPosition(), targetPosition) && !linearOpMode.isStopRequested()) {
            Thread.yield();
            linearOpMode.telemetry.update();
        }
        particleLauncher.setPower(0);
    }

    protected boolean reachedTarget(int currentPosition, int targetPosition) {
        return FastMath.abs(currentPosition - targetPosition) < 16;
    }

    public DcMotor getCannon() {
        return particleLauncher;
    }
}
