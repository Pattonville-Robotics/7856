package org.pattonvillerobotics.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.pattonvillerobotics.mechanisms.REVGyro;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

/**
 * Created by pieperm on 10/12/17.
 */
@TeleOp(name = "GyroTester", group = OpModeGroups.TESTING)
public class GyroTester extends LinearOpMode {

    private REVGyro gyro;
    private ListenableGamepad gamepad;
    private SimpleMecanumDrive mecanumDrive;

    @Override
    public void runOpMode() throws InterruptedException {

        initialize();

        waitForStart();

        while (opModeIsActive()) {

            gamepad.update(new GamepadData(gamepad1));

            gyro.getGyroTelemetry(telemetry);

            idle();

        }

    }

    private void initialize() {

        gyro = new REVGyro(hardwareMap);
        gamepad = new ListenableGamepad();
        try {
            mecanumDrive = new SimpleMecanumDrive(this, hardwareMap);
        } catch (IllegalArgumentException e) {
            telemetry.addData("Gyro", "Cannot balance robot! Check configs for mecanum motors");
        }


        gamepad.getButton(GamepadData.Button.X).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                telemetry.addData("Gyro", "Attempting to balance robot").setRetained(true);
                telemetry.update();
                //gyro.balanceRobot(mecanumDrive);
            }
        });

    }

}
