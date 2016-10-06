package pattonvillerobotics.opmodes.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;

import pattonvillerobotics.opmodes.CustomizedRobotParameters;

/**
 * Created by skaggsw on 10/4/16.
 */

public class TeleOp extends LinearOpMode {

    private EncoderDrive drive;
    private ButtonFSM abutton;

    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        while (opModeIsActive()) {
            doLoop();
            idle();
        }
    }

    public void initialize() {
        drive = new EncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        telemetry.addData("Init", "Initialized.");
        abutton = new ButtonFSM();
    }

    public void doLoop() {
        drive.moveFreely(gamepad1.left_stick_y, -gamepad1.right_stick_y);
        abutton.CheckButton(gamepad1.a);
    }
}
