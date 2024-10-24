package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class SomeDistanceSensor {
    // Note: You cannot use DISTANCE SENSOR as a class because FTC already has a class w/ that name
    // declare distanceSensor
    DistanceSensor distanceSensor;

    public SomeDistanceSensor(HardwareMap hardwareMap) {
        distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");
    }

    public double getDistance() {
        return distanceSensor.getDistance(DistanceUnit.CM);
    }

    public double getDistanceInches() {
        return distanceSensor.getDistance(DistanceUnit.INCH);
    }
    

}
