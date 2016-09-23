package org.firstinspires.ftc.teamcode.Autonomi;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by pieperm on 9/22/16.
 */
public class Autonomous extends LinearOpMode {

    Servo testServo;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();
    }

    public void initialize() {
        testServo = hardwareMap.servo.get("name");
    }
}
