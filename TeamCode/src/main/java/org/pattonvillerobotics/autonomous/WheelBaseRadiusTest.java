package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;

/**
 * Created by pieperm on 1/30/18.
 */
@Autonomous(name = "WheelBaseRadiusTesting", group = OpModeGroups.TESTING)
public class WheelBaseRadiusTest extends LinearOpMode {

    private MecanumEncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        drive = new MecanumEncoderDrive(hardwareMap, this, CustomRobotParameters.ROBOT_PARAMETERS);

        drive.rotateDegrees(Direction.LEFT, 360, 0.5);

    }

}
