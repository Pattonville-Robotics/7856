package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.AbstractColorSensor;
import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.JewelColorSensor;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.ImageProcessor;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.JewelColorDetector;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.util.PhoneOrientation;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.mechanisms.GlyphGrabber;
import org.pattonvillerobotics.mechanisms.Glyphter;
import org.pattonvillerobotics.mechanisms.JewelWhopper;
import org.pattonvillerobotics.mechanisms.REVGyro;

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
    private GlyphGrabber glyphGrabber;
    private VuforiaNavigation vuforia;
    private JewelWhopper jewelWhopper;
    private JewelColorSensor jewelColorSensor;
    private REVGyro gyro;
    private SimpleMecanumDrive simpleMecanumDrive;
    private JewelColorDetector jewelColorDetector;

    public AutoMethods(HardwareMap hardwareMap, LinearOpMode linearOpMode, AllianceColor allianceColor) {

        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;
        this.allianceColor = allianceColor;

        drive = new MecanumEncoderDrive(hardwareMap, linearOpMode, CustomRobotParameters.ROBOT_PARAMETERS);
        glyphter = new Glyphter(hardwareMap, linearOpMode); //, drive);
        glyphGrabber = new GlyphGrabber(hardwareMap, linearOpMode, Globals.GrabberPosition.RELEASED);
        gyro = new REVGyro(hardwareMap, linearOpMode);
        simpleMecanumDrive = new SimpleMecanumDrive(linearOpMode, hardwareMap);
        vuforia = new VuforiaNavigation(CustomRobotParameters.VUFORIA_PARAMETERS);
        jewelColorDetector = new JewelColorDetector(PhoneOrientation.PORTRAIT);

        vuforia.activateTracking();
        ImageProcessor.initOpenCV(hardwareMap, linearOpMode);

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

        drive.rotateDegrees(Direction.LEFT, 180, 0.2);
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

    public void detectJewelColor() {

        while(jewelColorDetector.getAnalysis().leftJewelColor == null || jewelColorDetector.getAnalysis().rightJewelColor ==  null) {
            jewelColorDetector.process(vuforia.getImage());
        }

        AllianceColor leftColor = AbstractColorSensor.toAllianceColor(jewelColorDetector.getAnalysis().leftJewelColor);
        AllianceColor rightColor = AbstractColorSensor.toAllianceColor(jewelColorDetector.getAnalysis().rightJewelColor);

        if(leftColor.equals(allianceColor) && !rightColor.equals(allianceColor)) {
            drive.rotateDegrees(Direction.LEFT, 30, 0.2);
            linearOpMode.sleep(1000);
            drive.rotateDegrees(Direction.RIGHT, 30, 0.2);
        } else if(rightColor.equals(allianceColor) && !rightColor.equals(allianceColor)) {
            drive.rotateDegrees(Direction.RIGHT, 30, 0.2);
            linearOpMode.sleep(1000);
            drive.rotateDegrees(Direction.LEFT, 30, 0.2);
        }
        else {
            linearOpMode.telemetry.addData("OpenCV", "Error detecting colors");
        }


    }

    public void placeGlyph() {

        drive.moveInches(Direction.FORWARD, Globals.NEAR_DISTANCE-5, 0.5);
        glyphGrabber.release();
        drive.moveInches(Direction.BACKWARD, Globals.NEAR_DISTANCE-5, 0.5);
        drive.rotateDegrees(Direction.CLOCKWISE, 180, 0.3);

    }

    public void runAutonomousProcess() {

        runTestProcess();
//        linearOpMode.telemetry.addData(TAG, allianceColor +  " autonomous initialized!");
//
//        driveToJewel();
//        driveOffBalancingStone();
//        driveToColumn();


    }

    public void runTestProcess() {

        linearOpMode.telemetry.addData(TAG, allianceColor + " test autonomous initialized!");

        glyphGrabber.clamp();
        drive.moveInches(Direction.LEFT, Globals.MEDIUM_DISTANCE, 0.5);
        linearOpMode.sleep(1000);
        drive.rotateDegrees(Direction.LEFT, 180, 0.3);
        linearOpMode.sleep(1000);
        glyphGrabber.release();

        linearOpMode.telemetry.addData(TAG,  allianceColor + "test autonomous complete");

        driveToJewel();
        driveOffBalancingStone();
        driveToColumn();
        placeGlyph();

    }

}
