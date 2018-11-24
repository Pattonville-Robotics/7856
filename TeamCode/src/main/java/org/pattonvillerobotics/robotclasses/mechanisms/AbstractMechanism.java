package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class AbstractMechanism {

    protected final HardwareMap hardwareMap;
    protected final LinearOpMode LinearOpmode;

    public AbstractMechanism(LinearOpMode linearOpMode, HardwareMap hardwareMap) {
        this.LinearOpmode = linearOpMode;
        this.hardwareMap = hardwareMap;
    }

}