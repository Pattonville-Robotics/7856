package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by skaggsw on 10/27/16.
 */

public abstract class AbstractMechanism {

    protected final HardwareMap hardwareMap;

    public AbstractMechanism(HardwareMap hardwareMap) {
        this.hardwareMap = hardwareMap;
    }
}
