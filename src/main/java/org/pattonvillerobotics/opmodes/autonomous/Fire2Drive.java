package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.opmodes.teleop.MainTeleOp;
import org.pattonvillerobotics.robotclasses.mechanisms.Hopper;

/**
 * Created by bahrg on 11/19/16.
 */
@Autonomous(name="Fire2Drive", group= OpModeGroups.MAIN)
public class Fire2Drive extends LinearOpMode {

    private EncoderDrive drive;
    private AutoMethods autoMethods;
    private Hopper hopper;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        autoMethods = new AutoMethods(drive, AllianceColor.BLUE, StartPosition.LINE, EndPosition.CENTER_VORTEX, hardwareMap, this);
        hopper = new Hopper(hardwareMap, this);
        waitForStart();

        autoMethods.fireCannon();
        sleep(1000);
        hopper.update(true, MainTeleOp.Direction.IN);
        sleep(7000);
        hopper.update(false, MainTeleOp.Direction.IN);
        autoMethods.fireCannon();

        drive.moveInches(Direction.BACKWARD, 67, Globals.MAX_MOTOR_POWER);

    }
}
