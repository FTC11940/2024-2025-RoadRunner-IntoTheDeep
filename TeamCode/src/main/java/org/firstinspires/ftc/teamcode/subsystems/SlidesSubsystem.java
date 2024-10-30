package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class SlidesSubsystem {


    private final DcMotor somemotor;

    public SlidesSubsystem(HardwareMap hardwaremap) {
        somemotor = hardwaremap.get(DcMotor.class, "leftback");
        somemotor.setDirection(DcMotor.Direction.FORWARD);
        somemotor.setPower(0);

    }

    // TODO Create a method to move the slides to different positions
    public void rotatemotorreverse(double power) {
        somemotor.setPower(-power);
    }


    public void stopmotor() {
        somemotor(0);
    }

    private void somemotor(int i) {

    }


    public void incrementmotor(double rotsomedegrees) {

    }


} //end of class