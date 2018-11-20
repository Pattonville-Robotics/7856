package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.robotclasses.AbstractLinearSlideStackMechanism;

/**
 * Created by wrightk03 on 11/19/18.
 * <p>
 * Purpouse:
 */
public class LinearSlideStackMechanism extends AbstractLinearSlideStackMechanism {

    public LinearSlideStackMechanism(int extension_distance, HardwareMap hardwareMap, LinearOpMode linearOpMode, RobotParameters parameters, boolean useBothWinches) {
        super(extension_distance, hardwareMap, linearOpMode, parameters, useBothWinches);
    }
}
