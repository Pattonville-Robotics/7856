package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by martint08 on 10/12/17.
 */

public class RelicExtender extends AbstractMechanism {

    public static final String TAG = RelicExtender.class.getSimpleName();
    private DcMotor relicExtenderMotor;

    public RelicExtender(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        try {
            relicExtenderMotor = hardwareMap.dcMotor.get("relic-extender-motor");
        } catch (IllegalArgumentException e) {
            linearOpMode.telemetry.addData(TAG, e.getMessage());
            linearOpMode.telemetry.update();
        }

    }

    public void extend() {
        relicExtenderMotor.setPower(0.7);
    }

    public DcMotor getMotor() {
        return relicExtenderMotor;
    }

}
