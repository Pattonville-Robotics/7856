package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;

@Autonomous(name = "ExampleAutonomous", group = "Examples")
public class ExampleAutonomous extends LinearOpMode {

    public SimpleDrive drive;

    @Override
    public void runOpMode() {

        initialize();

        waitForStart();

        //Here is where you run methods for the robot to do things

    }

    public void initialize() {
        drive = new SimpleDrive(this, hardwareMap);
    }
}
