package org.pattonvillerobotics;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.commoncode.robotclasses.drive.QuadEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

/**
 * Created by pieperm on 10/2/17.
 */

public class MecanumEncoderDrive extends QuadEncoderDrive {

    public MecanumEncoderDrive(HardwareMap hardwareMap, LinearOpMode linearOpMode, final RobotParameters robotParameters) {
        super(hardwareMap, linearOpMode, robotParameters);

    }

}
