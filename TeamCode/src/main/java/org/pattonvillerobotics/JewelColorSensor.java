package org.pattonvillerobotics;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;

/**
 * Created by pieperm on 10/17/17.
 */

public class JewelColorSensor extends AbstractColorSensor {

    private JewelColorSensorPosition jewelColorSensorPosition;

    public JewelColorSensor(HardwareMap hardwareMap, JewelColorSensorPosition position) {
        super(hardwareMap);
        this.jewelColorSensorPosition = position;
    }

    public AllianceColor leftJewelColor() {

        AllianceColor detectedColor = dominantAllianceColor();

        switch (jewelColorSensorPosition) {
            case LEFT:
                return detectedColor;
            case RIGHT:
                return detectedColor == AllianceColor.BLUE ? AllianceColor.RED : AllianceColor.BLUE;
            default:
                throw new IllegalArgumentException("JewelColorSensorPosition must be LEFT or RIGHT");
        }
    }

    public AllianceColor rightJewelColor() {

        AllianceColor detectedColor = dominantAllianceColor();

        switch (jewelColorSensorPosition) {
            case LEFT:
                return detectedColor == AllianceColor.BLUE ? AllianceColor.RED : AllianceColor.BLUE;
            case RIGHT:
                return detectedColor;
            default:
                throw new IllegalArgumentException("JewelColorSensorPosition must be LEFT or RIGHT");

        }

    }

    public enum JewelColorSensorPosition {
        LEFT, RIGHT
    }

}
