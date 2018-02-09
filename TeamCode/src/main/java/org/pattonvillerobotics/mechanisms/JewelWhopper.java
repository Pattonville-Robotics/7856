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
    private Position position;

    /**
     * Constructs a JewelWhopper mechanism designed to knock off the jewels
     *
     * @param hardwareMap     a HardwareMap
     * @param linearOpMode    a LinearOpMode
     * @param initialPosition the initial {@link Position} of the servo
     */
    public JewelWhopper(HardwareMap hardwareMap, LinearOpMode linearOpMode, Position initialPosition) {

        super(hardwareMap, linearOpMode);
        try {
            jewelWhopperServo = hardwareMap.servo.get("jewel-whopper-servo");
        } catch (IllegalArgumentException e) {
            displayTelemetry(TAG, "IllegalArgumentException: " + e.getMessage(), true);
        }
        position = initialPosition;

    }

    public void moveUp() {
        jewelWhopperServo.setPosition(1);
        position = Position.UP;
    }

    public void moveDown() {
        jewelWhopperServo.setPosition(0.45);
        position = Position.DOWN;
    }

    public void incrementPosition() {
        jewelWhopperServo.setPosition(jewelWhopperServo.getPosition() + 0.05);
    }

    public void decrementPosition() {
        jewelWhopperServo.setPosition(jewelWhopperServo.getPosition() - 0.05);
    }

    @Deprecated
    public void moveLeft() {
        jewelWhopperServo.setPosition(0);
    }

    @Deprecated
    public void moveRight() {
        jewelWhopperServo.setPosition(1);
    }

    @Deprecated
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

    public Position getPosition() {
        return position;
    }

    public Servo getServo() {
        return jewelWhopperServo;
    }

    public enum Position {
        UP,DOWN
    }

}