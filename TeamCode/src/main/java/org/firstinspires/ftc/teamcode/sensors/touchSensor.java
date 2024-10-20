package org.firstinspires.ftc.teamcode.sensors;


import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class touchSensor {

    private final TouchSensor touchOne;

    public touchSensor(HardwareMap hardwareMap) {

        touchOne = hardwareMap.get(TouchSensor.class, "touchOne");

    }


    public boolean isTouchOnePressed() {
        return hardwareMap.get(TouchSensor.class, "touchOne").isPressed();

    }

}