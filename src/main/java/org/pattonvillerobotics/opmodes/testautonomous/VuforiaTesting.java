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
    VuforiaNav vuforiaNav;

    @Override
    public void runOpMode() throws InterruptedException {

        OpenGLMatrix lastUpdatedLocation = vuforiaNav.getNearestBeaconLocation();
        Bitmap bm;

        initialize();
        waitForStart();
        vuforiaNav.activate();

        while (opModeIsActive()) {
            if (lastUpdatedLocation != null) {
                telemetry.addData("Distance", vuforiaNav.getDistance());
                telemetry.addData("x Position", vuforiaNav.getxPos());
                telemetry.addData("Angle To Turn", vuforiaNav.getAngle());
            } else {
                telemetry.addData("Position", "Unknown");
            }

            bm = vuforiaNav.getImage();
            if(bm != null) {
                telemetry.addData("Imaging", "Grabbing Frames");
                bm.recycle();
            }

            telemetry.update();
            idle();
            sleep(50);
        }
    }

    public void initialize() {
        colorDetection = new BeaconColorDetection(hardwareMap);
        vuforiaNav = new VuforiaNav(CustomizedRobotParameters.VUFORIA_PARAMETERS);
        telemetry.addData("Initialization", "Complete");
        telemetry.update();
    }
}
