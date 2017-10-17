package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by martint08 on 10/12/17.
 */

public class Jewelwhopper {
    private Servo jewelWhopperServo;

    public Jewelwhopper(HardwareMap hardwareMap) {
        jewelWhopperServo = hardwareMap.servo.get("jewel-whopper-servo");

    }


}