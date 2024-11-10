package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;


public class SlidesSubsystem {

    public final DcMotorEx slide;
    public final double POWER_INCREMENT = 0.1;

    /*  TODO Determine and Define the slide positions. */
    public static final int SLIDE_OUT_POSE = 300;
    public static final int SLIDE_IN_POSE = 0;

    /* Constructor for the SlidesSubsystem class */
    public SlidesSubsystem(HardwareMap hardwaremap) {
        slide = hardwaremap.get(DcMotorEx.class,"slide");
        slide.setDirection(DcMotor.Direction.REVERSE);
        slide.setPower(0);

    }

    /*  Move the slides to set positions to be determined with set points defined with variables */
    public void setSlidePose(int position) {
        slide.setTargetPosition(position);

        // Set run mode to RUN_TO_POSITION
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        slide.setPower(0.5);
    }

    /* Method to move a motor incrementally while a button is held */
    public void powerSlide(double power) {

        slide.setPower(power);
    }

    public void stopMotor(int power) {

        slide.setPower(0);
    }

    // TODO Test the reset of slide encoder
    /* Reset the slide motor encoder */
    public void resetSlideEncoder() {
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleepy(0.1);
        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private ElapsedTime delayTimer = new ElapsedTime();

    public void sleepy(double seconds) {
        delayTimer.reset();
        while (delayTimer.seconds() < seconds) {
        }
    } // end of sleepy method

    // TODO Check the status of the slides

    public enum SlideStatus {
        SLIDES_IN,
        SLIDES_OUT,
        UNKNOWN
    }

    /* STRICT check
    public SlideStatus getSlideStatus() {
        if (slide.getCurrentPosition() == SLIDE_IN_POSE) {
            return SlideStatus.SLIDES_OUT;
        } else if (slide.getCurrentPosition() == SLIDE_OUT_POSE) {
            return SlideStatus.SLIDES_IN;
        } else {
            return SlideStatus.UNKNOWN;
        }
    }
     */


    public SlideStatus getSlideStatus() {
        int slidePosition = slide.getCurrentPosition();
        double tolerance = 0.05; // 5% tolerance

        if (Math.abs(slidePosition - SLIDE_IN_POSE) <= tolerance * SLIDE_IN_POSE) {
            return SlideStatus.SLIDES_IN; // Corrected return value
        } else if (Math.abs(slidePosition - SLIDE_OUT_POSE) <= tolerance * SLIDE_OUT_POSE) {
            return SlideStatus.SLIDES_OUT; // Corrected return value
        } else {
            return SlideStatus.UNKNOWN;
        }
    }

} //end of class