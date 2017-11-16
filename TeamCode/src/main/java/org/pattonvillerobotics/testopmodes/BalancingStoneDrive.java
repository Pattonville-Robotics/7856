package org.pattonvillerobotics.testopmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.autonomous.AutoMethods;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;

/**
 * Created by pieperm on 11/16/17.
 */
@Autonomous(name = "BalancingStoneDrive", group = OpModeGroups.TESTING)
@Disabled
public class BalancingStoneDrive extends LinearOpMode {

    private AutoMethods autoMethods;

    @Override
    public void runOpMode() throws InterruptedException {

         autoMethods = new AutoMethods(hardwareMap, this, AllianceColor.BLUE);

        waitForStart();

        autoMethods.driveOffBalancingStone();

    }
}
