package org.pattonvillerobotics.robotclasses.vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.R;

/**
 * Created by greg on 10/12/2016.
 */

public class VuforiaParameters {

    private OpenGLMatrix phoneLocation;
    private OpenGLMatrix beaconLocation;
    private VuforiaLocalizer.CameraDirection cameraDirection;
    private VuforiaLocalizer.Parameters parameters;
    private VuforiaLocalizer vuforia;
    private String licenseKey;

    private VuforiaParameters(String licenseKey, VuforiaLocalizer.CameraDirection cameraDirection, OpenGLMatrix phoneLocation, OpenGLMatrix beaconLocation) {
        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.cameraDirection = cameraDirection;
        parameters.vuforiaLicenseKey = licenseKey;
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        this.licenseKey = licenseKey;
        this.phoneLocation = phoneLocation;
        this.cameraDirection = cameraDirection;
        this.beaconLocation = beaconLocation;
    }

    public VuforiaLocalizer getVuforia() { return vuforia; }

    public OpenGLMatrix getPhoneLocation() {
        return phoneLocation;
    }

    public OpenGLMatrix getBeaconLocation() {
        return beaconLocation;
    }

    public VuforiaLocalizer.CameraDirection getCameraDirection() {
        return cameraDirection;
    }

    public static class Builder {
        private String licenseKey;
        private VuforiaLocalizer.CameraDirection cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        private OpenGLMatrix phoneLocation = createMatrix(0,0,0, AxesOrder.XYZ, 0, 0, 0);
        private OpenGLMatrix beaconLocation = createMatrix(0,0,0, AxesOrder.XYZ, 0, 0, 0);

        public Builder() {
        }

        private OpenGLMatrix createMatrix(float x, float y, float z, AxesOrder o, float a, float b, float c) {
            return OpenGLMatrix
                    .translation(x, y, z)
                    .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, o, AngleUnit.DEGREES, a, b, c));
        }

        public Builder licenseKey(String licenseKey) {
            this.licenseKey = licenseKey;
            return this;
        }

        public Builder cameraDirection(VuforiaLocalizer.CameraDirection cameraDirection) {
            this.cameraDirection = cameraDirection;
            return this;
        }

        public Builder phoneLocation(float x, float y, float z, AxesOrder o, float a, float b, float c) {
            this.phoneLocation = createMatrix(x, y, z, o, a, b, c);
            return this;
        }

        public Builder beaconLocation(float x, float y, float z, AxesOrder o, float a, float b, float c) {
            this.beaconLocation = createMatrix(x, y, z, o, a, b, c);
            return this;
        }

        public VuforiaParameters build() {
            if(licenseKey == null) {
                throw new IllegalArgumentException("Must provide a license key.");
            }
            return new VuforiaParameters(licenseKey, cameraDirection, phoneLocation, beaconLocation);
        }
    }
}
