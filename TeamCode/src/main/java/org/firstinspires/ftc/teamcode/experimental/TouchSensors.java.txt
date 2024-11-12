package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;


public class TouchSensors {
    public TouchSensor touchOne;

    public TouchSensors(HardwareMap hardwareMap) {
        touchOne = hardwareMap.get(TouchSensor.class, "touchOne");
    }

    public boolean isTouchOnePressed() {

        return touchOne.isPressed();
    }

}