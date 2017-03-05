package org.pattonvillerobotics.opmodes.autonomous;

/**
 * Created by pieperm on 10/6/16.
 */

public class Globals {

    // General
    public static final double ROBOT_LENGTH = 15;

    // Motors
    public static final double MAX_MOTOR_POWER = .7;
    public static final double LOW_MOTOR_POWER = .35;
    public static final double CANNON_POWER = -.7;
    public static final double ALIGN_MOTOR_POWER = .2;
    public static final double TURNING_SPEED = .3;

    // Turning
    public static final int RIGHT_TURN = 90;
    public static final int HALF_TURN = 45;
    public static final int ANGLE_TO_CAPBALL = 45;

    // Servos
    public static final double BUTTON_PRESSER_LEFT_POSITION = 0;
    public static final double BUTTON_PRESSER_RIGHT_POSITION = 1;
    public static final double BUTTON_PRESSER_DOWN_POSITION = 0.5;
    public static final double BUTTON_PRESSER_UP_POSITION = 0;

    // Distances
    public static final int MINIMUM_DISTANCE_TO_BEACON = 24;
    public static final double BEACON_HALF_WIDTH = 4.75;
    public static final double BEACON_BACKUP_DISTANCE = 3;
    public static final double PARTICLE_LAUNCH_DISTANCE = 24;
    public static final double NEAR_BEACON_TO_CORNER = 30;
    public static final double NEAR_BEACON_TO_CENTER = 30;
    public static final double FAR_BEACON_TO_CORNER = 40;
    public static final double FAR_BEACON_TO_CENTER = 90;

}
