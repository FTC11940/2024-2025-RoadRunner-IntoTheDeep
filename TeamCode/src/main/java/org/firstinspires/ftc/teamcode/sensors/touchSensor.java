package org.firstinspires.ftc.teamcode.sensors;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class touchSensor {

    public TouchSensor touchOne;

    // Same name as the class
    public touchSensor(HardwareMap hardwareMap) {

        // Initialize the touchOne sensor here;
        // DeviceName needs to match name and location in hub (digital 1)
        touchOne = hardwareMap.get(TouchSensor.class, "touchOne");

    }

    // Modified method to use the class member instead of accessing hardwareMap
    public boolean isTouchOnePressed() {

        // Use the touchOne object initialized in the constructor
        return touchOne.isPressed();
    }

} // end of class