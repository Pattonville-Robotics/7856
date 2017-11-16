package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.mechanisms.GlyphGrabber;
import org.pattonvillerobotics.mechanisms.Glyphter;

/**
 * Created by pieperm on 11/16/17.
 */

public class AltBlueAutonomous extends LinearOpMode {

    private AutoMethods autoMethods;

    @Override
    public void runOpMode() throws InterruptedException {

        autoMethods = new AutoMethods(hardwareMap, this, AllianceColor.BLUE);

        waitForStart();

        autoMethods.pickUpGlyph();
        sleep(500);
        autoMethods.getMecanumEncoderDrive().moveInches(Direction.BACKWARD, Globals.ALT_DISTANCE_TO_CRYPTOBOX, 0.5);
        sleep(500);
        autoMethods.placeGlyph();



    }
}
