package org.pattonvillerobotics.opmodes.testautonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.apache.commons.math3.util.FastMath;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaNav;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.opmodes.autonomous.Globals;

/**
 * Created by bahrg on 11/1/16.
 */
@Autonomous(name="Alignment TEST NUMS")
public class BeaconAlignTest extends LinearOpMode {
    private VuforiaNav vuforia;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        double x = vuforia.getxPos();
        double y = vuforia.getDistance();
        double Q = Globals.MINIMUM_DISTANCE_TO_BEACON;
        double d = Math.sqrt(Math.pow(x, 2) + Math.pow((y - Q), 2));
        double angleToTurn = FastMath.toDegrees(FastMath.atan(y/x) - FastMath.atan((y-Q)/x) );
        double adjustmentAngle = FastMath.toDegrees(FastMath.asin(x/d));

        telemetry.addData("Angle to Turn", angleToTurn);
        telemetry.addData("Adjustment Angle", adjustmentAngle);
        telemetry.addData("d", d);
        telemetry.addData("x", x);
        telemetry.addData("y", y);

        //autoMethods.alignToBeacon();
        telemetry.update();
        while(opModeIsActive()){
            idle();
        }
    }

    private void initialize() {
        vuforia = new VuforiaNav(CustomizedRobotParameters.VUFORIA_PARAMETERS);
        vuforia.activate();
    }
}
