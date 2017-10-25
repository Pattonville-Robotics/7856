package org.pattonvillerobotics.testopmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.mechanisms.Glyphter;

/**
 * Created by pieperm on 10/23/17.
 */
@TeleOp(name = "GlyphterTester", group = OpModeGroups.DEBUG)
public class GlyphterTester extends LinearOpMode {

    private Glyphter glyphter;

    @Override
    public void runOpMode() throws InterruptedException {

        glyphter = new Glyphter(hardwareMap, this);

        waitForStart();

        while(opModeIsActive()) {

            glyphter.getMotor().setPower(gamepad1.right_stick_y);

            idle();

        }

    }
}
