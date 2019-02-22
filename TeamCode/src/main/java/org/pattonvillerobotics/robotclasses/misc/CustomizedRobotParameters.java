package org.pattonvillerobotics.robotclasses.misc;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.util.PhoneOrientation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaParameters;

public class CustomizedRobotParameters {


//    public static final RobotParameters ROBOT_PARAMETERS;
//    public static final VuforiaParameters VUFORIA_PARAMETERS;
//    public static final PhoneOrientation PHONE_ORIENTATION;

    public static RobotParameters setParams() {
        return new RobotParameters.Builder()
                .rightDriveMotorDirection(DcMotorSimple.Direction.REVERSE)
                .leftDriveMotorDirection(DcMotorSimple.Direction.FORWARD)
                .wheelBaseRadius(11.25)
                .encodersEnabled(true)
                .wheelRadius(2.136)
                .gyroEnabled(true)
                .driveGearRatio(1)
                .build();

    }

    public static PhoneOrientation setPhoneParams() {
        return PhoneOrientation.PORTRAIT_INVERSE;
    }

    public static VuforiaParameters setVuforiaParams() {
        return new VuforiaParameters.Builder()
                .phoneLocation(0, 0, 0, AxesOrder.XYX, 90, -90, 0)
                .cameraDirection(VuforiaLocalizer.CameraDirection.BACK)
                //.cameraMonitorViewId(R.id.cameraMonitorViewId)
                .licenseKey("AclLpHb/////AAAAGa41kVT84EtWtYJZW0bIHf9DHg5EHVYWCqExQMx6bbuBtjFeYdvzZLExJiXnT31qDi3WI3QQnOXH8pLZ4cmb39d1w0Oi7aCwy35ODjMvG5qX+e2+3v0l3r1hPpM8P7KPTkRPIl+CGYEBvoNkVbGGjalCW7N9eFDV/T5CN/RQvZjonX/uBPKkEd8ciqK8vWgfy9aPEipAoyr997DDagnMQJ0ajpwKn/SAfaVPA4osBZ5euFf07/3IUnpLEMdMKfoIH6QYLVgwbPuVtUiJWM6flzWaAw5IIhy0XXWwI0nGXrzVjPwZlN3El4Su73ADK36qqOax/pNxD4oYBrlpfYiaFaX0Q+BNro09weXQEoz/Mfgm")
                .build();

    }
}
