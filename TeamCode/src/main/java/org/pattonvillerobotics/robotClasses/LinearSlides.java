package org.pattonvillerobotics.robotClasses;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.pattonvillerobotics.robotClasses.AbstractMechanism;

public class LinearSlides extends AbstractMechanism{
    public DcMotor forwardMotor;
    public DcMotor backwardMotor;


    public LinearSlides(HardwareMap hardwareMap, LinearOpMode linearOpMode){
        super(hardwareMap, linearOpMode);
    }
}
