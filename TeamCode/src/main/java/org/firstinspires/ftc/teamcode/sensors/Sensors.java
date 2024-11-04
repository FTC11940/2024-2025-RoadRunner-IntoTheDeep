package org.firstinspires.ftc.teamcode.sensors;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class Sensors {

    // TODO Add slideTouch to Robot Configuration
    public TouchSensor slideTouch;

    public Sensors(HardwareMap hardwareMap) {
        slideTouch = hardwareMap.get(TouchSensor.class,"slideTouch");
    }

    // TODO Test boolean method to check if the slide touch sensor is pressed
    public boolean isSlideTouchPressed() {

        return slideTouch.isPressed();
    }

}
