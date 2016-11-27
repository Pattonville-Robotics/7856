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
@Deprecated
@Autonomous(name = "RedVortex", group = OpModeGroups.MAIN)
public class RedVortex extends LinearOpMode {

    private AutoMethods autoMethods;

    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();

        autoMethods.runAutonomousProcess();

        while(opModeIsActive()) {
            telemetry.update();
            idle();
        }

    }

    public void initialize() {
        autoMethods = new AutoMethods(new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS), AllianceColor.RED, EndPosition.CENTER_VORTEX, hardwareMap, this);
    }
}
