package org.pattonvillerobotics.opmodes.testautonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.colordetection.BeaconColorDetection;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;

@Autonomous(name = "VuforiaTest", group = OpModeGroups.TESTING)
public class VuforiaTesting extends LinearOpMode {

    private VuforiaNav vuforia;
    private BeaconColorDetection beaconColorDetection;

    @Override
    public void runOpMode() throws InterruptedException {

        OpenGLMatrix lastUpdatedLocation;

        initialize();
        waitForStart();
        vuforia.activate();

        while (opModeIsActive()) {
            lastUpdatedLocation = vuforia.getNearestBeaconLocation();
            if (lastUpdatedLocation != null) {
                telemetry.addData("X Offset", vuforia.getXPos());
                telemetry.addData("Y Offset", vuforia.getYPos());
                telemetry.addData("Orientation", vuforia.getOrientation());
                telemetry.addData("Correction Angle", FastMath.toDegrees(FastMath.atan(vuforia.getYPos()/vuforia.getXPos())));
            } else {
                telemetry.addData("Position", "Unknown");
            }

            telemetry.update();
            idle();
            sleep(50);
        }
    }

    public void initialize() {
        vuforia = new VuforiaNav(CustomizedRobotParameters.VUFORIA_PARAMETERS);
        beaconColorDetection = new BeaconColorDetection(hardwareMap);
        telemetry.addData("Initialization", "Complete");
        telemetry.update();
    }
}
