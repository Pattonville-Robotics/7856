package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;

/**
 * Created by pieperm on 11/16/17.
 */
@Autonomous(name = "AltBlueAutonomous", group = OpModeGroups.DEBUG)
public class AltBlueAutonomous extends LinearOpMode {

    private AutoMethods autoMethods;

    @Override
    public void runOpMode() throws InterruptedException {

        autoMethods = new AutoMethods(hardwareMap, this, AllianceColor.BLUE);

        waitForStart();

        autoMethods.pickUpGlyph();
        sleep(500);
        autoMethods.getMecanumEncoderDrive().moveInches(Direction.BACKWARD, 14, 0.5);
        autoMethods.getMecanumEncoderDrive().moveInches(Direction.RIGHT, 10, 0.5);
        sleep(500);
        autoMethods.placeGlyph();



    }
}
