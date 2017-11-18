package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
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
    private REVGyro gyro;
    private JewelColorDetector jewelColorDetector;
    private RelicRecoveryVuMark pictographKey;
    private JewelColorDetector.Analysis analysis;

    public AutoMethods(HardwareMap hardwareMap, LinearOpMode linearOpMode, AllianceColor allianceColor) {

        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;
        this.allianceColor = allianceColor;

        displayTelemetry("Wait for Vuforia to initialize...", true);

        ImageProcessor.initOpenCV(hardwareMap, linearOpMode);

        drive = new MecanumEncoderDrive(hardwareMap, linearOpMode, CustomRobotParameters.ROBOT_PARAMETERS);
        glyphter = new Glyphter(hardwareMap, linearOpMode);
        glyphGrabber = new GlyphGrabber(hardwareMap, linearOpMode, Globals.GrabberPosition.RELEASED);
        jewelWhopper = new JewelWhopper(hardwareMap, linearOpMode, JewelWhopper.Position.UP);
        gyro = new REVGyro(hardwareMap, linearOpMode);
        vuforia = new VuforiaNavigation(CustomRobotParameters.VUFORIA_PARAMETERS);
        jewelColorDetector = new JewelColorDetector(PhoneOrientation.LANDSCAPE_INVERSE);

        jewelWhopper.moveUp();
        glyphGrabber.release();

        vuforia.activateTracking();

        displayTelemetry(allianceColor + " autonomous initialized", true);

    }

    public void readVuforiaValues() {

        pictographKey = vuforia.getCurrentVisibleRelic();
        while(pictographKey == null || pictographKey.equals(RelicRecoveryVuMark.UNKNOWN)) {
            pictographKey = vuforia.getCurrentVisibleRelic();
        }

//        jewelColorDetector.process(vuforia.getImage());
//        try {
//            analysis = jewelColorDetector.getAnalysis();
//            displayTelemetry("Left color: " + analysis.leftJewelColor, true);
//            displayTelemetry("Right color: " + analysis.rightJewelColor, true);
//        } catch (NullPointerException n) {
//            displayTelemetry(n.getMessage(), true);
//        }

//        while(analysis.leftJewelColor == null || analysis.rightJewelColor ==  null) {
//            jewelColorDetector.process(vuforia.getImage());
//        }

        displayTelemetry("Driving to " + pictographKey + " column", true);


    }

    public void pickUpGlyph() {
        glyphGrabber.clamp();
        sleep(0.5);
        glyphter.getMotor().setPower(0.5);
        sleep(1);
        glyphter.getMotor().setPower(0);
    }

    public void knockOffJewel() {

        jewelWhopper.moveDown();

        try {

            AllianceColor leftColor = AbstractColorSensor.toAllianceColor(analysis.leftJewelColor);
            AllianceColor rightColor = AbstractColorSensor.toAllianceColor(analysis.rightJewelColor);

            if (leftColor.equals(allianceColor) && !rightColor.equals(allianceColor)) {
                drive.rotateDegrees(Direction.LEFT, 30, 0.2);
                sleep(1);
                drive.rotateDegrees(Direction.RIGHT, 30, 0.2);
            } else if (rightColor.equals(allianceColor) && !rightColor.equals(allianceColor)) {
                drive.rotateDegrees(Direction.RIGHT, 30, 0.2);
                sleep(1);
                drive.rotateDegrees(Direction.LEFT, 30, 0.2);
            } else {
                linearOpMode.telemetry.addData("OpenCV", "Error detecting colors");
            }

        } catch(NullPointerException n) {
            displayTelemetry(n.getMessage(), true);
        }

        jewelWhopper.moveUp();


    }

    public void driveOffBalancingStone() {

        Direction direction = allianceColor == AllianceColor.BLUE ? Direction.LEFT : Direction.RIGHT;

        drive.moveInches(direction, 1, 0.2);
        double angleMargin = 3;
        while ((gyro.getRoll() > angleMargin ||  gyro.getRoll() < -angleMargin) && ( gyro.getPitch() > angleMargin || gyro.getPitch() < -angleMargin)) {
            displayTelemetry("Roll: " + gyro.getRoll());
            displayTelemetry("Pitch: " + gyro.getPitch());
            drive.move(direction, 0.2);
        }

    }

    public void driveToColumn() {

        Direction direction = allianceColor == AllianceColor.BLUE ? Direction.LEFT : Direction.RIGHT;

        if(pictographKey == null) {
            pictographKey = RelicRecoveryVuMark.CENTER;
        }

        switch (pictographKey) {

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
                displayTelemetry("No pictograph key detected; driving to center column by default", true);
                drive.moveInches(direction, Globals.MEDIUM_DISTANCE, 0.5);
                break;
        }

        drive.rotateDegrees(Direction.LEFT, 170, 0.5);
    }

    public void placeGlyph() {

        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE_TO_CRYPTOBOX, 0.5);
        glyphter.getMotor().setPower(-0.5);
        sleep(0.5);
        glyphter.getMotor().setPower(0);
        glyphGrabber.release();
        drive.moveInches(Direction.FORWARD, Globals.DISTANCE_TO_CRYPTOBOX, 0.5);

    }

    public void park() {
        switch (pictographKey) {
            case LEFT:
                drive.moveInches(Direction.RIGHT, 5, 0.5);
                break;
            case RIGHT:
                drive.moveInches(Direction.LEFT, 5, 0.5);
                break;
            default:
                break;
        }
        sleep(1);
        drive.rotateDegrees(Direction.LEFT, 170, 0.5);
    }

    public void runAutonomousProcess() {

        displayTelemetry("Running " + allianceColor + " autonomous", true);

        readVuforiaValues();

        pickUpGlyph();

        //knockOffJewel();
        sleep(1);

        driveToColumn();
        sleep(1);

        placeGlyph();
        sleep(1);

        park();

        sleep(1);
        linearOpMode.stop();

        displayTelemetry("Completed " + allianceColor + " autonomous", true);

    }

    public void runTestProcess() {

        displayTelemetry("Running " + allianceColor + " test autonomous", true);

        sleep(2);

        pickUpGlyph();

        sleep(2);

        drive.moveInches(Direction.RIGHT, Globals.MEDIUM_DISTANCE, 0.5);

        sleep(2);

        drive.rotateDegrees(Direction.LEFT, 180, 0.5);

        sleep(2);

        drive.moveInches(Direction.BACKWARD, 10, 0.3);

        sleep(2);

        glyphGrabber.release();

        sleep(2);



    }

    private void displayTelemetry(Object value) {
        linearOpMode.telemetry.addData(TAG, value);
        linearOpMode.telemetry.update();
    }

    private void displayTelemetry(Object value, boolean setRetained) {
        linearOpMode.telemetry.addData(TAG, value).setRetained(setRetained);
        linearOpMode.telemetry.update();
    }

    private void sleep(double seconds) {
        linearOpMode.sleep((long)seconds * 1000);
    }

    public MecanumEncoderDrive getMecanumEncoderDrive() {
        return drive;
    }


}
