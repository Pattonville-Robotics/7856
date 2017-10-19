package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.CustomVuforiaParameters;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.mechanisms.Glyphter;

/**
 * Created by pieperm on 10/5/17.
 */

public class AutoMethods {

    private HardwareMap hardwareMap;
    private LinearOpMode linearOpMode;
    private AllianceColor allianceColor;
    private MecanumEncoderDrive drive;
    private Glyphter glyphter;
    private VuforiaNavigation vuforia;

    public AutoMethods(HardwareMap hardwareMap, LinearOpMode linearOpMode, AllianceColor allianceColor) {

        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;
        this.allianceColor = allianceColor;

        vuforia.activateTracking();

    }

    public void initialize() {

        drive = new MecanumEncoderDrive(hardwareMap, linearOpMode, CustomRobotParameters.ROBOT_PARAMETERS);
        glyphter = new Glyphter(hardwareMap, linearOpMode, drive);
        vuforia = new VuforiaNavigation(CustomVuforiaParameters.VUFORIA_PARAMTERS);

    }


    public void driveToColumn() {

        final double NEAR_DISTANCE = 28;
        final double MEDIUM_DISTANCE = 35;
        final double FAR_DISTANCE = 42;
        Direction direction = allianceColor == AllianceColor.BLUE ? Direction.RIGHT : Direction.LEFT;

        switch (vuforia.getCurrentVisibleRelic()) {

            case LEFT:
                drive.moveInches(direction, allianceColor == AllianceColor.BLUE ? NEAR_DISTANCE : FAR_DISTANCE, 0.5);
                break;
            case CENTER:
                drive.moveInches(direction, MEDIUM_DISTANCE, 0.5);
                break;
            case RIGHT:
                drive.moveInches(direction, allianceColor == AllianceColor.BLUE ? FAR_DISTANCE : NEAR_DISTANCE, 0.5);
                break;
            default:
                driveToColumn();
                break;
        }

    }

    public void runAutonomousProcess() {



    }

}
