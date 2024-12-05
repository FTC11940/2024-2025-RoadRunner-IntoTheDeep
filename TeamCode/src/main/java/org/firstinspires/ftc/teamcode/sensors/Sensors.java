package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Sensors {

    public TouchSensor slideTouch;
    public TouchSensor liftTouch;
    public DistanceSensor intakeSensor;
    private ElapsedTime sampleTimer;
    private boolean isTimerStarted;

    public Sensors(HardwareMap hardwareMap) {

        slideTouch = hardwareMap.get(TouchSensor.class,"slideTouch");
        intakeSensor = hardwareMap.get(DistanceSensor.class,"intakeSensor");
        liftTouch = hardwareMap.get(TouchSensor.class,"liftTouch");
        sampleTimer = new ElapsedTime();
        isTimerStarted = false;
    }

    public boolean isSlideTouchPressed() {

        return slideTouch.isPressed();
    }

    public boolean isLiftTouchPressed() {

        return liftTouch.isPressed();
    }

    public SampleStatus getSampleStatus() {
        try {
            boolean currentlyDetected = intakeSensor.getDistance(DistanceUnit.CM) <= Constants.SAMPLE_DISTANCE;

            if (currentlyDetected) {
                if (!isTimerStarted) {
                    sampleTimer.reset();
                    isTimerStarted = true;
                }
                if (sampleTimer.seconds() < Constants.SAMPLE_CONFIRM_TIME) {
                    return SampleStatus.SAMPLE_DETECTED;
                }
                if (sampleTimer.seconds() >= Constants.SAMPLE_CONFIRM_TIME) {
                    return SampleStatus.SAMPLE_GRABBED;
                }
            } else {
                isTimerStarted = false;
            }

            return SampleStatus.NO_SAMPLE;
        } catch (Exception e) {
            return SampleStatus.ERROR;
        }
    }

    public enum SampleStatus {
        SAMPLE_DETECTED("Sample detected"),
        SAMPLE_GRABBED("Sample grabbed"),
        NO_SAMPLE("No sample detected"),
        ERROR("Sensor error");

        private final String description;

        SampleStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public static final class Constants {
        public static final double SAMPLE_DISTANCE = 3.8;
        public static final double SAMPLE_CONFIRM_TIME = 0.5; // 500 ms
    }


} // end of class