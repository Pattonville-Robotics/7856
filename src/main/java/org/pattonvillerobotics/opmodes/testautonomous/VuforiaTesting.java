package org.pattonvillerobotics.opmodes.testautonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.robotclasses.VuforiaNav;

/**
 * Created by greg on 10/2/2016.
 */
@Autonomous(name="VuforiaTest", group= OpModeGroups.TESTING)
public class VuforiaTesting extends LinearOpMode {

    private VuforiaNav vuforia;
    private OpenGLMatrix lastUpdatedLocation = null;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        vuforia.activate();

        while (opModeIsActive()) {
            lastUpdatedLocation = vuforia.getNearestBeaconLocation();
            if(lastUpdatedLocation!=null) {
                telemetry.addData("Distance", vuforia.getDistance(lastUpdatedLocation));
                telemetry.addData("x Position", vuforia.getxPos(lastUpdatedLocation));
            }



            telemetry.update();
            idle();
            sleep(100);
        }
    }

    public void initialize() {
        vuforia = new VuforiaNav();
    }
}
