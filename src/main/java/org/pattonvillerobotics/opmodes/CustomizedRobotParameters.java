package org.pattonvillerobotics.opmodes;

import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

/**
 * Created by bahrg on 10/4/16.
 */

public class CustomizedRobotParameters {
    public static final RobotParameters ROBOT_PARAMETERS;
    public static final RobotParameters TESTROBOT_PARAMETERS;


    static {
        ROBOT_PARAMETERS = new RobotParameters.Builder()
                    .encodersEnabled(true)
                    .gyroEnabled(false)
                    .wheelRadius(2)
                    .wheelBaseRadius(7.5)
                    .driveGearRatio(2)
                    .build();

        TESTROBOT_PARAMETERS = new RobotParameters.Builder()
                .encodersEnabled(true)
                .gyroEnabled(false)
                .driveGearRatio(6)
                .wheelBaseRadius(6.25)
                .wheelRadius(2)
                .build();
    }
}
