package org.pattonvillerobotics.opmodes.autonomous.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.opmodes.autonomous.robotclasses.HookLiftingMechanism;

@Autonomous (name = "TestAutonomous", group = OpModeGroups.TESTING)
public class TestAutonomous extends LinearOpMode {
    public SimpleDrive simpleDrive;
    public HookLiftingMechanism hooklifter;


    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        telemetry.addData("hook position", hooklifter);

    }
    public void initialize() {
        simpleDrive = new SimpleDrive(this, hardwareMap);

        hooklifter.lower();

    }

}
