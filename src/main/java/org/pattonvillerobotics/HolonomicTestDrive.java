package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;

/**
 * Created by pieperm on 9/26/17.
 */

@TeleOp(name = "HolonomicTestDrive", group = OpModeGroups.TESTING)
public class HolonomicTestDrive extends LinearOpMode {

    private HolonomicDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new HolonomicDrive(hardwareMap, this);
        telemetry.addData("Holonomic", "Initialized");

        waitForStart();
        telemetry.addData("Holonomic", "Opmode started");

        while(opModeIsActive()) {
            drive.drive(gamepad1.left_stick_y, gamepad1.left_stick_x);
            idle();
        }

    }
}
