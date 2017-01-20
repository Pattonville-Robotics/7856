package org.pattonvillerobotics.opmodes.testautonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.enums.EndPosition;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.opmodes.autonomous.AutoMethods;

/**
 * Created by bahrg on 11/26/16.
 */
@Autonomous(name="DetectColor Test Beacon", group= OpModeGroups.TESTING)
public class DetectColorTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        AutoMethods autoMethods = new AutoMethods(new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS), AllianceColor.BLUE, EndPosition.CENTER_VORTEX, hardwareMap, this);
        waitForStart();

        autoMethods.detectBeaconColor();

        while(opModeIsActive()) {
            idle();
        }
    }
}
