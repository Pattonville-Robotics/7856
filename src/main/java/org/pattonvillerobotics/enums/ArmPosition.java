package org.pattonvillerobotics.enums;

/**
 * Created by bahrg on 12/27/16.
 */

public enum ArmPosition {
    IN(1), OUT(0);

    private double value;

    ArmPosition(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }
}
