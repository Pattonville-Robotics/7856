package org.pattonvillerobotics.opmodes;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.R;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.drive.trailblazer.vuforia.VuforiaParameters;

/**
 * Created by bahrg on 10/4/16.
 */

public class CustomizedRobotParameters {
    public static final RobotParameters ROBOT_PARAMETERS;
    public static final VuforiaParameters VUFORIA_PARAMETERS;

    static {
        ROBOT_PARAMETERS = new RobotParameters.Builder()
                .encodersEnabled(true)
                .gyroEnabled(false)
                .wheelRadius(2)
                .wheelBaseRadius(8.7)
                .driveGearRatio(3)
                .build();

        VUFORIA_PARAMETERS = new VuforiaParameters.Builder()
                .licenseKey("AclLpHb/////AAAAGa41kVT84EtWtYJZW0bIHf9DHg5EHVYWCqExQMx6bbuBtjFeYdvzZLExJiXnT31qDi3WI3QQnOXH8pLZ4cmb39d1w0Oi7aCwy35ODjMvG5qX+e2+3v0l3r1hPpM8P7KPTkRPIl+CGYEBvoNkVbGGjalCW7N9eFDV/T5CN/RQvZjonX/uBPKkEd8ciqK8vWgfy9aPEipAoyr997DDagnMQJ0ajpwKn/SAfaVPA4osBZ5euFf07/3IUnpLEMdMKfoIH6QYLVgwbPuVtUiJWM6flzWaAw5IIhy0XXWwI0nGXrzVjPwZlN3El4Su73ADK36qqOax/pNxD4oYBrlpfYiaFaX0Q+BNro09weXQEoz/Mfgm")
                .cameraDirection(VuforiaLocalizer.CameraDirection.BACK)
                .phoneLocation(-76.2f, 1.8f, 0, AxesOrder.YZY, -90, 0, 0)
                .addBeaconLocation(0, 0, 150, AxesOrder.XZX, 90, 90, 0) // blue 1
                .addBeaconLocation(0, 0, 150, AxesOrder.XZX, 90, 90, 0) // red 2
                .addBeaconLocation(0, 0, 150, AxesOrder.XZX, 90, 90, 0) // blue 2
                .addBeaconLocation(0, 0, 150, AxesOrder.XZX, 90, 90, 0) // red 1
                .cameraMonitorViewId(R.id.cameraMonitorViewId)
                .build();
    }
}
