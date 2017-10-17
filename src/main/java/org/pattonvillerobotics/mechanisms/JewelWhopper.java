package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.ColorSensorColor;

/**
 * Created by pieperm on 10/12/17.
 */

public class JewelWhopper {

    private Servo jewelWhopper;

    public JewelWhopper(HardwareMap hardwareMap) {

        jewelWhopper = hardwareMap.servo.get("jewel-whopper");

    }

    public void moveLeft() {
        jewelWhopper.setPosition(0);
    }

    public void moveRight() {
        jewelWhopper.setPosition(1);
    }

    public void knockOffJewel(AllianceColor allianceColor, AllianceColor leftJewelColor, AllianceColor rightJewelColor) {

        if(leftJewelColor == allianceColor) {
            moveRight();
        }
        else if(rightJewelColor == allianceColor) {
            moveLeft();
        }

    }

}
