package org.pattonvillerobotics;

import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

/**
 * Created by pieperm on 10/10/17.
 */

public class CustomRobotParameters {

    public static RobotParameters ROBOT_PARAMETERS;

    // TODO: Find wheelBaseRadius

    static {

        ROBOT_PARAMETERS = new RobotParameters.Builder()
                .driveGearRatio(2)
                .encodersEnabled(true)
                .wheelBaseRadius(10)
                .wheelRadius(2.5)
                .build();

    }

}
