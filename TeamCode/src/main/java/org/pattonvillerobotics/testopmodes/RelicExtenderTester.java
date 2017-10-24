package org.pattonvillerobotics.testopmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.mechanisms.RelicExtender;

/**
 * Created by pieperm on 10/23/17.
 */

public class RelicExtenderTester extends LinearOpMode {

    private RelicExtender relicExtender;
    private static final String TAG = RelicExtenderTester.class.getSimpleName();

    @Override
    public void runOpMode() throws InterruptedException {

        relicExtender = new RelicExtender(hardwareMap);

        waitForStart();

        telemetry.addData(TAG, "Use the right joystick to affect the motor");
        telemetry.update();

        while(opModeIsActive()) {

            relicExtender.getMotor().setPower(gamepad1.right_stick_y);

            idle();

        }

    }
}
