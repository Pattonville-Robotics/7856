package org.pattonvillerobotics.opmodes;

import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

/**
 * Created by bahrg on 10/4/16.
 */

public class CustomizedRobotParameters {
    public static final RobotParameters ROBOT_PARAMETERS;

    static {
        ROBOT_PARAMETERS = new RobotParameters.Builder()
                    .encodersEnabled(true)
                    .gyroEnabled(false)
                    .wheelRadius(2)
                    .wheelBaseRadius(16.13927)
                    .driveGearRatio(2)
                    .build();
    }
}
