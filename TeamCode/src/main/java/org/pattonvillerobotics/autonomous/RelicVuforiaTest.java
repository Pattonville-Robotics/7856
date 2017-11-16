package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;

/**
 * Created by pieperm on 10/21/17.
 */

@Autonomous(name = "RelicVuforiaTest", group = OpModeGroups.TESTING)
@Disabled
public class RelicVuforiaTest extends LinearOpMode {

    private VuforiaNavigation vuforia;

    @Override
    public void runOpMode() throws InterruptedException {

        addTelemetry("init");
        sleep(5000);
        vuforia = new VuforiaNavigation(CustomRobotParameters.VUFORIA_PARAMETERS);
        addTelemetry("vuforia init");
        sleep(5000);
        vuforia.activateTracking();
        addTelemetry("activated tracking");

        waitForStart();

        while(opModeIsActive()) {

            vuforia.getVisibleTrackableLocation();

            telemetry.addData("Relic", vuforia.getCurrentVisibleRelic());
            telemetry.update();
            idle();

        }

    }

    public void addTelemetry(Object value) {
        telemetry.addData("RelicVuforiaTest", value);
        telemetry.update();
    }

}
