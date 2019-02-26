package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;
import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.opmodes.OpenCVTest;
import org.pattonvillerobotics.commoncode.robotclasses.drive.MecanumEncoderDrive;
import org.pattonvillerobotics.commoncode.robotclasses.drive.RobotParameters;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleMecanumDrive;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.GamepadData;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableButton;
import org.pattonvillerobotics.commoncode.robotclasses.gamepad.ListenableGamepad;
import org.pattonvillerobotics.commoncode.robotclasses.opencv.roverruckus.minerals.MineralDetector;
import org.pattonvillerobotics.robotclasses.mechanisms.HookLiftingMechanism;
import org.pattonvillerobotics.robotclasses.mechanisms.ShovelMechanism;
import org.pattonvillerobotics.robotclasses.mechanisms.TeamMarkerMechanism;
import org.pattonvillerobotics.robotclasses.misc.CommonMethods;
import org.pattonvillerobotics.robotclasses.misc.CustomizedRobotParameters;

@TeleOp(name = "MainTeleOp", group = OpModeGroups.MAIN)
public class MainTeleOp extends LinearOpMode {


    private SimpleMecanumDrive drive;
    private ListenableGamepad driveGamepad, armGamepad;
//    private HookLiftingMechanism hookLiftingMechanism;
    private BNO055IMU imu;
    private boolean orientedDriveMode = false, slowDrive = false;
    private RobotParameters robotParameters = CustomizedRobotParameters.ROBOT_PARAMETERS;
    private CommonMethods runner;
//    private ShovelMechanism shovelArm;
//    private TeamMarkerMechanism teamMarkerMechanism;

    @Override
    public void runOpMode() {
        Vector2D polarCoordinates;
        Orientation angles;

        initialize();
        waitForStart();

//        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        while (opModeIsActive()) {
            polarCoordinates = SimpleMecanumDrive.toPolar(-gamepad1.left_stick_x, gamepad1.left_stick_y);
            angles = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
            driveGamepad.update(gamepad1);
//            armGamepad.update(gamepad2);

            if(!slowDrive) {
                drive.moveFreely(polarCoordinates.getY() - (orientedDriveMode ? angles.secondAngle + (Math.PI / 2.) : 0), -polarCoordinates.getX()*3, -gamepad1.right_stick_x);
            } else {
                drive.moveFreely(polarCoordinates.getY() - (orientedDriveMode ? angles.secondAngle + (Math.PI / 2.) : 0), -polarCoordinates.getX()/2, -gamepad1.right_stick_x);
            }
//            hookLiftingMechanism.move(gamepad2.right_trigger - gamepad2.left_trigger);
//            telemetry.update();
        }
    }


    private void initialize() {
        drive = new SimpleMecanumDrive(this, hardwareMap);
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();
        imu.initialize(parameters);
//        hookLiftingMechanism = new HookLiftingMechanism(this, hardwareMap);
        driveGamepad = new ListenableGamepad();
        armGamepad = new ListenableGamepad();
        drive.leftRearMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        drive.rightRearMotor.setDirection(DcMotorSimple.Direction.FORWARD);
        drive.leftDriveMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        drive.rightDriveMotor.setDirection(DcMotorSimple.Direction.FORWARD);
//        teamMarkerMechanism = new TeamMarkerMechanism(hardwareMap, this);
        driveGamepad.addButtonListener(GamepadData.Button.Y, ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                if(slowDrive){
                    slowDrive = false;
                    telemetry.addData("[MainTeleop]","Slow drive deactivated!");
                } else {
                    slowDrive = true;
                    telemetry.addData("[MainTeleop]", "Slow drive activated!");
                }
            }
        });
//        armGamepad.addButtonListener(GamepadData.Button.STICK_BUTTON_LEFT, ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
//            @Override
//            public void run() {
////                shovelArm.moveBaseArm(0.5);
////                shovelArm.moveElbow_servo(0.5);
//
//            }
//        });
//        armGamepad.addButtonListener(GamepadData.Button.STICK_BUTTON_RIGHT, ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
//            @Override
//            public void run() {
//                shovelArm.moveWrist_servo(0.5);
//            }
//        });
//        driveGamepad.addButtonListener(GamepadData.Button.DPAD_UP, ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
//            @Override
//            public void run() {
//                teamMarkerMechanism.move(teamMarkerMechanism.servo.getPosition()+0.1);
//                telemetry.addData("Teleop: ", teamMarkerMechanism.servo.getPosition() + "");
//            }
//        });
//        driveGamepad.addButtonListener(GamepadData.Button.DPAD_UP, ListenableButton.ButtonState.JUST_PRESSED, new ListenableButton.ButtonListener() {
//            @Override
//            public void run() {
//                teamMarkerMechanism.move(teamMarkerMechanism.servo.getPosition()-0.1);
//                telemetry.addData("Teleop: ", teamMarkerMechanism.servo.getPosition() + "");
//            }
//        });
    }




}
