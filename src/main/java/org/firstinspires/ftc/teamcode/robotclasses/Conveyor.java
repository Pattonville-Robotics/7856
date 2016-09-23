package org.firstinspires.ftc.teamcode.robotclasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;


/**
 * Created by pieperm on 9/22/16.
 */
public class Conveyor {

    DcMotor conveyor;

    public Conveyor(HardwareMap hardwareMap) {
        conveyor = hardwareMap.dcMotor.get("conveyor");
    }

    public void run() {
        conveyor.setPower(0.7);
    }

    public void stop() {
        conveyor.setPower(0);
    }


}
