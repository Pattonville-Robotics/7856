package org.pattonvillerobotics.robotclass;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.util.PhoneOrientation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaParameters;

public class CustomizedRobotParameters {

    public static final RobotParameters ROBOT_PARAMETERS;
    public static final PhoneOrientation PHONE_ORIENTATION;

    static {
        PHONE_ORIENTATION = PhoneOrientation.PORTRAIT_INVERSE;

        ROBOT_PARAMETERS = new RobotParameters.Builder()
                .wheelBaseRadius(15)
                .encodersEnabled(true)
                .wheelRadius(2)
                .gyroEnabled(true)
                .driveGearRatio(2)
                .build();
    }

}
