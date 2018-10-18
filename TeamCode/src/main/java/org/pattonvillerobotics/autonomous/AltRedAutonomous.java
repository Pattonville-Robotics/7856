package org.pattonvillerobotics.opmodes.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;

/**
 * Created by pieperm on 11/16/17.
 */
@Autonomous(name = "AltRedAutonomous", group = OpModeGroups.DEBUG)
public class AltRedAutonomous extends LinearOpMode {

    public static final String TAG = AltRedAutonomous.class.getSimpleName();
    //private AutoMethods autoMethods;

    @Override
    public void runOpMode() throws InterruptedException {

//        autoMethods = new AutoMethods(hardwareMap, this, AllianceColor.RED);
//
//        waitForStart();
//
//        autoMethods.pickUpGlyph();
//        autoMethods.readVuforiaValues();
//        autoMethods.knockOffJewel();
//
//        sleep(500);
//        autoMethods.getMecanumEncoderDrive().moveInches(Direction.BACKWARD, 20, 0.5);

  sleep(500);
      //autoMethods.placeGlyph();

    }
}
