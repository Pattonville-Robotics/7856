package org.pattonvillerobotics.robotclasses.vuforia;

import android.graphics.Bitmap;

import com.vuforia.Image;

import org.apache.commons.math3.util.FastMath;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.List;

/**
 * Created by bahrg on 10/29/16.
 */

public class VuforiaNav {

    private final double mmPerInch = 25.4;
    private VuforiaTrackables beacons;
    private VuforiaParameters vuforiaParameters;
    private boolean isActivated;
    private VuforiaLocalizerImplSubclass vuforia;
    private OpenGLMatrix lastLocation;

    public VuforiaNav(VuforiaParameters parameters) {
        vuforiaParameters = parameters;
        vuforia = new VuforiaLocalizerImplSubclass(vuforiaParameters.getParameters());
        beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");

        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        setPhoneInformation(vuforiaParameters.getPhoneLocation());
        setBeaconLocations(vuforiaParameters.getBeaconLocations());

        isActivated = false;
    }

    private void setBeaconLocations(List<OpenGLMatrix> locations) {
        for (int i = 0; i < beacons.size(); i++) {
            beacons.get(i).setLocation(locations.get(i));
        }
    }

    private void setPhoneInformation(OpenGLMatrix location) {
        for(VuforiaTrackable beacon : beacons) {
            ((VuforiaTrackableDefaultListener)beacon.getListener()).setPhoneInformation(location, vuforiaParameters.getCameraDirection());
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
