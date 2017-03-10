package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.enums.EndPosition;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.robotclasses.drive.TestEncoderDrive;
import org.pattonvillerobotics.robotclasses.mechanisms.Hopper;

/**
 * Created by pieperm on 3/1/17.
 */
@Autonomous(name = "TwoParticlesOneBeaconCapballRed", group = OpModeGroups.MAIN)
public class TwoParticlesOneBeaconCapballRed extends LinearOpMode {

    private AutoMethods autoMethods;
    private TestEncoderDrive drive;
    private Hopper hopper;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();
        waitForStart();

        drive.moveInches(Direction.BACKWARD, Globals.PARTICLE_LAUNCH_DISTANCE, Globals.LOW_MOTOR_POWER);
        autoMethods.fireParticles();
        autoMethods.driveToNearBeacon();
        autoMethods.turnCorrection();
        autoMethods.pressBeacon();
        hopper.setDirection(Hopper.Direction.OUT);
        hopper.activate();
        drive.moveInches(Direction.FORWARD, Globals.NEAR_BEACON_TO_CENTER, Globals.MAX_MOTOR_POWER);
        hopper.deactivate();


        while(opModeIsActive()) {
            telemetry.update();
            idle();
        }

    }

    public void initialize() {
        drive = new TestEncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        autoMethods = new AutoMethods(drive, AllianceColor.RED, EndPosition.CENTER_VORTEX, hardwareMap, this);
        hopper = autoMethods.getHopper();
    }

}
