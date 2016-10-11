package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;

import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by bahrg on 10/1/16.
 */


public class BlueLineCBV extends LinearOpMode {

    private EncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
    }

    public void initialize() {
        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
    }
}