package org.firstinspires.ftc.teamcode.sensors;

import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Sensors {

    public TouchSensor slideTouch;

    public DistanceSensor intakeSensor;

    public Sensors(HardwareMap hardwareMap) {

        slideTouch = hardwareMap.get(TouchSensor.class,"slideTouch");

        intakeSensor = hardwareMap.get(DistanceSensor.class,"intakeSensor");
    }

    public boolean isSlideTouchPressed() {

        return slideTouch.isPressed();
    }

    public static final class IntakeThresholds {
        public static final double SAMPLE_DISTANCE = 3.8;  // Moved from IntakeSubsystem
    }

    public enum SampleStatus {
        SAMPLE_GRABBED("Sample detected"),
        NO_SAMPLE("No sample detected"),
        ERROR("Sensor error");

        private final String description;
        SampleStatus(String description) {
            this.description = description;
        }
        public String getDescription() { return description; }
    }

    public SampleStatus getSampleStatus() {
        try {
            if (intakeSensor.getDistance(DistanceUnit.CM) <= IntakeThresholds.SAMPLE_DISTANCE) {
                return SampleStatus.SAMPLE_GRABBED;
            } else {
                return SampleStatus.NO_SAMPLE;
            }
        } catch (Exception e) {
            return SampleStatus.ERROR;
        }
    }

    public boolean isSamplePresent() {
        return getSampleStatus() == SampleStatus.SAMPLE_GRABBED;
    }


} // end of class