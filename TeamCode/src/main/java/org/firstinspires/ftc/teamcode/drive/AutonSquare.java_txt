package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class AutonSquare {
    public static void runAuton(LinearOpMode linearOpMode) {
        linearOpMode.waitForStart();

        // Initialize hardware map
        HardwareMap hardwareMap = linearOpMode.hardwareMap;

        // Initialize drive
        Drive drive = new Drive(hardwareMap);

        // Initialize variables
        double squareSize = 12.0; // Size of the square in inches
        double speed = 0.5; // Speed of the robot

        // Drive forward
        drive.forward(squareSize);

    }
}
