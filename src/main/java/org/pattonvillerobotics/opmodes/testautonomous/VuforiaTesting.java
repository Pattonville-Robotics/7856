package org.pattonvillerobotics.opmodes.testautonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

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
        int count = 0;
        vuforia.activate();

        while (opModeIsActive()) {

        }
    }

    public void initialize() {
        vuforia = new VuforiaNav();
    }
}
