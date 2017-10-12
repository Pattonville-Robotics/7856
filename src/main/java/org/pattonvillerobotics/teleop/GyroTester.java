package org.pattonvillerobotics.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.REVGyro;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;

/**
 * Created by pieperm on 10/12/17.
 */
@TeleOp(name = "GyroTester", group = OpModeGroups.TESTING)
public class GyroTester extends LinearOpMode {

    private REVGyro gyro;

    @Override
    public void runOpMode() throws InterruptedException {

        gyro = new REVGyro(hardwareMap);

        waitForStart();

        while (opModeIsActive()) {

            gyro.getGyroTelemetry(telemetry);

            idle();

        }

    }
}
