package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;

/**
 * Created by pieperm on 11/16/17.
 */

public class AltRedAutonomous extends LinearOpMode {

    public static final String TAG = AltRedAutonomous.class.getSimpleName();
    private AutoMethods autoMethods;

    @Override
    public void runOpMode() throws InterruptedException {

        autoMethods = new AutoMethods(hardwareMap, this, AllianceColor.RED);

        waitForStart();

        autoMethods.pickUpGlyph();
        sleep(500);
        autoMethods.getMecanumEncoderDrive().moveInches(Direction.BACKWARD, Globals.ALT_DISTANCE_TO_CRYPTOBOX, 0.5);
        sleep(500);
        autoMethods.placeGlyph();

    }
}
