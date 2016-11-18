package org.pattonvillerobotics.opmodes.testautonomous;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.colordetection.BeaconColorDetection;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav;
import org.pattonvillerobotics.commoncode.vision.util.ScreenOrientation;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by bahrg on 11/3/16.
 */
@Autonomous(name="Beacon Color Test", group= OpModeGroups.TESTING)
public class BeaconColorTesting extends LinearOpMode {

    private BeaconColorDetection beaconColorDetection;
    private VuforiaNav vuforiaNav;

    @Override
    public void runOpMode() throws InterruptedException {
        vuforiaNav = new VuforiaNav(CustomizedRobotParameters.VUFORIA_PARAMETERS);
        beaconColorDetection = new BeaconColorDetection(hardwareMap);
        Bitmap bm;
        waitForStart();
        vuforiaNav.activate();
        while(opModeIsActive()) {
            bm = vuforiaNav.getImage();
            if(bm != null) {
                beaconColorDetection.analyzeFrame(bm, ScreenOrientation.LANDSCAPE_REVERSE);
                telemetry.addData("Colors", beaconColorDetection.getLeftColor());
            }

            telemetry.update();
            idle();
        }
    }
}
