package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by skaggsw on 10/27/16.
 */

public class OtherParticleLauncher extends AbstractMechanism {

    public DcMotor particleLauncher;

    public OtherParticleLauncher(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        particleLauncher = hardwareMap.dcMotor.get("particle_launcher");
        if (CustomizedRobotParameters.ROBOT_PARAMETERS.areEncodersEnabled()) {
            this.particleLauncher.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public void primeLauncher() {
        //Pull the launcher bottom back

        int targetPosition;

        targetPosition = 0; //This needs to be set to the actual target position

        particleLauncher.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        particleLauncher.setTargetPosition(targetPosition);

        particleLauncher.setDirection(DcMotorSimple.Direction.FORWARD);

        particleLauncher.setPower(0.7);
        while (particleLauncher.getCurrentPosition() < targetPosition) {
            if (linearOpMode.isStopRequested())
                break;
        }
        particleLauncher.setPower(0);
    }

    public void releaseLauncher() {

        int targetPosition;

        targetPosition = 0; //This needs to be set to the actual target position

        particleLauncher.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        particleLauncher.setTargetPosition(targetPosition);

        particleLauncher.setDirection(DcMotorSimple.Direction.FORWARD);

        particleLauncher.setPower(0.7);
        while (particleLauncher.getCurrentPosition() < targetPosition) {
            if (linearOpMode.isStopRequested())
                break;
        }
        particleLauncher.setPower(0);
    }
}
