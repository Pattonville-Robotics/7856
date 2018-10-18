package org.pattonvillerobotics.opmodes.autonomous.robotclasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.util.PhoneOrientation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaParameters;

public class CustomizedParameters extends LinearOpMode {
    public static final RobotParameters ROBOT_PARAMETERS;
    //public static final VuforiaParameters VUFORIA_PARAMETERS;
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

//        VUFORIA_PARAMETERS = new VuforiaParameters.Builder()
//                .phoneLocation(0, 0,  0, AxesOrder.XYX, 90, -90, 0)
//                .cameraDirection(VuforiaLocalizer.CameraDirection.BACK)
//                .cameraMonitorViewId(R.id.ca);
    }

    @Override
    public void runOpMode() throws InterruptedException {

    }
}
