package org.pattonvillerobotics.robotclasses.mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by pieperm on 10/20/16.
 */

public class Hopper extends AbstractMechanism {

    private DcMotor hopper;
    private Hopper.Direction direction;
    private boolean activated;

    public Hopper(HardwareMap hardwareMap, LinearOpMode linearOpMode) {
        super(hardwareMap, linearOpMode);
        hopper = hardwareMap.dcMotor.get("hopper");
        direction = Hopper.Direction.OUT;
    }

    public void setDirection(Hopper.Direction direction) {
        this.direction = direction;
        if (activated) activate();
    }

    public void activate() {
        switch (direction) {
            case IN:
                hopper.setPower(-.8);
                break;
            case OUT:
                hopper.setPower(.8);
                break;
        }
        activated = true;
    }

    public void deactivate() {
        hopper.setPower(0);
        activated = false;
    }

    public enum Direction {IN, OUT}
}