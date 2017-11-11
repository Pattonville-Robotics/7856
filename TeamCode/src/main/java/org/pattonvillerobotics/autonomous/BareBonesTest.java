package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;

/**
 * Created by pieperm on 11/9/17.
 */
@TeleOp(name = "BareBonesTest", group = OpModeGroups.TESTING)
public class BareBonesTest extends LinearOpMode {

    private SimpleMecanumDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {

        drive = new SimpleMecanumDrive(this, hardwareMap);
        Vector2D coords;

        waitForStart();

        while(opModeIsActive()) {

            coords = SimpleMecanumDrive.toPolar(gamepad1.left_stick_x, gamepad1.left_stick_y);

//            drive.driveWithJoysticks(gamepad1.left_stick_x, gamepad1.left_stick_y, gamepad1.left_stick_x);
            drive.moveFreely(coords.getY(), coords.getX(), -gamepad1.left_stick_x);

        }

    }
}
