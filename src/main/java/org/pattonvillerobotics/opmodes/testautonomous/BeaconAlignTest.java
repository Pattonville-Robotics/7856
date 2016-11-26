package org.pattonvillerobotics.opmodes.testautonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.opmodes.autonomous.AutoMethods;
import org.pattonvillerobotics.opmodes.autonomous.EndPosition;
import org.pattonvillerobotics.opmodes.autonomous.StartPosition;

/**
 * Created by bahrg on 11/1/16.
 */
@Autonomous(name="Alignment TEST")
public class BeaconAlignTest extends LinearOpMode {
    private AutoMethods autoMethods;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        autoMethods.alignToBeacon();
        while(opModeIsActive()){
            idle();
        }
    }

    private void initialize() {
        autoMethods = new AutoMethods(new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS), AllianceColor.BLUE, StartPosition.LINE, EndPosition.CENTER_VORTEX, hardwareMap, this);
    }
}
