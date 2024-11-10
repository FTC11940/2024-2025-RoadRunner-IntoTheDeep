package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.sensors.Sensors;

public class SlidesSubsystem {

    public final DcMotorEx slide;
    public final double POWER_INCREMENT = 0.1;

    private Sensors sensors;

    /*  TODO Determine and Define the slide positions. */
    public static final int SLIDE_OUT_POSE = 300;
    public static final int SLIDE_IN_POSE = 0;

    /* Constructor for the SlidesSubsystem class */
    public SlidesSubsystem(HardwareMap hardwareMap, Sensors sensors) {
        slide = hardwareMap.get(DcMotorEx.class,"slide");
        slide.setDirection(DcMotor.Direction.REVERSE);
        slide.setPower(0);

        this.sensors = sensors; // Initialize the sensors

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

    /* Generic reset the slide motor encoder */
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

    /* Reset the slide motor encoder when the slide touch sensor is pressed */
    public void resetSlideEncoderOnTouch() {
        if (sensors.slideTouch.isPressed()) {
            slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

} //end of class