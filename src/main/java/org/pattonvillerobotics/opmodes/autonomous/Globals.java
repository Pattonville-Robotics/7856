package org.pattonvillerobotics.opmodes.autonomous;

/**
 * Created by pieperm on 10/6/16.
 */

public class Globals {

    public static final double ROBOT_LENGTH = 15;

    // Motors
    public static final double MAX_MOTOR_POWER = 0.7;
    public static final double HALF_MOTOR_POWER = .25;
    public static final double CANNON_POWER = .9;
    public static final double ALIGN_MOTOR_POWER = .25;

    // Turning
    public static final int RIGHT_TURN = 90;
    public static final int HALF_TURN = 45;
    public static final int BEACON_ALIGN_TURN = 1;
    public static final double BEACON_MAXIMUM_OFFSET = 1;
    public static final double BEACON_MINIMUM_OFFSET = -1;

    // Servos
    public static final double BUTTON_PRESSER_LEFT_POSITION = 0;
    public static final double BUTTON_PRESSER_RIGHT_POSITION = 1;
    public static final double BUTTON_PRESSER_DOWN_POSITION = 0.5;
    public static final double BUTTON_PRESSER_UP_POSITION = 0;

    // Distances
    public static final int DISTANCE1_TO_CAPBALL = 10;
    public static final int DISTANCE2_TO_CAPBALL = 46;
    public static final int STRAIGHT_DISTANCE_TO_CAPBALL = 50;
    public static final int DISTANCE_TO_NEAR_BEACON = 40;
    public static final int DISTANCE_TO_NEXT_BEACON = 55;
    public static final int DISTANCE_TO_CORNER_VORTEX = 38;
    public static final int DISTANCE_TO_CLIMB_CORNER_VORTEX = 20;
    public static final int MINIMUM_DISTANCE_TO_BEACON = 24;
    public static final int BEACON_BACKUP_DISTANCE = 24;
    public static final double BEACON_HALF_WIDTH = 4.75;

}
