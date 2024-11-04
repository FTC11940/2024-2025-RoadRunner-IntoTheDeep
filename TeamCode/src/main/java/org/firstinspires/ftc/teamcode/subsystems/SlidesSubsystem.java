package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


public class SlidesSubsystem {

    public final DcMotorEx slideMotor;
    public final double POWER_INCREMENT = 0.1;

    /*  TODO Determine and Define the slide positions. */
    public static final int SLIDE_OUT_POSE = 500;
    public static final int SLIDE_IN_POSE = 0;

    /* Constructor for the SlidesSubsystem class */
    public SlidesSubsystem(HardwareMap hardwaremap) {
        slideMotor = hardwaremap.get(DcMotorEx.class,"slideMotor");
        slideMotor.setDirection(DcMotor.Direction.FORWARD);
        slideMotor.setPower(0);

    }

    /*  Move the slides to set positions to be determined with set points defined with variables */
    public void setSlidePose(int position) {
        slideMotor.setTargetPosition(position);

        // Set run mode to RUN_TO_POSITION
        slideMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        slideMotor.setPower(0.5);
    }

    /* Method to move a motor incrementally while a button is held */
    public void powerSlide(double power) {

        slideMotor.setPower(power);
    }

    public void stopMotor(int power) {

        slideMotor.setPower(0);
    }

    // TODO Test the reset of slide encoder
    /* Reset the slide motor encoder */
    public void resetSlideEncoder() {
        slideMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleepy(0.1);
        slideMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private ElapsedTime delayTimer = new ElapsedTime();

    public void sleepy(double seconds) {
        delayTimer.reset();
        while (delayTimer.seconds() < seconds) {
        }
    } // end of sleepy method

} //end of class