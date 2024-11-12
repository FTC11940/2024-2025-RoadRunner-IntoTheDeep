package org.firstinspires.ftc.teamcode.sensors;

import android.hardware.Sensor;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class Sensors {

    // TODO Add slideTouch to Robot Configuration
    public TouchSensor slideTouch;

    public DistanceSensor intakeSensor;

    public Sensors(HardwareMap hardwareMap) {

        slideTouch = hardwareMap.get(TouchSensor.class,"slideTouch");

        intakeSensor = hardwareMap.get(DistanceSensor.class,"intakeSensor");
    }

    // TODO Test boolean method to check if the slide touch sensor is pressed
    public boolean isSlideTouchPressed() {

        return slideTouch.isPressed();
    }



} // end of class