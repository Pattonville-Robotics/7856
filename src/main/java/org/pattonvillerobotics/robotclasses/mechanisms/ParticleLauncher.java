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

    public void primeLauncher() {
        //Pull the launcher bottom back

        if (!launcherPrimed) {

            targetPosition = 310;

            particleLauncher.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            particleLauncher.setTargetPosition(targetPosition);

            particleLauncher.setDirection(DcMotorSimple.Direction.FORWARD);

            particleLauncher.setPower(0.7);
            while (particleLauncher.getCurrentPosition() < targetPosition) {
                if (linearOpMode.isStopRequested())
                    break;
            }
            particleLauncher.setPower(0);

            launcherPrimed = true;
        }
    }

    public void releaseLauncher() {

        if (launcherPrimed) {

            int targetPosition;

            targetPosition = 360;

            particleLauncher.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            particleLauncher.setTargetPosition(targetPosition);

            particleLauncher.setDirection(DcMotorSimple.Direction.FORWARD);

            particleLauncher.setPower(0.7);
            while (particleLauncher.getCurrentPosition() < targetPosition) {
                if (linearOpMode.isStopRequested())
                    break;
            }
            particleLauncher.setPower(0);

            launcherPrimed = false;
        }
    }

    public void holdPrime() {

        if (launcherPrimed && (particleLauncher.getCurrentPosition() < targetPosition)) {
            particleLauncher.setPower(0.2);
            while (particleLauncher.getCurrentPosition() < targetPosition) {
                if (linearOpMode.isStopRequested()) {
                    break;
                }
            }
            particleLauncher.setPower(0);
        }
    }
}
