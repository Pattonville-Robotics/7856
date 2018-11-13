package org.pattonvillerobotics.commoncode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by developer on 8/6/16.
 */
@TeleOp(name="ServoValueFinder", group = "Testing")
public class ServoValueFinder extends LinearOpMode {

    private final static String SERVO_NAME = "servo";
    private final static String ELBOW_NAME = "elbow";
    private final static double SERVO_DEFAULT_POSITION = 0.5;

    private Servo servo;
    private Servo elbow;

    private void setUp() {

        servo = hardwareMap.servo.get(SERVO_NAME);
        servo.setPosition(SERVO_DEFAULT_POSITION);

        elbow = hardwareMap.servo.get(ELBOW_NAME);
    }

    @Override
    public void runOpMode() throws InterruptedException {

        setUp();
        waitForStart();

        while (opModeIsActive()) {

            double position = servo.getPosition();
            double elbow_position = elbow.getPosition();

            if (gamepad1.a) {
                position += 0.1;
            } else if (gamepad1.b) {
                position -= 0.1;
            } else if (gamepad1.x) {
                position = 0;
            } else if (gamepad1.y) {
                position = 1;
            } else {
                position = SERVO_DEFAULT_POSITION;
            }

            servo.setPosition(position);

            elbow_position += gamepad1.left_stick_x * 0.1;
            elbow.setPosition(elbow_position);

            telemetry.addData("Servo Position", "Position" + position);
            telemetry.update();

            Thread.sleep(250);

        }


    }


}
