package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by bahrg on 10/1/16.
 */


public class BlueLineCBV extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();

        AutoMethods.runProcessCBV();

    }

    public void initialize() {
        AutoMethods.init(new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS), AllianceColor.BLUE, StartPosition.LINE);
    }
}
