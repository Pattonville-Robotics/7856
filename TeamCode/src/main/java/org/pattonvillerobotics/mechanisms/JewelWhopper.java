package org.pattonvillerobotics.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.pattonvillerobotics.JewelColorSensor;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;

/**
 * Created by martint08 on 10/12/17.
 */

public class JewelWhopper extends AbstractMechanism {

    public static final String TAG = JewelWhopper.class.getSimpleName();
    private Servo jewelWhopperServo;

    public JewelWhopper(HardwareMap hardwareMap, LinearOpMode linearOpMode) {

        super(hardwareMap, linearOpMode);
        try {
            jewelWhopperServo = hardwareMap.servo.get("jewel-whopper-servo");
        } catch (IllegalArgumentException e) {
            linearOpMode.telemetry.addData(TAG, e.getMessage());
            linearOpMode.telemetry.update();
        }

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