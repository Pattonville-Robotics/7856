package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.pattonvillerobotics.AbstractColorSensor;
import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.ImageProcessor;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.relicrecovery.jewels.JewelAnalysisMode;
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

        drive.leftRearMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        drive.leftDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.rightDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        vuforia.activateTracking();

        jewelColorDetector.setAnalysisMode(JewelAnalysisMode.COMPLEX);

        displayTelemetry(allianceColor + " autonomous initialized", true);

    }

    /**
     * Continously retrieves data from Vuforia and OpenCV until all values are detected
     */
    public void readVuforiaValues() {

        drive.rotateDegrees(Direction.LEFT, 13, 0.5); // was 10 degrees

        pictographKey = vuforia.getCurrentVisibleRelic();
        while (pictographKey == null || pictographKey.equals(RelicRecoveryVuMark.UNKNOWN)) {
            pictographKey = vuforia.getCurrentVisibleRelic();
        }

        displayTelemetry("Column Key:  " + pictographKey, true);

        drive.rotateDegrees(Direction.RIGHT, 18, 0.5); // was 15 degrees

        jewelColorDetector.process(vuforia.getImage());
        analysis = jewelColorDetector.getAnalysis();

        while ((analysis.leftJewelColor == null || analysis.rightJewelColor == null)) {
            jewelColorDetector.process(vuforia.getImage());
            analysis = jewelColorDetector.getAnalysis();
        }

        displayTelemetry("Left color: " + analysis.leftJewelColor, true);
        displayTelemetry("Right color: " + analysis.rightJewelColor, true);

        drive.rotateDegrees(Direction.LEFT, 5, 0.5);

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
            double whopAngle = 20;

            if (leftColor.equals(allianceColor)) {
                displayTelemetry("Turning right to knock off " + rightColor + " jewel", true);
                drive.rotateDegrees(Direction.RIGHT, whopAngle, 0.2);
                sleep(0.1);
                jewelWhopper.moveUp();
                drive.rotateDegrees(Direction.LEFT, whopAngle, 0.2);
            } else if (rightColor.equals(allianceColor)) {
                displayTelemetry("Turning left to knock off " + leftColor + " jewel", true);
                drive.rotateDegrees(Direction.LEFT, whopAngle, 0.2);
                sleep(0.1);
                jewelWhopper.moveUp();
                drive.rotateDegrees(Direction.RIGHT, whopAngle, 0.2);
            } else {
                displayTelemetry("Error detecting colors", true);
            }

            displayTelemetry("Done whopping jewel", true);

        } catch (Exception exception) {
            displayTelemetry(exception.getMessage());
            jewelWhopper.moveUp();
        }

    }

    /**
     * Uses the IMU in the Rev Robotics Hub to determine when robot has driven onto a flat surface
     */
    public void driveOffBalancingStone() {

        Direction direction = allianceColor == AllianceColor.BLUE ? Direction.FORWARD : Direction.BACKWARD;

        drive.moveInches(direction, 5, 0.2);
        double angleMargin = 3;
        while (Math.abs(gyro.getRoll()) > angleMargin && Math.abs(gyro.getPitch()) > angleMargin) {
            gyro.getGyroTelemetry();
            drive.move(direction, 0.5);
        }

    }

    /**
     * Uses the pictograph key to decide how far to drive to get to the corresponding cryptobox column
     */
    public void driveToColumn() {

        jewelWhopper.moveUp();
        sleep(0.2);

        if (pictographKey == null) {
            pictographKey = RelicRecoveryVuMark.CENTER;
        }

        Direction direction = allianceColor == AllianceColor.BLUE ? Direction.FORWARD : Direction.BACKWARD;

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

        sleep(0.2);
        drive.rotateDegrees(Direction.LEFT, 90, 0.5);
    }

    public void placeGlyph() {

        glyphter.getMotor().setPower(-0.5);
        sleep(1);
        glyphter.getMotor().setPower(0);
        drive.moveInches(Direction.FORWARD, Globals.DISTANCE_TO_CRYPTOBOX, 0.5);
        glyphGrabber.release();
        drive.moveInches(Direction.BACKWARD, 6, 0.5);

    }

    public void park() {
        switch (pictographKey) {
            case LEFT:
                drive.moveInches(Direction.LEFT, 5, 0.5);
                break;
            case RIGHT:
                drive.moveInches(Direction.RIGHT, 5, 0.5);
                break;
            default:
                break;
        }
    }

    /**
     * Container method that is called in LinearOpMode autonomous classes
     */
    public void runAutonomousProcess() {

        displayTelemetry("Running " + allianceColor + " autonomous", true);

        pickUpGlyph();

        readVuforiaValues();

        sleep(0.5);

        knockOffJewel();
        sleep(0.5);

        driveToColumn();
        sleep(0.5);

        placeGlyph();
        sleep(0.5);

        park();

        sleep(1);
        displayTelemetry("Completed " + allianceColor + " autonomous", true);
        linearOpMode.stop();

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

    public void driveTest() {

        displayTelemetry("Driving " + Direction.FORWARD, true);
        drive.moveInches(Direction.FORWARD, 10, 0.5);

        sleep(0.5);

        displayTelemetry("Driving " + Direction.BACKWARD, true);
        drive.moveInches(Direction.BACKWARD, 10, 0.5);

        sleep(0.5);

        displayTelemetry("Turning " + Direction.LEFT, true);
        drive.rotateDegrees(Direction.LEFT, 90, 0.5);

        sleep(0.5);

        displayTelemetry("Turning " + Direction.RIGHT, true);
        drive.rotateDegrees(Direction.RIGHT, 90, 0.5);

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
