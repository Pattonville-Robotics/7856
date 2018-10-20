package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;

@TeleOp(name = "ExampleTeleOp", group = "Examples")
public class ExampleTeleOp extends LinearOpMode {

    public SimpleDrive drive;

    @Override
    public void runOpMode() {

        initialize();

        waitForStart();

        while(opModeIsActive()) {

        }
    }

    public void initialize() {
        drive = new SimpleDrive(this, hardwareMap);
    }
}
