package org.pattonvillerobotics.robotClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class shovelmechanism {
    private DcMotor baseMotor;
    private Servo elbow;
    public Servo wrist;

    public shovelmechanism (HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        baseMotor = hardwareMap.get("baseMotor");

    }
        public void 
}
