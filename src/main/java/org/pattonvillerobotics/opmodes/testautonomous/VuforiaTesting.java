package org.pattonvillerobotics.opmodes.testautonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;

import org.pattonvillerobotics.robotclasses.VuforiaNav;

/**
 * Created by greg on 10/2/2016.
 */
@Autonomous(name="VuforiaTest", group= OpModeGroups.TESTING)
public class VuforiaTesting extends LinearOpMode {

    private VuforiaNav vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
        vuforia.activate();

        while (opModeIsActive()) {
            for(VuforiaTrackable beacon : vuforia.getBeacons()) {
                telemetry.addData("Loc:", vuforia.getTranslation(beacon));
            }

            telemetry.update();
            idle();
        }
    }

    public void initialize() {
        vuforia = new VuforiaNav();
    }
}
