package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by martint08 on 10/12/17.
 */

public class JewelWhopper {
    private Servo jewelWhopperServo;

    public JewelWhopper(HardwareMap hardwareMap) {
        jewelWhopperServo = hardwareMap.servo.get("jewel-whopper-servo");

    }


}