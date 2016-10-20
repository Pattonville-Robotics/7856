package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.opmodes.autonomous.Globals;

/**
 * Created by pieperm on 10/20/16.
 */

public class ParticleLauncher {

    private GyroSensor gyro;
    private DcMotor particleLauncher;
    private DcMotor launcherRotater;

    private ParticleLauncher(HardwareMap hardwareMap) {
        gyro = hardwareMap.gyroSensor.get("name");
        particleLauncher = hardwareMap.dcMotor.get("name");
        launcherRotater = hardwareMap.dcMotor.get("name");
    }

    public void rotateLauncher() {

        gyro.calibrate();

        if(!gyro.isCalibrating()) {

            int x = 2;
            double t = 1;
            int currentAngle = gyro.rawY();
            int launchAngle = (int)Math.atan( (1.27 + 4.9 * Math.pow(t, 2)) / x );
            int degreesToTurn = launchAngle - currentAngle;

            //launcherRotater.

        }

    }


    public void launchParticle() {

        rotateLauncher();

        // Do processes here that are necessary to launch the particle
        particleLauncher.setPower(Globals.MAX_MOTOR_POWER);

    }

}
