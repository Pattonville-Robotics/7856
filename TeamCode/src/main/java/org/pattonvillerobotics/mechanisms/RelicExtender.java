package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by martint08 on 10/12/17.
 */

public class RelicExtender {

    private DcMotor relicExtenderMotor;

    public RelicExtender(HardwareMap hardwareMap) {
        relicExtenderMotor = hardwareMap.dcMotor.get("relic-extender-motor");
    }

    public void extend() {
        relicExtenderMotor.setPower(0.7);
    }

    public DcMotor getMotor() {
        return relicExtenderMotor;
    }

}
