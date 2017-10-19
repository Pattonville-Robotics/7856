package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.JewelColorSensor;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;

/**
 * Created by martint08 on 10/12/17.
 */

public class JewelWhopper {

    private Servo jewelWhopperServo;

    public JewelWhopper(HardwareMap hardwareMap) {
        jewelWhopperServo = hardwareMap.servo.get("jewel-whopper-servo");

    }

    // TODO: find left and right servo values

    public void moveLeft() {
        jewelWhopperServo.setPosition(0);
    }

    public void moveRight() {
        jewelWhopperServo.setPosition(1);
    }

    public void knockOffJewel(AllianceColor allianceColor, JewelColorSensor jewelColorSensor) {

        AllianceColor leftJewelColor = jewelColorSensor.leftJewelColor();
        AllianceColor rightJewelColor = jewelColorSensor.rightJewelColor();

        if(leftJewelColor != allianceColor) {
            moveLeft();
        }
        else if(rightJewelColor != allianceColor) {
            moveRight();
        }

    }


}