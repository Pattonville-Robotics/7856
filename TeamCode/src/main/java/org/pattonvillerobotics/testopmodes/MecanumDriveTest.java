package org.pattonvillerobotics.testopmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;

/**
 * Created by martint08 on 11/7/17.
 */
@Autonomous (name = "mecanum-drive-test")
public class MecanumDriveTest extends LinearOpMode {
    private MecanumEncoderDrive mecanumEncoderDrive;


    @Override
    public void runOpMode() throws InterruptedException {

        MecanumEncoderDrive drive = new MecanumEncoderDrive(hardwareMap, this, CustomRobotParameters.ROBOT_PARAMETERS);

        waitForStart();

        while(opModeIsActive()) {
            drive.moveInches(Direction.RIGHT, 25, .5);
            sleep(1000);
            drive.rotateDegrees(Direction.LEFT, 360, .5);
            sleep(500);
            stop();
        }



    }
}
