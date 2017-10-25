package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.JewelColorSensor;
import org.pattonvillerobotics.mechanisms.REVGyro;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.mechanisms.Glyphter;
import org.pattonvillerobotics.mechanisms.JewelWhopper;

/**
 * Created by pieperm on 10/5/17.
 */

public class AutoMethods {

    public static final String TAG = AutoMethods.class.getSimpleName();
    private HardwareMap hardwareMap;
    private LinearOpMode linearOpMode;
    private AllianceColor allianceColor;
    private MecanumEncoderDrive drive;
    private Glyphter glyphter;
    private VuforiaNavigation vuforia;
    private JewelWhopper jewelWhopper;
    private JewelColorSensor jewelColorSensor;
    private REVGyro gyro;
    private SimpleMecanumDrive simpleMecanumDrive;

    public AutoMethods(HardwareMap hardwareMap, LinearOpMode linearOpMode, AllianceColor allianceColor) {

        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;
        this.allianceColor = allianceColor;

        drive = new MecanumEncoderDrive(hardwareMap, linearOpMode, CustomRobotParameters.ROBOT_PARAMETERS);
        glyphter = new Glyphter(hardwareMap); //, drive);
        gyro = new REVGyro(hardwareMap);
        simpleMecanumDrive = new SimpleMecanumDrive(linearOpMode, hardwareMap);
        vuforia = new VuforiaNavigation(CustomRobotParameters.VUFORIA_PARAMETERS);

        vuforia.activateTracking();

    }


    public void driveToColumn() {

        Direction direction = allianceColor == AllianceColor.BLUE ? Direction.LEFT : Direction.RIGHT;

        switch (vuforia.getCurrentVisibleRelic()) {

            case LEFT:
                drive.moveInches(direction, allianceColor == AllianceColor.BLUE ? Globals.NEAR_DISTANCE : Globals.FAR_DISTANCE, 0.5);
                break;
            case CENTER:
                drive.moveInches(direction, Globals.MEDIUM_DISTANCE, 0.5);
                break;
            case RIGHT:
                drive.moveInches(direction, allianceColor == AllianceColor.BLUE ? Globals.FAR_DISTANCE : Globals.NEAR_DISTANCE, 0.5);
                break;
            default:
                driveToColumn();
                break;
        }

    }


    public void driveOffBalancingStone() {

        drive.moveInches(allianceColor == AllianceColor.BLUE? Direction.LEFT: Direction.RIGHT, 1, 0.2);
        double angleMargin = 3;
        double angle = allianceColor == AllianceColor.BLUE ? 180 : 0;
        while ((gyro.getRoll() > angleMargin ||  gyro.getRoll() < -angleMargin) && ( gyro.getPitch() > angleMargin || gyro.getPitch() < -angleMargin)){
            simpleMecanumDrive.moveFreely(angle, 0.3, 0);
        }

    }

    public void park(){

    }

    public void driveToJewel(){
        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE_TO_JEWEL, .5);
        jewelWhopper.knockOffJewel(allianceColor, jewelColorSensor);
        drive.moveInches(Direction.FORWARD, Globals.DISTANCE_TO_JEWEL, .5);
    }

    public void runAutonomousProcess() {

        linearOpMode.telemetry.addData(TAG, allianceColor +  " autonomous initialized!");

    }

}
