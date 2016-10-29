package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.robotclasses.vuforia.VuforiaNav;

/**
 * Created by pieperm on 10/18/16.
 */
@Autonomous(name = "RedLineCBV", group = OpModeGroups.MAIN)
public class RedLineCBV extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();

        VuforiaNav.activate();
        AutoMethods.runProcessCBV();

    }

    public void initialize() {
        VuforiaNav.initializeVuforia(CustomizedRobotParameters.getVuforiaParameters());
        AutoMethods.init(new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS), AllianceColor.RED, StartPosition.LINE, this, hardwareMap);
    }
}
