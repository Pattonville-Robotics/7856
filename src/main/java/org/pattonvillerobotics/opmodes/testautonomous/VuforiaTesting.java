package org.pattonvillerobotics.opmodes.testautonomous;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.colordetection.BeaconColorDetection;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

@Autonomous(name = "VuforiaTest", group = OpModeGroups.TESTING)
public class VuforiaTesting extends LinearOpMode {

    private VuforiaNav vuforiaNav;
    private BeaconColorDetection beaconColorDetection;

    @Override
    public void runOpMode() throws InterruptedException {

        OpenGLMatrix lastUpdatedLocation;
        Bitmap bm;

        initialize();
        waitForStart();
        vuforiaNav.activate();

        while (opModeIsActive()) {
            lastUpdatedLocation = vuforiaNav.getNearestBeaconLocation();
            if (lastUpdatedLocation != null) {
                telemetry.addData("Distance", vuforiaNav.getDistance());
                telemetry.addData("x Position", vuforiaNav.getxPos());
                telemetry.addData("Angle To Turn", vuforiaNav.getAngle());
            } else {
                telemetry.addData("Position", "Unknown");
            }

            telemetry.update();
            idle();
            sleep(50);
        }
    }

    public void initialize() {
        vuforiaNav = new VuforiaNav(CustomizedRobotParameters.VUFORIA_PARAMETERS);
        beaconColorDetection = new BeaconColorDetection(hardwareMap);
        telemetry.addData("Initialization", "Complete");
        telemetry.update();
    }
}
