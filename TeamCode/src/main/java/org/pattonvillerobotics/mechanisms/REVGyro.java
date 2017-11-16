package org.pattonvillerobotics.mechanisms;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;

import java.util.Locale;

public class REVGyro extends AbstractMechanism {

    public static final String TAG = REVGyro.class.getSimpleName();
    private BNO055IMU imu;
    private Orientation angles;

    public REVGyro(HardwareMap hardwareMap, LinearOpMode linearOpMode) {

        super(hardwareMap, linearOpMode);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        try {
            imu = hardwareMap.get(BNO055IMU.class, "imu");
            imu.initialize(parameters);
        } catch (IllegalArgumentException e) {
            linearOpMode.telemetry.addData(TAG, e.getMessage());
            linearOpMode.telemetry.update();
        }


        updateAngles();

        startAccelerationIntegration();

    }

    public void setOrientation(AxesReference axesReference, AxesOrder axesOrder) {
        angles = imu.getAngularOrientation(axesReference, axesOrder, AngleUnit.DEGREES);
    }

    public void startAccelerationIntegration() {
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }

    public void updateAngles() {
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES); // Should get the AxesOrder from CustomRobotParameters.ROBOT_PARAMETERS.getRevOrientation()
    }

    public double getHeading() {
        updateAngles();
        return Double.parseDouble(formatAngle(angles.angleUnit, angles.firstAngle));
    }

    public double getRoll() {
        updateAngles();
        return Double.parseDouble(formatAngle(angles.angleUnit, angles.secondAngle));
    }

    public double getPitch() {
        updateAngles();
        return Double.parseDouble(formatAngle(angles.angleUnit, angles.thirdAngle));
    }

    public String getGravity() {
        updateAngles();
        return imu.getGravity().xAccel + ", " + imu.getGravity().yAccel + ", " + imu.getGravity().zAccel;
    }

    public void getGyroTelemetry() {
        updateAngles();
        linearOpMode.telemetry.addData("Heading", getHeading());
        linearOpMode.telemetry.addData("Roll", getRoll());
        linearOpMode.telemetry.addData("Pitch", getPitch());
        linearOpMode.telemetry.addData("Gravity", getGravity());
        linearOpMode.telemetry.update();
    }

    public void balanceRobot(SimpleMecanumDrive mecanumDrive) {

        double roll = getRoll();
        double pitch = getPitch();
        final double ANGLE_MARGIN = 10;

        final double ROLL_MARGIN = 8;
        final double PITCH_MARGIN = 6;
        double balancingSpeed = 0.2;

        //TODO: Move directions based on roll and pitch
        while(getRoll() > ROLL_MARGIN) {
            //mecanumDrive.moveFreely(Direction.FORWARD, balancingSpeed, 0);
        }
        while(getRoll() < -ROLL_MARGIN) {
            //mecanumDrive.moveFreely(Direction.RIGHT, balancingSpeed, 0);
        }
        while(getPitch() > PITCH_MARGIN) {
            //mecanumDrive.moveFreely(Direction.FORWARD, balancingSpeed, 0);
        }
        while(getPitch() < -PITCH_MARGIN) {
            //mecanumDrive.moveFreely(Direction.BACKWARD, balancingSpeed, 0);
        }

    }

    private String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    private String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
