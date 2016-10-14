package org.pattonvillerobotics.opmodes.autonomous;

import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.EncoderDrive;

/**
 * Created by murphyk01 on 10/1/16.
 */

public class AutoMethods {

    private EncoderDrive drive;

    AutoMethods(EncoderDrive drive) {
        this.drive = drive;
    }

    //~~~~~~~~~~~~~~ BLUE SIDE ~~~~~~~~~~~~~~//

    //Pushes Big Ball from Vortex Position - Blue Side
    public void BlueVortexC(){
        drive.moveInches(Direction.FORWARD, 15, Globals.HALF_MOTOR_POWER);
        drive.rotateDegrees(Direction.RIGHT, 45, Globals.HALF_MOTOR_POWER);
    }

    //Pushes Big Ball from Corner Position - Blue Side
    public void BlueCornerC() {
        drive.moveInches(Direction.BACKWARD, 24, Globals.HALF_MOTOR_POWER);
    }

    //Goes to both beacons far side first to close - Blue side
    public void BlueB() {
        drive.moveInches(Direction.FORWARD, 60, Globals.HALF_MOTOR_POWER);
        drive.rotateDegrees(Direction.RIGHT, 45, Globals.HALF_MOTOR_POWER);
        //implement the color sensors here and distance
        drive.rotateDegrees(Direction.RIGHT, 90, Globals.HALF_MOTOR_POWER);
        drive.moveInches(Direction.FORWARD, 38, Globals.HALF_MOTOR_POWER);
        drive.rotateDegrees(Direction.LEFT, 90, Globals.HALF_MOTOR_POWER);
        //Implement the color sensors here and distance sensor
    }

    //Goes to far beacon from Ball - blue side
    public void BlueFarB() {
        drive.moveInches(Direction.FORWARD, 60, Globals.HALF_MOTOR_POWER);
        drive.rotateDegrees(Direction.RIGHT, 45, Globals.HALF_MOTOR_POWER);
        //Implement the color and distance sensor code here
    }

    //Goes to close beacon from ball - blue side
    public void BlueCloseB() {
        drive.rotateDegrees(Direction.RIGHT, 45, Globals.HALF_MOTOR_POWER);
        drive.moveInches(Direction.FORWARD, 45, Globals.HALF_MOTOR_POWER);
        //Implement the color and distance sensor code here
    }

    //Parks from beacon - blue side
    public void BluePark() {
        drive.rotateDegrees(Direction.RIGHT, 90, Globals.HALF_MOTOR_POWER);
        drive.moveInches(Direction.FORWARD, 42, Globals.HALF_MOTOR_POWER);
    }

    //~~~~~~~~~~~~ RED SIDE~~~~~~~~~~~ //

    //Pushes Big Ball from Vortex Position - Red Side
    public void RedVortexC(){
        drive.moveInches(Direction.FORWARD, 15, Globals.HALF_MOTOR_POWER);
        drive.rotateDegrees(Direction.LEFT, 45, Globals.HALF_MOTOR_POWER);
    }

    //Pushes Big Ball from Corner Position - Red Side
    public void RedCornerC() {
        drive.moveInches(Direction.FORWARD, 84, Globals.HALF_MOTOR_POWER);
    }

    //Goes to both beacons far side first to close - Red side
    public void RedB() {
        drive.moveInches(Direction.FORWARD, 60, Globals.HALF_MOTOR_POWER);
        drive.rotateDegrees(Direction.LEFT, 45, Globals.HALF_MOTOR_POWER);
        //implement the color sensors here and distance
        drive.rotateDegrees(Direction.LEFT, 90, Globals.HALF_MOTOR_POWER);
        drive.moveInches(Direction.FORWARD, 38, Globals.HALF_MOTOR_POWER);
        drive.rotateDegrees(Direction.RIGHT, 90, Globals.HALF_MOTOR_POWER);
        //Implement the color sensors here and distance sensor
    }

    //Goes to far beacon from Ball - Red side
    public void RedFarB() {
        drive.moveInches(Direction.FORWARD, 60, Globals.HALF_MOTOR_POWER);
        drive.rotateDegrees(Direction.LEFT, 45, Globals.HALF_MOTOR_POWER);
        //Implement the color and distance sensor code here
    }

    //Goes to close beacon from ball - Red side
    public void RedCloseB() {
        drive.rotateDegrees(Direction.LEFT, 45, Globals.HALF_MOTOR_POWER);
        drive.moveInches(Direction.FORWARD, 45, Globals.HALF_MOTOR_POWER);
        //Implement the color and distance sensor code here
    }

    //Parks from beacon - Red side
    public void RedPark() {
        drive.rotateDegrees(Direction.LEFT, 90, Globals.HALF_MOTOR_POWER);
        drive.moveInches(Direction.FORWARD, 42, Globals.HALF_MOTOR_POWER);
    }
}
