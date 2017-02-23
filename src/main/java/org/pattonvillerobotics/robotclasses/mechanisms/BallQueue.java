package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by bahrg on 2/23/17.
 */

public class BallQueue extends AbstractMechanism {

    private Servo ballQueue;

    public BallQueue(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);

        ballQueue = hardwareMap.servo.get("ball_queue");
    }

    public BallQueue setBallQueueOut() {
        ballQueue.setPosition(1);
        return this;
    }

    public BallQueue setBallQueueIn() {
        ballQueue.setPosition(0);
        return this;
    }
}
