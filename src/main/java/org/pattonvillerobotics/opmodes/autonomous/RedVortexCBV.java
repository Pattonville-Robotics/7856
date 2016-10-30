package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by pieperm on 10/18/16.
 */
@Autonomous(name = "RedVortexCBV", group = OpModeGroups.MAIN)
public class RedVortexCBV extends LinearOpMode {

    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();

        AutoMethods.runProcessCBV();

    }

    public void initialize() {
        AutoMethods.init(new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS), AllianceColor.RED, StartPosition.VORTEX, hardwareMap);
    }
}
