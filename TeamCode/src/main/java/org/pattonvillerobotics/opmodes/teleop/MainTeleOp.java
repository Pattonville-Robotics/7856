package org.pattonvillerobotics.opmodes.teleop;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.apache.commons.math3.geometry.euclidean.twod.Vector2D;
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
import org.pattonvillerobotics.robotclasses.misc.CommonMethods;
import org.pattonvillerobotics.robotclasses.misc.CustomizedRobotParameters;

@TeleOp(name = "MainTeleOp", group = OpModeGroups.MAIN)
public class MainTeleOp extends LinearOpMode {


    private MecanumEncoderDrive drive;
    private ListenableGamepad driveGamepad, armGamepad;
    private HookLiftingMechanism hookLiftingMechanism;
    private BNO055IMU imu;
    private boolean orientedDriveMode;
    private CommonMethods runner;
    private ShovelMechanism shovelArm;
    private RobotParameters robotParameters = CustomizedRobotParameters.ROBOT_PARAMETERS;

    @Override
    public void runOpMode() {
        Vector2D polarCoordinates;
        Orientation angles;

        initialize();
        waitForStart();

        imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);

        while (opModeIsActive()) {
            polarCoordinates = SimpleMecanumDrive.toPolar(gamepad1.left_stick_x, -gamepad1.left_stick_y);
            angles = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);

            driveGamepad.update(new GamepadData(gamepad1));
            armGamepad.update(new GamepadData(gamepad2));




            drive.moveFreely(polarCoordinates.getY() - (orientedDriveMode ? angles.secondAngle + (Math.PI / 2.) : 0), polarCoordinates.getX(), -gamepad1.right_stick_x);

            hookLiftingMechanism.move(gamepad1.right_trigger - gamepad1.left_trigger);
        }
        armGamepad.addButtonListener(GamepadData.Button.STICK_BUTTON_LEFT, ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                shovelArm.moveBaseArm(0.5);
                shovelArm.moveElbow_servo(0.5);

            }
        });
        armGamepad.addButtonListener(GamepadData.Button.STICK_BUTTON_RIGHT, ListenableButton.ButtonState.BEING_PRESSED, new ListenableButton.ButtonListener() {
            @Override
            public void run() {
                shovelArm.moveWrist_servo(0.5);
            }
        });

    }


    private void initialize() {

        drive = new MecanumEncoderDrive(hardwareMap, this, CustomizedRobotParameters.ROBOT_PARAMETERS);
        imu = hardwareMap.get(BNO055IMU.class, "imu");
        hookLiftingMechanism = new HookLiftingMechanism(this, hardwareMap);
        driveGamepad = new ListenableGamepad();
        armGamepad = new ListenableGamepad();
        driveGamepad.addButtonListener(GamepadData.Button.STICK_BUTTON_LEFT, ListenableButton.ButtonState.JUST_PRESSED, () -> orientedDriveMode = !orientedDriveMode);
        runner = new CommonMethods(hardwareMap, this, drive, hookLiftingMechanism, imu);
        runner.initTeleop();
        drive = new MecanumEncoderDrive(hardwareMap, this, robotParameters);


    }




}
