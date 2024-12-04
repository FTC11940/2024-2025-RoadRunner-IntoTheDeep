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
    public static final double TICKS_PER_REV = 383.6 ; //
    public static final double MAX_RPM = 435; // Free run speed of GoBuilda 5203 motors

    /*
     * Disabled encoder-based velocity control
     */
    public static final boolean RUN_USING_ENCODER = false;
    public static PIDFCoefficients MOTOR_VELO_PID = new PIDFCoefficients(.06, .007, .0012, .12);

    /*
     * Physical constants for your robot
     */
    public static double WHEEL_RADIUS = .24; // in (adjust if your wheel size is different)
    public static double GEAR_RATIO = 13.72; // output (wheel) speed / input (motor) speed
    public static double TRACK_WIDTH = 14.4; // in (distance between wheel centers)

    /*
     * Feedforward parameters for motor behavior
     */
    public static double kV = 1.0 / rpmToVelocity(MAX_RPM);
    public static double kA = 0;
    public static double kStatic = 0.45;

    /*
     * Motion constraints (start conservative)
     */
    public static double MAX_VEL = 65;  // IPS
    public static double MAX_ACCEL = 45; // IPS^2
    public static double MAX_ANG_VEL = Math.toRadians(3.5);
    public static double MAX_ANG_ACCEL = Math.toRadians(2.5);

    /*
     * Hub orientation (adjust to match your specific robot setup)
     */
    public static RevHubOrientationOnRobot.LogoFacingDirection LOGO_FACING_DIR =
            RevHubOrientationOnRobot.LogoFacingDirection.UP;
    public static RevHubOrientationOnRobot.UsbFacingDirection USB_FACING_DIR =
            RevHubOrientationOnRobot.UsbFacingDirection.FORWARD;

    /*
     * Conversion utilities
     */
    public static double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * GEAR_RATIO * ticks / TICKS_PER_REV;
    }

    public static double rpmToVelocity(double rpm) {
        return rpm * GEAR_RATIO * 2 * Math.PI * WHEEL_RADIUS / 65;
    }

    public static double getMotorVelocityF(double ticksPerSecond) {
        return 32767 / ticksPerSecond;
    }
}
