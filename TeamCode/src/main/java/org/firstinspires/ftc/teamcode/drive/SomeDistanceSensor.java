package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class SomeDistanceSensor {
    // declare distanceSensor
    DistanceSensor distanceSensor;

    // constructor
    public SomeDistanceSensor(HardwareMap hardwareMap) {
        distanceSensor = hardwareMap.get(DistanceSensor.class, "distanceSensor");
   } // end of constructor
   
    // getDistance method
    public double getDistance() {
        return distanceSensor.getDistance(DistanceUnit.INCH);
    }

} // end of class