package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.enums.EndPosition;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.robotclasses.drive.TestEncoderDrive;

/**
 * Created by bahrg on 1/26/17.
 */

@Autonomous(name = "FireAutonomous")
public class FireAutonomous extends LinearOpMode {
    private AutoMethods autoMethods;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();

        autoMethods.fireParticles();

        while(opModeIsActive()) {
            telemetry.update();
            idle();
        }

    }

    public void initialize() {
        autoMethods = new AutoMethods(
                new TestEncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS),
                AllianceColor.BLUE,
                EndPosition.CENTER_VORTEX,
                hardwareMap,
                this
        );
    }
}
