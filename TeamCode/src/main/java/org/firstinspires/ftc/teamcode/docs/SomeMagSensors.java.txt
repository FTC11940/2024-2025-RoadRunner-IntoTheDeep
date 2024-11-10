package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.hardware.HardwareMap;
// import REV MagSensor
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class SomeMagSensors {
    TouchSensor pseudoMag;

    public SomeMagSensors(HardwareMap hardwareMap) {
        pseudoMag = hardwareMap.get(TouchSensor.class, "magOne");
    }


    // Modified method to use the class member instead of accessing hardwareMap
    public boolean magStatus() {

        // Use the touchOne object initialized in the constructor
        return pseudoMag.isPressed();
    }
}
