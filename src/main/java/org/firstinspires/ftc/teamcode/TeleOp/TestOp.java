package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robotclasses.Conveyor;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.Drive;

/**
 * Created by pieperm on 9/22/16.
 */
@TeleOp(name = "Candy Bot TeleOp", group = OpModeGroups.TESTING)
public class TestOp extends LinearOpMode {

    private Drive drive;
    private Conveyor conveyor;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        while (opModeIsActive()) {
            doLoop();
            idle();
        }
    }

    public void initialize() {
        drive = new Drive(hardwareMap, this);
        conveyor = new Conveyor(hardwareMap);
    }

    public void doLoop() {

        if (gamepad1.a) {
            conveyor.run();
            telemetry.addData("TeleOp", "Conveyor started");
        } else if (gamepad1.b) {
            conveyor.stop();
            telemetry.addData("TeleOp", "Conveyor stopped");
        }

    }

}
