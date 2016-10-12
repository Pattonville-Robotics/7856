package org.pattonvillerobotics.opmodes.testautonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.robotclasses.vuforia.VuforiaNav;

/**
 * Created by greg on 10/2/2016.
 */
@Autonomous(name="VuforiaTest", group= OpModeGroups.TESTING)
public class VuforiaTesting extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        OpenGLMatrix lastUpdatedLocation;

        initialize();
        waitForStart();
        VuforiaNav.activate();

        while (opModeIsActive()) {
            lastUpdatedLocation = VuforiaNav.getNearestBeaconLocation();
            if(lastUpdatedLocation!=null) {
                telemetry.addData("Distance", VuforiaNav.getDistance(lastUpdatedLocation));
                telemetry.addData("x Position", VuforiaNav.getxPos(lastUpdatedLocation));
            }



            telemetry.update();
            idle();
            sleep(100);
        }
    }

    public void initialize() {
        VuforiaNav.initializeVuforia(CustomizedRobotParameters.VUFORIA_PARAMETERS);
    }
}
