package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

@TeleOp(name = "teleopGrey", group = OpModeGroups.TESTING)
public class teleopGrey extends LinearOpMode {

    public SimpleDrive drive;
    public ListenableGamepad gamepad;

    @Override
    public void runOpMode () throws InterruptedException {

        gamepad1 = new ListenableGamepad();
        gamepad2 = new ListenableGamepad();
        //etcetera

        initialize();

        waitForStart();

        while (opModeIsActive()) {

            gamepad.update(new GamepadD2ata(gamepad1));
            drive.moveFreely(-gamepad1.left_stick_x, gamepad1.left_stick_y);

        }
    }
    public void initialize() {
        gamepad = new ListenableGamepad();
        drive = new SimpleDrive(this, hardwareMap)
    }
}
