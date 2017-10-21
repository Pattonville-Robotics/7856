package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;

/**
 * Created by pieperm on 10/21/17.
 */

@Autonomous(name = "VuforiaTest", group = OpModeGroups.TESTING)
public class VuforiaTest extends LinearOpMode {

    private VuforiaNavigation vuforia;

    @Override
    public void runOpMode() throws InterruptedException {

        vuforia = new VuforiaNavigation(CustomRobotParameters.VUFORIA_PARAMETERS);
        vuforia.activateTracking();

        waitForStart();

        while(opModeIsActive()) {

            telemetry.addData("Relic", vuforia.getCurrentVisibleRelic());
            telemetry.update();
            idle();

        }

    }
}
