package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

@Config
public class DriveConstants {
    // Motor Configuration for GoBuilda 5203 Yellow Jacket Motors
    public static final double TICKS_PER_REV = 0; // No encoders
    public static final double MAX_RPM = 435; // For 13.7:1 gear ratio
    
    // Drive Train Geometric Constants
    public static double TRACK_WIDTH = 14.375; // inches - CRITICAL TO MEASURE PRECISELY
    public static double WHEEL_RADIUS = 2.0; // inches - wheel diameter / 2
    
    // Motor Power and Movement Constants
    public static final double NORMAL_DRIVE_POWER = 0.7;
    public static final double MAX_DRIVE_POWER = 1.0;
    
    // Feedforward Constants (Without Encoders)
    public static double kV = 1.0 / (MAX_RPM / 60.0); // Theoretical velocity constant
    public static double kA = 0.002; // Acceleration constant
    public static double kStatic = 0.1; // Static friction compensation
    
    // Turn PID Constants
    public static PIDFCoefficients TURN_PIDF = new PIDFCoefficients(
        0.01,  // Proportional
        0.001, // Integral
        0.0005, // Derivative
        0.0    // Feed Forward
    );
    
    // Custom Telemetry and Debugging
    public static boolean TELEMETRY_ENABLED = true;
    
    // IMU Orientation (Adjust based on your hub mounting)
    public static RevHubOrientationOnRobot.LogoFacingDirection LOGO_DIRECTION =
        RevHubOrientationOnRobot.LogoFacingDirection.UP;
    public static RevHubOrientationOnRobot.UsbFacingDirection USB_DIRECTION = 
        RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
    
    // Tuning Parameters
    public static class Params {
        public static double HEADING_P = 0.03;
        public static double HEADING_I = 0.001;
        public static double HEADING_D = 0.002;
        
        // Lateral Movement Tuning
        public static double LATERAL_MULTIPLIER = 1.0;
    }
}