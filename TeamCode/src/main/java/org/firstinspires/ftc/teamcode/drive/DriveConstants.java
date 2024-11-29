package org.firstinspires.ftc.teamcode.drive;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.PIDFCoefficients;

/*
 * Constants shared between multiple drive types.
 *
 * Configured for:
 * - GoBuilda 5203 Motors
 * - No encoder velocity control
 * - Track width: 14.4 inches
 */
@Config
public class DriveConstants {


    /*
     * Motor constants for GoBuilda 5203 motors
     */
    public static final double TICKS_PER_REV = 28; // Core Hex Motor encoder ticks
    public static final double MAX_RPM = 312; // Free run speed of GoBuilda 5203 motors

    /*
     * Disabled encoder-based velocity control
     */
    public static final boolean RUN_USING_ENCODER = false;
    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(0, 0, 0, 0);

    /*
     * Physical constants for your robot
     */
    public static double WHEEL_RADIUS = 2; // in (adjust if your wheel size is different)
    public static double GEAR_RATIO = 1; // output (wheel) speed / input (motor) speed
    public static double TRACK_WIDTH = 14.4; // in (distance between wheel centers)

    /*
     * Feedforward parameters for motor behavior
     */
    public static double kV = 1.0 / rpmToVelocity(MAX_RPM);
    public static double kA = 0;
    public static double kStatic = 0;

    /*
     * Motion constraints (start conservative)
     */
    public static double MAX_VEL = 30;
    public static double MAX_ACCEL = 30;
    public static double MAX_ANG_VEL = Math.toRadians(60);
    public static double MAX_ANG_ACCEL = Math.toRadians(60);

    /*
     * Hub orientation (adjust to match your specific robot setup)
     */
    public static RevHubOrientationOnRobot.LogoFacingDirection LOGO_FACING_DIR =
            RevHubOrientationOnRobot.LogoFacingDirection.UP;
    public static RevHubOrientationOnRobot.UsbFacingDirection USB_FACING_DIR =
            RevHubOrientationOnRobot.UsbFacingDirection.RIGHT;

    /*
     * Conversion utilities
     */
    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;

    // Motor Configuration for GoBuilda 5203 Yellow Jacket Motors
    public static final double TICKS_PER_REV = 0; // No encoders
    public static final double MAX_RPM = 435; // For 13.7:1 gear ratio
    
    // Drive Train Geometric Constants
    public static double TRACK_WIDTH = 14.375; // inches - CRITICAL TO MEASURE PRECISELY
    public static double WHEEL_RADIUS = 2.0; // inches - wheel diameter / 2
    
    // Motor Power and Movement Constants
    public static final double NORMAL_DRIVE_POWER;

    static {
        NORMAL_DRIVE_POWER = 0.7;
    }

    public static final double MAX_DRIVE_POWER;

    static {
        MAX_DRIVE_POWER = 1.0;
    }

    // Feedforward Constants (Without Encoders)
    public static double kV = 1.0 / (MAX_RPM / 60.0); // Theoretical velocity constant
    public static double kA = 0.002; // Acceleration constant
    public static double kStatic = 0.1; // Static friction compensation
    
    // Turn PID Constants
    public static PIDFCoefficients TURN_PIDF;

    static {
        TURN_PIDF = new PIDFCoefficients(
                0.01,  // Proportional
                0.001, // Integral
                0.0005, // Derivative
                0.0    // Feed Forward
        );
    }

    // Custom Telemetry and Debugging
    public static boolean TELEMETRY_ENABLED;

    static {
        TELEMETRY_ENABLED = true;
    }

    // IMU Orientation (Adjust based on your hub mounting)
    public static RevHubOrientationOnRobot.LogoFacingDirection LOGO_DIRECTION;

    static {
        LOGO_DIRECTION = RevHubOrientationOnRobot.LogoFacingDirection.UP;
    }

    public static RevHubOrientationOnRobot.UsbFacingDirection USB_DIRECTION;

    static {
        USB_DIRECTION = RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;
    }

    // Tuning Parameters
    public static class Params {
        public static double HEADING_P;

        static {
            HEADING_P = 0.03;
        }

        public static double HEADING_I;

        static {
            HEADING_I = 0.001;
        }

        public static double HEADING_D;

        static {
            HEADING_D = 0.002;
        }

        // Lateral Movement Tuning
        public static double LATERAL_MULTIPLIER;

        static {
            LATERAL_MULTIPLIER = 1.0;
        }
    }

    public static double rpmToVelocity(double rpm) {
        return rpm * GEAR_RATIO * 2 * Math.PI * WHEEL_RADIUS / 60.0;
    }

    public static double getMotorVelocityF(double ticksPerSecond) {
        return 32767 / ticksPerSecond;
    }
}