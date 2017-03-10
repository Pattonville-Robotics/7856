package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by bahrg on 2/23/17.
 */

public class ParticleQueue extends AbstractMechanism {

    private Servo particleQueue;

    public ParticleQueue(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);

        particleQueue = hardwareMap.servo.get("ball_queue");
    }

    public ParticleQueue setParticleQueueOut() {
        particleQueue.setPosition(0);
        return this;
    }

    public ParticleQueue setParticleQueueIn() {
        particleQueue.setPosition(1);
        return this;
    }
}
