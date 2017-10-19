package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;

/**
 * Created by pieperm on 10/18/17.
 */
@Autonomous(name = "RedAutonomous", group = OpModeGroups.MAIN)
public class RedAutonomous extends LinearOpMode {

    private AutoMethods autoMethods;

    @Override
    public void runOpMode() throws InterruptedException {

        autoMethods = new AutoMethods(hardwareMap, this, AllianceColor.RED);
        autoMethods.initialize();

        waitForStart();

        while (opModeIsActive()) {
            autoMethods.runAutonomousProcess();
            idle();
        }

    }
}
