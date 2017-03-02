package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.enums.EndPosition;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.robotclasses.drive.TestEncoderDrive;

/**
 * Created by pieperm on 3/1/17.
 */
@Autonomous(name = "TwoParticlesNearBeaconCornerBlue", group = OpModeGroups.MAIN)
public class OneParticleOneBeaconCornerRed extends LinearOpMode {

    private AutoMethods autoMethods;
    private TestEncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();

        drive.moveInches(Direction.BACKWARD, Globals.PARTICLE_LAUNCH_DISTANCE, Globals.LOW_MOTOR_POWER);
        autoMethods.fireCannon();
        autoMethods.driveToNearBeacon();
        autoMethods.turnCorrection();
        autoMethods.pressBeacon();
        drive.rotateDegrees(autoMethods.getOppositeTurnDirection(), Globals.RIGHT_TURN, Globals.TURNING_SPEED);
        drive.moveInches(Direction.BACKWARD, Globals.NEAR_BEACON_TO_CORNER, Globals.LOW_MOTOR_POWER);


        while(opModeIsActive()) {
            telemetry.update();
            idle();
        }

    }

    public void initialize() {
        drive = new TestEncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        autoMethods = new AutoMethods(drive, AllianceColor.RED, EndPosition.CENTER_VORTEX, hardwareMap, this);
    }

}
