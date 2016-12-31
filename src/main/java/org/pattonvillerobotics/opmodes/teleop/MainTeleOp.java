package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.opmodes.CustomizedRobotParameters;
import org.pattonvillerobotics.opmodes.autonomous.Globals;
import org.pattonvillerobotics.robotclasses.mechanisms.ArmMover;
import org.pattonvillerobotics.robotclasses.mechanisms.Cannon;
import org.pattonvillerobotics.robotclasses.mechanisms.Hopper;

/**
 * Created by skaggsw on 10/4/16.
 */
@TeleOp(name = "MainTeleOp", group = OpModeGroups.MAIN)

public class MainTeleOp extends LinearOpMode {

    private EncoderDrive drive;
    private ListenableGamepad gamepad;
    private ArmMover armMover;
    private boolean cannonOn = false;
    private Hopper hopper;
    private Cannon cannon;
    private boolean hopperOn = false;
    private Direction currentDirection;

    public void runOpMode() throws InterruptedException {
        initialize();
        telemetry.setMsTransmissionInterval(16);
        final Telemetry.Item particle_Launcher = telemetry.addData("Particle Launcher: ", "N/A").setRetained(true);
        waitForStart();

        while (opModeIsActive()) {
            particle_Launcher.setValue(cannon.getCannon().getCurrentPosition());
            doLoop();
            idle();
        }
    }

    public void initialize() {
        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        gamepad = new ListenableGamepad();
        armMover = new ArmMover(hardwareMap, this);
        cannon = new Cannon(hardwareMap, this);
        hopper = new Hopper(hardwareMap, this);
        currentDirection = Direction.IN;

        telemetry.setMsTransmissionInterval(16);
        final Telemetry.Item particle_Launcher = telemetry.addData("Particle Launcher: ", "N/A").setRetained(true);

        gamepad.getButton(GamepadData.Button.X).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                hopperOn = !hopperOn;
            }
        });

        gamepad.getButton(GamepadData.Button.Y).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                switch (currentDirection) {
                    case IN:
                        currentDirection = Direction.OUT;
                        break;
                    case OUT:
                        currentDirection = Direction.IN;
                        break;
                }
            }
        });
        gamepad.getButton(GamepadData.Button.B).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                cannon.primeLauncher();
            }
        });
        gamepad.getButton(GamepadData.Button.A).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {

                cannonOn = !cannonOn;
            }
        });

        gamepad.getButton(GamepadData.Button.LEFT_BUMPER).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                armMover.toggle(armMover.getArmMoverLeft());
            }
        });
        gamepad.getButton(GamepadData.Button.RIGHT_BUMPER).addListener(ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                armMover.toggle(armMover.getArmMoverRight());
            }
        });
    }




    public void doLoop() {
        drive.moveFreely(Range.clip(gamepad1.left_stick_y, 0, Globals.MAX_MOTOR_POWER), Range.clip(gamepad1.right_stick_y, 0, Globals.MAX_MOTOR_POWER));
        gamepad.update(new GamepadData(gamepad1));
        hopper.update(hopperOn, currentDirection);
        cannon.update(cannonOn);
        telemetry.update();
    }

    public enum Direction {IN, OUT}
}