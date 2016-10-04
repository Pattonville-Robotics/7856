package pattonvillerobotics.robotclasses;

import android.graphics.Bitmap;

import com.vuforia.Image;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.teamcode.R;

/**
 * Created by greg on 10/1/2016.
 */

public class VuforiaNav {

    private VuforiaLocalizer.Parameters parameters;
    private VuforiaLocalizerImplHack vuforia;
    private VuforiaTrackables beacons;
    private boolean isActivated;

    public VuforiaNav() {
        parameters = new VuforiaLocalizer.Parameters(R.id.cameraMonitorViewId);
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.vuforiaLicenseKey = "AclLpHb/////AAAAGa41kVT84EtWtYJZW0bIHf9DHg5EHVYWCqExQMx6bbuBtjFeYdvzZLExJiXnT31qDi3WI3QQnOXH8pLZ4cmb39d1w0Oi7aCwy35ODjMvG5qX+e2+3v0l3r1hPpM8P7KPTkRPIl+CGYEBvoNkVbGGjalCW7N9eFDV/T5CN/RQvZjonX/uBPKkEd8ciqK8vWgfy9aPEipAoyr997DDagnMQJ0ajpwKn/SAfaVPA4osBZ5euFf07/3IUnpLEMdMKfoIH6QYLVgwbPuVtUiJWM6flzWaAw5IIhy0XXWwI0nGXrzVjPwZlN3El4Su73ADK36qqOax/pNxD4oYBrlpfYiaFaX0Q+BNro09weXQEoz/Mfgm";
        parameters.cameraMonitorFeedback = VuforiaLocalizer.Parameters.CameraMonitorFeedback.AXES;

        vuforia = new VuforiaLocalizerImplHack(parameters);

        beacons = vuforia.loadTrackablesFromAsset("FTC_2016-17");

        beacons.get(0).setName("Wheels");
        beacons.get(1).setName("Tools");
        beacons.get(2).setName("Lego");
        beacons.get(3).setName("Gears");

        setBeaconLocation();
        setPhoneInformation();

        isActivated = false;

    }

    public void activate() {
        beacons.activate();
        isActivated = true;
    }

    public void deactivate() {
        beacons.deactivate();
        isActivated = false;
    }

    public VuforiaTrackables getBeacons() {
        return beacons;
    }

    public VectorF getTranslation(VuforiaTrackable beacon) {
        OpenGLMatrix lastLoc = null;
        OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener)beacon.getListener()).getUpdatedRobotLocation();
        if(robotLocationTransform != null) {
            lastLoc = robotLocationTransform;
        }
        if(lastLoc != null) {
            return lastLoc.getTranslation();
        } else {
            return null;
        }


    }

    public Bitmap getRGBAImage() {
        Image vimg = vuforia.getRGBImage();
        if(vimg != null) {
            Bitmap bm = Bitmap.createBitmap(vimg.getWidth(), vimg.getHeight(), Bitmap.Config.RGB_565);
            bm.copyPixelsFromBuffer(vimg.getPixels());
            return bm;
        } else {
            return null;
        }
    }

    public Bitmap getGrayImage() {
        Image vimg = vuforia.getGrayImage();
        if(vimg != null) {
            Bitmap bm = Bitmap.createBitmap(vimg.getWidth(), vimg.getHeight(), Bitmap.Config.RGB_565);
            bm.copyPixelsFromBuffer(vimg.getPixels());
            return bm;
        } else {
            return null;
        }
    }

    private void setBeaconLocation() {
        OpenGLMatrix loc = OpenGLMatrix.translation(0,0,0).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX, AngleUnit.DEGREES, 90, 90, 0));
        for(VuforiaTrackable beacon : beacons) {
            beacon.setLocation(loc);
        }
    }

    private void setPhoneInformation() {
        OpenGLMatrix phoneLoc = OpenGLMatrix.translation(0,0,0).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.YZY, AngleUnit.DEGREES, 0, 0, 0));
        for(VuforiaTrackable beacon : beacons) {
            ((VuforiaTrackableDefaultListener)beacon.getListener()).setPhoneInformation(phoneLoc, parameters.cameraDirection);
        }
    }

    public boolean isActivated() {
        return isActivated;
    }

}
