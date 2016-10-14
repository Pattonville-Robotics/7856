package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;

/**
 * Created by bahrg on 10/1/16.
 */


public class BlueLineCBV extends LinearOpMode {

    private EncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        waitForStart();

        AutoMethods.setAllianceColor(AllianceColor.BLUE);
        AutoMethods.driveToCapball();
        AutoMethods.driveToBeacon();
        AutoMethods.alignToBeacon();
        AutoMethods.driveToNextBeacon();
        AutoMethods.alignToBeacon();
        AutoMethods.driveToCornerVortex();
        AutoMethods.climbCornerVortex();

    }
}
