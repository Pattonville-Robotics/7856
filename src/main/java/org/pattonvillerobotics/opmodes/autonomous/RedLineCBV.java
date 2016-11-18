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
@Autonomous(name = "RedLineCBV", group = OpModeGroups.MAIN)
public class RedLineCBV extends LinearOpMode {

    private AutoMethods autoMethods;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();

        autoMethods.runProcessCBV();

        while(opModeIsActive()) {
            telemetry.update();
            idle();
        }

    }

    public void initialize() {
        autoMethods = new AutoMethods(new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS), AllianceColor.RED, StartPosition.LINE, hardwareMap, this);
    }
}
