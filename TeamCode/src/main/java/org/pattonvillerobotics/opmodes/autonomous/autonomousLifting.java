package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;

@Autonomous(name = "SammyAutonomous", group = "Autonomous")
public class SammyAutonomous extends LinearOpMode {

    public SimpleDrive drive;

    @Override
    public void runOpMode() {

        initialize();

        waitForStart();



    }

    public void initialize() {
        drive = new SimpleDrive(this, hardwareMap);
    }
}