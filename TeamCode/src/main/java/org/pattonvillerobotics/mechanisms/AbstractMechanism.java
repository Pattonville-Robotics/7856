package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by pieperm on 10/24/17.
 */

public abstract class AbstractMechanism {

    protected final HardwareMap hardwareMap;
    protected final LinearOpMode linearOpMode;

    public AbstractMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;
    }

}
