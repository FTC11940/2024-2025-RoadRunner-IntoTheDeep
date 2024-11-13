package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;

import com.qualcomm.robotcore.hardware.Gamepad;

public class ControllerMappings {

    Gamepad gamepad;
    Gamepad gamepad2;

    public ControllerMappings(Gamepad gamepad,Gamepad gamepad2) {
        this.gamepad = gamepad;
        this.gamepad2 = gamepad2;
    }

    /*

    public static final double driveForward = gamepad1.left_stick_y;
    public static final double driveBackward = gamepad1.left_stick_y;
    public static final driveLeft = gamepad1.left_stick_x;
    public static final driveRight = gamepad1.right_stick_x;

    public static final double slideOut = gamepad1.a;
    public static final double slideIn = gamepad1.b;
    public static final intakeArmUp = gamepad1.x;
    public static final intakeArmDown = gamepad1.y;

     */


    /*
    ## IntakeSubsystem.java
    - [ ] Power intake motor (grabber wheel) forward direction and reverse direction (no position)
                - [ ] Set arm servo to specific position for intake and for release

    ## SlidesSubsystem.java
    - [ ] Set slides motor forward to slide out to a specific position and reverse to slide in to a specific position
    - [ ] Power slides motor incrementally for testing purposes

    ## ClimbSubsystem.java
    - [ ] Power climb motor forward/up to a specific position and reverse/down to a specific position
    - [ ] Power climb motor off

    ## BucketSubsystem.java
    - [ ] Set bucket servo to a specific position for intake, release, and travel
    - [ ] Set bucket dump motor reverse direction to specific position
    */

    /*
    gamepad1.a
    gamepad1.b
    gamepad1.x
    gamepad1.y
    gamepad1.left_bumper
    gamepad1.right_bumper
    gamepad1.dpad_up
    gamepad1.dpad_down
    gamepad1.dpad_left
    gamepad1.dpad_right
    gamepad1.start
    gamepad1.back
    gamepad1.guide (Xbox controller only) Triggers
    gamepad1.left_trigger (analog value, 0.0 to 1.0)
    gamepad1.right_trigger (analog value, 0.0 to 1.0) Joysticks
    gamepad1.left_stick_x (analog value, -1.0 to 1.0)
    gamepad1.left_stick_y (analog value, -1.0 to 1.0)
    gamepad1.right_stick_x (analog value, -1.0 to 1.0)
    gamepad1.right_stick_y (analog value, -1.0 to 1.0)
     */

    /**
     * Gamepad 1 Inputs
     */// Buttons
    /**
     * Button A
     */
    boolean a = gamepad1.a;
    /**
     * Button B
     */
    boolean b = gamepad1.b;
    /**
     * Button X
     */
    boolean x = gamepad1.x;
    /**
     * Button Y
     */
    boolean y = gamepad1.y;
    /**
     * Left Bumper
     */
    boolean left_bumper = gamepad1.left_bumper;
    /**
     * Right Bumper
     */
    boolean right_bumper = gamepad1.right_bumper;
    /**
     * D-pad Up
     */
    boolean dpad_up = gamepad1.dpad_up;
    /**
     * D-pad Down
     */
    boolean dpad_down = gamepad1.dpad_down;
    /**
     * D-pad Left
     */
    boolean dpad_left = gamepad1.dpad_left;
    /**
     * D-pad Right
     */
    boolean dpad_right = gamepad1.dpad_right;
    /**
     * Start Button
     */
    boolean start = gamepad1.start;
    /**
     * Back Button
     */
    boolean back = gamepad1.back;
    /**
     * Guide Button (Xbox controller only)
     */
    boolean guide = gamepad1.guide;

// Triggers (Analog values, 0.0 to 1.0)
    /**
     * Left Trigger
     */
    double left_trigger = gamepad1.left_trigger;
    /**
     * Right Trigger
     */
    double right_trigger = gamepad1.right_trigger;

// Joysticks (Analog values, -1.0 to 1.0)
    /**
     * Left Stick X-axis
     */
    double left_stick_x = gamepad1.left_stick_x;
    /**
     * Left Stick Y-axis
     */
    double left_stick_y = gamepad1.left_stick_y;
    /**
     * Right Stick X-axis
     */
    double right_stick_x = gamepad1.right_stick_x;
    /**
     * Right Stick Y-axis
     */
    double right_stick_y = gamepad1.right_stick_y;
}
