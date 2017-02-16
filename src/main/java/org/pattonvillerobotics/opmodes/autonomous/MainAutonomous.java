package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.enums.EndPosition;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by bahrg on 1/19/17.
 */

@Autonomous(name="MainAutonomousBlue")
public class MainAutonomous extends LinearOpMode {

    private AutoMethods autoMethods;

    @Override
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
        autoMethods = new AutoMethods(
                new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS),
                AllianceColor.BLUE,
                EndPosition.CENTER_VORTEX,
                hardwareMap,
                this
        );
    }
}
