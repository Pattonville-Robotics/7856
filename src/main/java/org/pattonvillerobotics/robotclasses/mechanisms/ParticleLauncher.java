package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by skaggsw on 10/27/16.
 */

public class ParticleLauncher extends AbstractMechanism {

    public DcMotor particleLauncher;
    public boolean launcherPrimed = false;
    public int targetPosition = 0;

    public ParticleLauncher(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        particleLauncher = hardwareMap.dcMotor.get("particle_launcher");
        if (CustomizedRobotParameters.ROBOT_PARAMETERS.areEncodersEnabled()) {
            this.particleLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void launchLauncher() {

        targetPosition = particleLauncher.getCurrentPosition() + 1440;

        particleLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        particleLauncher.setTargetPosition(targetPosition);

        particleLauncher.setDirection(DcMotorSimple.Direction.FORWARD);

        particleLauncher.setPower(0.7);
        while (java.lang.Math.abs(particleLauncher.getCurrentPosition() - targetPosition) > 10) {
            if (linearOpMode.isStopRequested())
                break;
        }
        particleLauncher.setPower(0);


    }

    public void primeLauncher() {
        //Pull the launcher bottom back

        if (!launcherPrimed) {

            targetPosition = particleLauncher.getCurrentPosition() + 1040;

            particleLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            particleLauncher.setTargetPosition(targetPosition);

            particleLauncher.setDirection(DcMotorSimple.Direction.FORWARD);

            particleLauncher.setPower(0.7);
            while (java.lang.Math.abs(particleLauncher.getCurrentPosition() - targetPosition) > 20) {
                if (linearOpMode.isStopRequested())
                    break;
            }
            particleLauncher.setPower(0);

            launcherPrimed = true;
        }
    }

    public void releaseLauncher() {

        if (launcherPrimed) {

            targetPosition = particleLauncher.getCurrentPosition() + 400;

            particleLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

            particleLauncher.setTargetPosition(targetPosition);

            particleLauncher.setDirection(DcMotorSimple.Direction.FORWARD);

            particleLauncher.setPower(0.7);
            while (java.lang.Math.abs(particleLauncher.getCurrentPosition() - targetPosition) > 20) {
                if (linearOpMode.isStopRequested())
                    break;
            }
            particleLauncher.setPower(0);

            launcherPrimed = false;
        }
    }

    public void holdPrime() {

        if (launcherPrimed && (java.lang.Math.abs(particleLauncher.getCurrentPosition() - targetPosition) > 20)) {
            particleLauncher.setPower(0.2);
            while (particleLauncher.getCurrentPosition() < targetPosition) {
                if (linearOpMode.isStopRequested()) {
                    break;
                }
            }
            particleLauncher.setPower(0);
        }
    }

    public void returntozero() {

        targetPosition = 0;

        particleLauncher.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        particleLauncher.setTargetPosition(targetPosition);

        particleLauncher.setDirection(DcMotorSimple.Direction.FORWARD);

        particleLauncher.setPower(0.7);
        while (java.lang.Math.abs(particleLauncher.getCurrentPosition() - targetPosition) > 20) {
            if (linearOpMode.isStopRequested())
                break;
        }
        particleLauncher.setPower(0);

    }

    public void turnmotorleft() {

        targetPosition = particleLauncher.getCurrentPosition() + 16;

        particleLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        particleLauncher.setTargetPosition(targetPosition);

        particleLauncher.setDirection(DcMotorSimple.Direction.FORWARD);

        particleLauncher.setPower(0.7);
        while (java.lang.Math.abs(particleLauncher.getCurrentPosition() - targetPosition) > 20) {
            if (linearOpMode.isStopRequested())
                break;
        }
        particleLauncher.setPower(0);

    }

    public void turnmotorright() {

        targetPosition = particleLauncher.getCurrentPosition() - 16;

        particleLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        particleLauncher.setTargetPosition(targetPosition);

        particleLauncher.setDirection(DcMotorSimple.Direction.FORWARD);

        particleLauncher.setPower(0.7);
        while (java.lang.Math.abs(particleLauncher.getCurrentPosition() - targetPosition) > 5) {
            if (linearOpMode.isStopRequested())
                break;
        }
        particleLauncher.setPower(0);

    }

    public void update(boolean toggle) {

        if(toggle) {
            particleLauncher.setPower(0.2);
        }
        else {
            particleLauncher.setPower(0);
        }

    }

}
