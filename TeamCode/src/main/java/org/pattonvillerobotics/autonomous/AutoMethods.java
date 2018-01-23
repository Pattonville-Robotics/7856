package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.pattonvillerobotics.AbstractColorSensor;
import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.ImageProcessor;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.relicrecovery.jewels.JewelColorDetector;
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
    private JewelColorDetector.AnalysisResult analysis;

    /**
     * Constructs an AutoMethods object for use in LinearOpMode autonomous classes
     *
     * @param hardwareMap   the HardwareMap from the LinearOpMode
     * @param linearOpMode  the LinearOpMode
     * @param allianceColor the {@link AllianceColor} for the autonomous
     */
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
        jewelColorDetector = new JewelColorDetector(PhoneOrientation.PORTRAIT_INVERSE);

        jewelWhopper.moveUp();
        glyphGrabber.release();
        glyphter.getMotor().setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        vuforia.activateTracking();

        displayTelemetry(allianceColor + " autonomous initialized", true);

    }

    /**
     * Continously retrieves data from Vuforia and OpenCV until all values are detected
     *
     * @param detectPictograph boolean that determines whether to read the pictograph
     * @param detectJewel      boolean that determines whether to read the jewel set
     */
    public void readVuforiaValues(boolean detectPictograph, boolean detectJewel) {

        int tries = 0;

        if (detectJewel) {
            jewelColorDetector.process(vuforia.getImage());
            analysis = jewelColorDetector.getAnalysis();

            while ((analysis.leftJewelColor == null || analysis.rightJewelColor == null) && tries < 30) {
                jewelColorDetector.process(vuforia.getImage());
                analysis = jewelColorDetector.getAnalysis();
                tries++;
            }
            displayTelemetry("Left color: " + analysis.leftJewelColor, true);
            displayTelemetry("Right color: " + analysis.rightJewelColor, true);

        }

        if (detectPictograph) {
            drive.rotateDegrees(Direction.RIGHT, 20, 0.5);
            sleep(1);
            pictographKey = vuforia.getCurrentVisibleRelic();
            while (pictographKey == null || pictographKey.equals(RelicRecoveryVuMark.UNKNOWN)) {
                pictographKey = vuforia.getCurrentVisibleRelic();
            }
            displayTelemetry("Column Key:  " + pictographKey, true);
            drive.rotateDegrees(Direction.LEFT, 20, 0.5);
        }



    }

    public void pickUpGlyph() {
        glyphGrabber.clamp();
        sleep(1);
        glyphter.getMotor().setPower(0.5);
        sleep(1);
        glyphter.getMotor().setPower(0);
    }

    /**
     * Uses the {@link JewelColorDetector.AnalysisResult} to decide whether to turn left or right to knock off the corresponding jewel
     */
    public void knockOffJewel() {

        jewelWhopper.moveDown();

        sleep(0.5);

        try {

            AllianceColor leftColor = AbstractColorSensor.toAllianceColor(analysis.leftJewelColor);
            AllianceColor rightColor = AbstractColorSensor.toAllianceColor(analysis.rightJewelColor);

            if (leftColor.equals(allianceColor)) {
                displayTelemetry("Turning left", true);
                drive.rotateDegrees(Direction.LEFT, 30, 0.2);
                sleep(0.1);
                jewelWhopper.moveUp();
                drive.rotateDegrees(Direction.RIGHT, 30, 0.2);
            } else if (rightColor.equals(allianceColor)) {
                displayTelemetry("Turning right", true);
                drive.rotateDegrees(Direction.RIGHT, 30, 0.2);
                sleep(0.1);
                jewelWhopper.moveUp();
                drive.rotateDegrees(Direction.LEFT, 30, 0.2);
            } else {
                displayTelemetry("Error detecting colors", true);
            }

            displayTelemetry("Done whopping jewel", true);

        } catch (Error error) {
            displayTelemetry("Error detecting jewels: " + error.getMessage(), true);
        }

    }

    /**
     * Uses the IMU in the Rev Robotics Hub to determine when robot has driven onto a flat surface
     */
    public void driveOffBalancingStone() {

        drive.moveInches(Direction.BACKWARD, 5, 0.2);
        double angleMargin = 3;
        while ((gyro.getRoll() > angleMargin || gyro.getRoll() < -angleMargin) && (gyro.getPitch() > angleMargin || gyro.getPitch() < -angleMargin)) {
            gyro.getGyroTelemetry();
            drive.move(Direction.BACKWARD, 0.5);
        }

    }

    /**
     * Uses the pictograph key to decide how far to drive to get to the corresponding cryptobox column
     */
    public void driveToColumn() {


        if (pictographKey == null) {
            pictographKey = RelicRecoveryVuMark.CENTER;
        }

        switch (pictographKey) {

            case LEFT:
                drive.moveInches(Direction.FORWARD, allianceColor == AllianceColor.BLUE ? Globals.NEAR_DISTANCE : Globals.FAR_DISTANCE, 0.5);
                break;
            case CENTER:
                drive.moveInches(Direction.FORWARD, Globals.MEDIUM_DISTANCE, 0.5);
                break;
            case RIGHT:
                drive.moveInches(Direction.FORWARD, allianceColor == AllianceColor.BLUE ? Globals.FAR_DISTANCE : Globals.NEAR_DISTANCE, 0.5);
                break;
            default:
                displayTelemetry("No pictograph key detected; driving to center column by default", true);
                drive.moveInches(Direction.FORWARD, Globals.MEDIUM_DISTANCE, 0.5);
                break;
        }

        drive.rotateDegrees(allianceColor == AllianceColor.BLUE ? Direction.LEFT : Direction.RIGHT, 90, 0.5);
    }

    public void placeGlyph() {

        drive.moveInches(Direction.BACKWARD, Globals.DISTANCE_TO_CRYPTOBOX, 0.5);
        glyphter.getMotor().setPower(-0.5);
        sleep(1);
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

    /**
     * Container method that is called in LinearOpMode autonomous classes
     */
    public void runAutonomousProcess() {

        displayTelemetry("Running " + allianceColor + " autonomous", true);

        pickUpGlyph();

        readVuforiaValues(true, true);

        sleep(0.5);

        knockOffJewel();
        sleep(0.5);

        driveToColumn();
        sleep(0.5);

        placeGlyph();
        sleep(0.5);

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
        linearOpMode.sleep((long) seconds * 1000);
    }

    public MecanumEncoderDrive getMecanumEncoderDrive() {
        return drive;
    }


}
