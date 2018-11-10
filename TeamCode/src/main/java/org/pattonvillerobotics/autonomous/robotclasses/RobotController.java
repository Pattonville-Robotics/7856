package org.pattonvillerobotics.robotclass;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.util.PhoneOrientation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaParameters;
import org.pattonvillerobotics.commoncode.robotclasses .drive.RobotParameters.;

public class SammyRobotcontroller {
    public static RobotParameters ROBOT_PARAMETERS;
    public static VuforiaParameters Vuforia_PARAMETERS;
    public static PhoneOrientation Phone_orientation;

    static {
        Phone_orientation = PhoneOrientation.PORTRAIT_INVERSE;

        ROBOT_PARAMETERS = new RobotParameters.Builder()
                .wheelBaseRadius(15)
                .encodersEnabled(true)
                .wheelRadius(2)
                .gyroEnabled(true)
                .driveGearRatio(2)
                .build();

        Vuforia_PARAMETERS = new Vuforia_PARAMETERS.build()
                .phoneLocation();
    }