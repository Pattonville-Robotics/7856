package org.pattonvillerobotics.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.CustomRobotParameters;
import org.pattonvillerobotics.Globals;
import org.pattonvillerobotics.REVGyro;
import org.pattonvillerobotics.commoncode.enums.AllianceColor;
import org.pattonvillerobotics.commoncode.enums.Direction;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;
import org.pattonvillerobotics.commoncode.robotclasses.vuforia.VuforiaNavigation;
import org.pattonvillerobotics.mechanisms.Glyphter;

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
    private REVGyro revGyro;
    private SimpleMecanumDrive simpleMecanumDrive;

    public AutoMethods(HardwareMap hardwareMap, LinearOpMode linearOpMode, AllianceColor allianceColor) {

        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;
        this.allianceColor = allianceColor;

        vuforia.activateTracking();

    }

    public void initialize() {

        drive = new MecanumEncoderDrive(hardwareMap, linearOpMode, CustomRobotParameters.ROBOT_PARAMETERS);
        glyphter = new Glyphter(hardwareMap, drive);
        vuforia = new VuforiaNavigation(CustomRobotParameters.VUFORIA_PARAMETERS);
        simpleMecanumDrive = new SimpleMecanumDrive(linearOpMode, hardwareMap);

    }


    public void driveToColumn() {


        Direction direction = allianceColor == AllianceColor.BLUE ? Direction.RIGHT : Direction.LEFT;

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

    public void runAutonomousProcess() {

        linearOpMode.telemetry.addData(TAG, allianceColor +  " autonomous initialized!");

    }
    private void driveOffBalancingStone (){
        while (revGyro.getRoll() != 0 && revGyro.getPitch() != 0) {
            if (allianceColor == AllianceColor.BLUE) {
                simpleMecanumDrive.moveFreely(0,0.5,0);
            }else {
                simpleMecanumDrive.moveFreely(180,0.5,0);
            }
        }
    }

}
