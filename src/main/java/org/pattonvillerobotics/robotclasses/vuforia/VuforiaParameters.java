package org.pattonvillerobotics.robotclasses.vuforia;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.teamcode.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by greg on 10/12/2016.
 */

public class VuforiaParameters {

    private OpenGLMatrix phoneLocation;
    private List<OpenGLMatrix> beaconLocations;
    private VuforiaLocalizer.CameraDirection cameraDirection;
    private VuforiaLocalizer.Parameters parameters;
    private String licenseKey;

    private VuforiaParameters(String licenseKey, VuforiaLocalizer.CameraDirection cameraDirection, OpenGLMatrix phoneLocation, List<OpenGLMatrix> beaconLocations) {
        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.cameraDirection = cameraDirection;
        parameters.vuforiaLicenseKey = licenseKey;
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        this.licenseKey = licenseKey;
        this.phoneLocation = phoneLocation;
        this.cameraDirection = cameraDirection;
        this.beaconLocations = beaconLocations;
    }

    public OpenGLMatrix getPhoneLocation() {
        return phoneLocation;
    }

    public List<OpenGLMatrix> getBeaconLocations() {
        return beaconLocations;
    }

    public VuforiaLocalizer.CameraDirection getCameraDirection() {
        return cameraDirection;
    }

    public VuforiaLocalizer.Parameters getParameters() {
        return parameters;
    }

    public static class Builder {
        private String licenseKey;
        private VuforiaLocalizer.CameraDirection cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        private OpenGLMatrix phoneLocation = createMatrix(0, 0, 0, AxesOrder.XYZ, 0, 0, 0);
        private List<OpenGLMatrix> beaconLocations = new ArrayList<>();

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

        /**
         * Should be called IN ORDER
         *
         * @param x
         * @param y
         * @param z
         * @param o
         * @param a
         * @param b
         * @param c
         * @return this
         */
        public Builder addBeaconLocation(float x, float y, float z, AxesOrder o, float a, float b, float c) {
            this.beaconLocations.add(createMatrix(x, y, z, o, a, b, c));
            return this;
        }

        public VuforiaParameters build() {
            if (licenseKey == null) {
                throw new IllegalArgumentException("Must provide a license key.");
            }
            return new VuforiaParameters(licenseKey, cameraDirection, phoneLocation, beaconLocations);
        }
    }
}
