package org.pattonvillerobotics;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.ColorSensorColor;

/**
 * Created by pieperm on 10/17/17.
 */

public abstract class AbstractColorSensor {

    private ColorSensor colorSensor;

    public AbstractColorSensor(ColorSensor colorSensor) {
        this.colorSensor = colorSensor;
    }

    public AbstractColorSensor(HardwareMap hardwareMap) {
        this(hardwareMap.colorSensor.get("color-sensor"));
    }

    public static AllianceColor toAllianceColor(ColorSensorColor colorSensorColor) {
        switch (colorSensorColor) {
            case RED:
                return AllianceColor.RED;
            case BLUE:
                return AllianceColor.BLUE;
            default:
                throw new IllegalArgumentException("ColorSensorColor must be BLUE or RED");
        }
    }

    public int red() {
        return colorSensor.red();
    }

    public int green() {
        return colorSensor.green();
    }

    public int blue() {
        return colorSensor.blue();
    }

    public int alpha() {
        return colorSensor.alpha();
    }

    public ColorSensorColor dominantColor() {
        int blue = blue(), red = red(), green = green();

        if (blue > red && blue > green) {
            return ColorSensorColor.BLUE;

        } else if (red > blue && red > green) {
            return ColorSensorColor.RED;

        } else if (green > blue && green > red) {
            return ColorSensorColor.GREEN;

        } else {
            return null;
        }
    }

    public AllianceColor dominantAllianceColor() {

        return toAllianceColor(dominantColor());

    }



}
