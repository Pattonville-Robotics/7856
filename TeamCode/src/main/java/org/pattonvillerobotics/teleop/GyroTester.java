package org.pattonvillerobotics.teleop;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.autonomous.AutoMethods;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;

/**
 * Created by pieperm on 10/12/17.
 */
@TeleOp(name = "GyroTester", group = OpModeGroups.TESTING)
@Disabled
public class GyroTester extends LinearOpMode {

    private AutoMethods autoMethods;

    @Override
    public void runOpMode() throws InterruptedException {

        autoMethods = new AutoMethods(hardwareMap, this, AllianceColor.RED);

        waitForStart();

        while (opModeIsActive()) {

            idle();

        }

    }

}
