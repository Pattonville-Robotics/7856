package org.pattonvillerobotics.teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;

/**
 * Created by pieperm on 2/1/18.
 */
@TeleOp(name = "ActiveIntakeTest", group = OpModeGroups.TESTING)
public class ActiveIntakeTest extends LinearOpMode {

    private CRServo leftServo, rightServo;
    private ListenableGamepad gamepad;

    @Override
    public void runOpMode() throws InterruptedException {

        leftServo = hardwareMap.crservo.get("left-servo");
        rightServo = hardwareMap.crservo.get("right-servo");
        gamepad = new ListenableGamepad();

        waitForStart();

        while (opModeIsActive()) {

            gamepad.update(new GamepadData(gamepad1));

            if (gamepad1.a) {
                leftServo.setPower(0.5);
                rightServo.setPower(0.5);
            } else if (gamepad1.b) {
                leftServo.setPower(0.5);
                rightServo.setPower(0.5);
            } else {
                leftServo.setPower(0);
                rightServo.setPower(0);
            }


        }


    }


}
