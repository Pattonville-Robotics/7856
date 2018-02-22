package org.pattonvillerobotics.testopmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.mechanisms.GlyphGrabber;

/**
 * Created by pieperm on 10/23/17.
 */
@TeleOp(name = "GlyphGrabberTester", group = OpModeGroups.DEBUG)
public class GlyphGrabberTester extends LinearOpMode {

    private static final String TAG = GlyphGrabberTester.class.getSimpleName();
    private GlyphGrabber glyphGrabber;

    @Override
    public void runOpMode() throws InterruptedException {

        glyphGrabber = new GlyphGrabber(hardwareMap, this, Globals.GrabberState.RELEASED);

        waitForStart();

        telemetry.addData(TAG, "Use the triggers to control the servos");
        telemetry.update();

        while(opModeIsActive()) {

            glyphGrabber.getLeftTopServo().setPosition(gamepad1.left_trigger);
            glyphGrabber.getRightTopServo().setPosition(gamepad1.right_trigger);

            idle();

        }

    }
}
