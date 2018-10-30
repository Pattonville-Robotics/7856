package org.pattonvillerobotics.opmodes.autonomous.autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.commoncode.opmodes.OpModeGroups;
import org.pattonvillerobotics.commoncode.robotclasses.drive.SimpleDrive;
import org.pattonvillerobotics.opmodes.autonomous.robotclasses.HookLiftingMechanism;

@Autonomous (name = "TestAutonomous", group = OpModeGroups.TESTING)
public class TestAutonomous extends LinearOpMode {
    public SimpleDrive simpleDrive;
    public HookLiftingMechanism hooklifter;
    public AbstractMechanism(HardwareMap hardwareMap, LinearOpMode linearOpMode){
        this.hardwareMap = hardwareMap;
        this.linearOpMode = linearOpMode;


    @Override
    public void runOpMode() {
        initialize();

        waitForStart();



        telemetry.addData("hook position", hooklifter);

    }
    public void initialize() {
        simpleDrive = new SimpleDrive(this, hardwareMap);

        rateOfRotation = gyro.getRotation();

            simpleDrive.leftDriveMotor(-50)
            simpleDrive.rightDriveMotor(50)

        motor[motorD] = 50;
        motor[motorE] = 50;
        wait1Msec(30);

        motor[motorD] = 0;
        motor[motorE] = 0;

        drive = new SimpleDrive(this, hardwareMap);

        hooklifter.lower(30);




    }

}
