package org.pattonvillerobotics;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;

import java.util.Locale;

public class REVGyro {

    private BNO055IMU imu;
    private Orientation angles;

    public REVGyro(HardwareMap hardwareMap) {

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        imu = hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);

        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);

    }

    public void startAccelerationIntegration() {
        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
    }

    public double getHeading() {
        return Double.parseDouble(formatAngle(angles.angleUnit, angles.firstAngle));
    }

    public double getRoll() {
        return Double.parseDouble(formatAngle(angles.angleUnit, angles.secondAngle));
    }

    public double getPitch() {
        return Double.parseDouble(formatAngle(angles.angleUnit, angles.thirdAngle));
    }

    public double[] getGravity() {
        double[] array = {imu.getGravity().xAccel, imu.getGravity().yAccel, imu.getGravity().zAccel};
        return array;
    }

    public void getGyroTelemetry(Telemetry telemetry) {
        telemetry.addData("Heading", getHeading());
        telemetry.addData("Roll", getRoll());
        telemetry.addData("Pitch", getPitch());
        telemetry.addData("Gravity", getGravity());
        telemetry.update();
    }

    public void balanceRobot(SimpleMecanumDrive mecanumDrive) {

        double roll = getRoll();
        double pitch = getPitch();
        final double ANGLE_MARGIN = 10;

    }

    private String formatAngle(AngleUnit angleUnit, double angle) {
        return formatDegrees(AngleUnit.DEGREES.fromUnit(angleUnit, angle));
    }

    private String formatDegrees(double degrees) {
        return String.format(Locale.getDefault(), "%.1f", AngleUnit.DEGREES.normalize(degrees));
    }
}
