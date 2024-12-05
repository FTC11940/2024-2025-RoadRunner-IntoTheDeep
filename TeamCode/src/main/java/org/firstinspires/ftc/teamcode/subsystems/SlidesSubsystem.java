package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.sensors.Sensors;

public class SlidesSubsystem {

    private static final float TRIGGER_THRESHOLD = 0.1f;
    public final DcMotorEx slide;
    private final TouchSensor slideTouch;
    private final ElapsedTime delayTimer;

    public SlidesSubsystem(HardwareMap hardwareMap,Sensors sensors) {
        slide = hardwareMap.get(DcMotorEx.class,"slide");
        slideTouch = hardwareMap.get(TouchSensor.class,"slideTouch");
        delayTimer = new ElapsedTime();

        slide.setDirection(DcMotor.Direction.REVERSE);
        slide.setPower(0);
    }

    public void setSlidePose(int position, double power) {
        slide.setTargetPosition(position);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setPower(power);
    }

    public void setSlideOut(double power) {
        slide.setTargetPosition(Constants.SLIDE_OUT_POSE);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setPower(power);
    }

    public void setSlideIn(double power) {
        slide.setTargetPosition(Constants.SLIDE_IN_POSE);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setPower(power);
    }

    public void stopSlides() {
        slide.setPower(0);
    }


    public void powerSlide(double power) {
        slide.setPower(power);
    }

    public void resetSlideEncoder() {
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleepy(Constants.RESET_DELAY);
        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void sleepy(double seconds) {
        delayTimer.reset();
        while (delayTimer.seconds() < seconds) {
        }
    }

    public void controlSlides(Gamepad gamepad) {
        if (gamepad.left_trigger > TRIGGER_THRESHOLD) {
            // Map trigger input from 0.5 to 1.0 power for smoother control
            double power = mapTriggerToPower(gamepad.left_trigger);
            setSlideOut(power);
        } else if (gamepad.right_trigger > TRIGGER_THRESHOLD) {
            double power = mapTriggerToPower(gamepad.right_trigger);
            setSlideIn(power);
        } else {
            stopSlides();
        }
    }

    // Maps trigger input (0.1 to 1.0) to power range (0.5 to 1.0)
    private double mapTriggerToPower(double triggerValue) {
        // Ensure minimum power of 0.5, scaling up to 1.0 based on trigger pressure
        return 0.5 + (triggerValue * 0.5);
    }

    public SlideStatus getSlideStatus() {
        int slidePosition = slide.getCurrentPosition();
        double tolerance = Constants.POSITION_TOLERANCE;

        if (Math.abs(slidePosition - Constants.SLIDE_IN_POSE) <= tolerance * Constants.SLIDE_IN_POSE) {
            return SlideStatus.SLIDES_IN;
        } else if (Math.abs(slidePosition - Constants.SLIDE_OUT_POSE) <= tolerance * Constants.SLIDE_OUT_POSE) {
            return SlideStatus.SLIDES_OUT;
        } else {
            return SlideStatus.UNKNOWN;
        }
    }

    public void resetSlideEncoderOnTouch() {
        if (slideTouch.isPressed()) {
            slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    public enum SlideStatus {
        SLIDES_IN,SLIDES_OUT,UNKNOWN
    }

    public static class Constants {
        // Motor positions
        public static final int SLIDE_OUT_POSE = 800; // increase from 500
        public static final int SLIDE_IN_POSE = 0;

        // Motor settings
        public static final double POSITION_TOLERANCE = 0.05;

        // Timing
        public static final double RESET_DELAY = 0.1;
    }
}