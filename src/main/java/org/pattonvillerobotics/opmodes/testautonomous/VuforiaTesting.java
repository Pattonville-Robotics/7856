package org.pattonvillerobotics.opmodes.testautonomous;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.robotclasses.colordetection.BeaconColorDetection;
import org.pattonvillerobotics.robotclasses.vuforia.VuforiaNav;

@Autonomous(name = "VuforiaTest", group = OpModeGroups.TESTING)
public class VuforiaTesting extends LinearOpMode {

    BeaconColorDetection colorDetection;

    @Override
    public void runOpMode() throws InterruptedException {

        OpenGLMatrix lastUpdatedLocation;
        colorDetection = new BeaconColorDetection(hardwareMap);
        Bitmap bm;

        initialize();
        telemetry.update();
        waitForStart();
        VuforiaNav.activate();

        while (opModeIsActive()) {
            lastUpdatedLocation = VuforiaNav.getNearestBeaconLocation();
            if (lastUpdatedLocation != null) {
                telemetry.addData("Distance", VuforiaNav.getDistance(lastUpdatedLocation));
                telemetry.addData("x Position", VuforiaNav.getxPos(lastUpdatedLocation));
            } else {
                telemetry.addData("Position", "Unknown");
            }

            bm = VuforiaNav.getImage();

            // do color stuff PLEASE
            if (bm != null) {
                telemetry.addData("Detecting an image.", "");
                telemetry.addData("Colors", colorDetection.analyzeFrame(bm).getColorString());
                bm.recycle();
            }

            telemetry.update();
            idle();
            sleep(100);
        }
        if (isStopRequested()) {
            VuforiaNav.deactivate();
        }
    }

    public void initialize() {
        VuforiaNav.initializeVuforia(CustomizedRobotParameters.getVuforiaParameters());
        colorDetection.setColorToleranceBlue(0);
        colorDetection.setColorToleranceRed(0);
        telemetry.addData("Initialization", "Complete");
    }
}
