package org.pattonvillerobotics.robotclasses.vuforia;

import android.graphics.Bitmap;

import com.vuforia.Image;

import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;

/**
 * Created by bahrg on 10/29/16.
 */

public class VuforiaNav {

    private VuforiaTrackables beacons;
    private final double mmPerInch = 25.4;
    private boolean isActivated;
    private VuforiaLocalizerImplSubclass vuforia;
    private OpenGLMatrix lastLocation;
    private VuforiaLocalizer.Parameters parameters;

    public VuforiaNav(VuforiaParameters parameters) {
        this.parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        this.parameters.cameraDirection = parameters.getCameraDirection();
        this.parameters.vuforiaLicenseKey = parameters.getLicenseKey();
        this.parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;
        vuforia = new VuforiaLocalizerImplSubclass(this.parameters);
        beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");

        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        setPhoneInformation(parameters.getPhoneLocation());
        setBeaconLocation(parameters.getBeaconLocation());

        isActivated = false;
    }

    private void setBeaconLocation(OpenGLMatrix location) {
        for(VuforiaTrackable beacon : beacons) {
            beacon.setLocation(location);
        }
    }

    private void setPhoneInformation(OpenGLMatrix location) {
        for(VuforiaTrackable beacon : beacons) {
            ((VuforiaTrackableDefaultListener)beacon.getListener()).setPhoneInformation(location, parameters.cameraDirection);
        }
    }

    public void activate() {
        beacons.activate();
        isActivated = true;
    }

    public void deactivate() {
        beacons.deactivate();
        isActivated = false;
    }

    public OpenGLMatrix getNearestBeaconLocation() {
        if(!isActivated) {
            throw new IllegalStateException("Vuforia must be activated to track beacons.");
        }
        for(VuforiaTrackable beacon : beacons) {
            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)beacon.getListener()).getUpdatedRobotLocation();
            if (robotLocationTransform != null) {
                lastLocation = robotLocationTransform;
                return robotLocationTransform;
            }
        }
        return null;
    }

    public double getDistance() {
        VectorF translation = lastLocation.getTranslation();
        return translation.getData()[0]/mmPerInch;
    }

    public double getxPos() {
        VectorF translation = lastLocation.getTranslation();
        return translation.getData()[1]/mmPerInch;
    }

    public double getAngle() {
        return FastMath.toDegrees(FastMath.atan(getDistance() / getxPos()));
    }

    public Bitmap getImage() {
        Image img = vuforia.getImage();

        if(img != null) {
            Bitmap bm = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.RGB_565);
            bm.copyPixelsFromBuffer(img.getPixels());
            return bm;
        } else {
            return null;
        }
    }

}
