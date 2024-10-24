package org.firstinspires.ftc.teamcode.sensors;


import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class someTouchSensor {

    public TouchSensor touchOne;

    // Same name as the class
    public someTouchSensor(HardwareMap hardwareMap) {

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