package org.pattonvillerobotics.autonomous;

import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;

/**
 * Created by pieperm on 10/5/17.
 */

public class AutoMethods {

    private AllianceColor allianceColor;
    private Direction defaultTurnDireciton, oppositeTurnDirection;

    public AutoMethods(AllianceColor allianceColor) {

        this.allianceColor = allianceColor;

        switch(allianceColor) {
            case RED:
                defaultTurnDireciton = Direction.LEFT;
                oppositeTurnDirection = Direction.RIGHT;
                break;
            case BLUE:
                defaultTurnDireciton = Direction.RIGHT;
                oppositeTurnDirection = Direction.LEFT;
                break;
        }

    }

}
