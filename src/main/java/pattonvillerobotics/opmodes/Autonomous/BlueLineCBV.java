package pattonvillerobotics.opmodes.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;

/**
 * Created by bahrg on 10/1/16.
 */

public class BlueLineCBV extends LinearOpMode {

    private EncoderDrive drive;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();


    }

    public void initialize() {
        drive = new EncoderDrive(hardwareMap, this, new RobotParameters.Builder()
                                    .encodersEnabled(true)
                                    .driveGearRatio(6)
                                    .gyroEnabled(false)
                                    .wheelBaseRadius(2)
                                    .wheelRadius(2)
                                    .build());
    }

    


}
