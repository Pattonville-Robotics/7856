package org.pattonvillerobotics.robotclasses.vuforia;

import android.graphics.Bitmap;

import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by greg on 10/12/2016.
 */

public class VuforiaNav {

    private static VuforiaTrackables beacons;
    private static final double mmPerInch = 25.4;
    private static VuforiaParameters vuforiaParameters;
    private static boolean isActivated;
    private static boolean initialized;

    private VuforiaNav(){
    }

    public static void initializeVuforia(VuforiaParameters config) {
        vuforiaParameters = config;
        vuforiaParameters.getVuforia().setFrameQueueCapacity(1);
        beacons = config.getVuforia().loadTrackablesFromAsset("FTC_2016-17");

        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        setBeaconLocation(config.getBeaconLocation());
        setPhoneInformation(config.getPhoneLocation());

        isActivated = false;
        initialized = true;
    }

    private static void setBeaconLocation(OpenGLMatrix location) {
        for(VuforiaTrackable beacon : beacons) {
            beacon.setLocation(location);
        }
    }

    private static void setPhoneInformation(OpenGLMatrix location) {
        for(VuforiaTrackable beacon : beacons) {
            ((VuforiaTrackableDefaultListener)beacon.getListener()).setPhoneInformation(location, vuforiaParameters.getCameraDirection());
        }
    }

    public static void activate() {
        if(!initialized) {
            throw new IllegalStateException("Vuforia must be initialized before activation.");
        }
        beacons.activate();
        isActivated = true;
    }

    public static void deactivate() {
        if(!initialized) {
            throw new IllegalStateException("Vuforia must be initialized before deactivation.");
        }
        beacons.deactivate();
        isActivated = false;
    }

    public static OpenGLMatrix getNearestBeaconLocation() {
        if(!isActivated) {
            throw new IllegalStateException("Vuforia must be activated to track beacons.");
        }
        for(VuforiaTrackable beacon : beacons) {
            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)beacon.getListener()).getUpdatedRobotLocation();
            if (robotLocationTransform != null) {
                return robotLocationTransform;
            }
        }
        return null;
    }

    public static double getDistance(OpenGLMatrix lastLocation) {
        VectorF translation = lastLocation.getTranslation();
        return translation.getData()[0]/mmPerInch;
    }

    public static double getxPos(OpenGLMatrix lastLocation) {
        VectorF translation = lastLocation.getTranslation();
        return translation.getData()[1]/mmPerInch;
    }

    public static Bitmap getImage() {
        Image img = null;

        for(int i = 0; i<vuforiaParameters.getVuforia().getFrameQueueCapacity(); i++) {
            if(vuforiaParameters.getVuforia().getFrameQueue().poll().getImage(i).getFormat() == PIXEL_FORMAT.RGB565) {
                img = vuforiaParameters.getVuforia().getFrameQueue().poll().getImage(i);
            }
        }

        if(img != null) {
            Bitmap bm = Bitmap.createBitmap(img.getWidth(), img.getHeight(), Bitmap.Config.RGB_565);
            bm.copyPixelsFromBuffer(img.getPixels());
            return bm;
        } else {
            return null;
        }
    }
}