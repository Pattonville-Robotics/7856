package org.pattonvillerobotics.enums;

/**
 * Created by bahrg on 12/27/16.
 */

public enum ArmPosition {
    IN(0), OUT(1);

    private int value;

    ArmPosition(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
