package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

// Removed the extended LinearOpMode
public class IntakeSubsystem {
    //Equation for finding 160 degrees: 0.88889*X and X = what position the servo is in when it is 180 degrees.
    //servoName.setPosition(0.88889);

    public Servo intakeArm; // GoBilda
    public DcMotor intakeWheel; // REV Hex Core
    TouchSensor intakeTouch;

    /* Motor and Servo Positions    */

    // TODO
    /* Position to pick up pieces (for picking up pieces) and releasing */
    public static final double ARM_POSE_DOWN = 0.22; //
    public static final double ARM_POSE_UP = 0.75; //

    /*
    POSE    | Comments
    ------- | --------------
    0.00    | Too far down
    0.10    |
    0.20    | Maybe too far down
    0.22    | Close enough for now
    0.25    | Not far down enough
    0.30    |
    0.40    | Not down enough
    0.50    |
    0.60    | Not up enough towards bucket
    0.70    | Close, but a little more
    0.75    | Seems about right
    0.80    |
    0.90    |
    1.00    | Too far up

    * */

    /* The intake wheel power for picking up and releasing pieces */
    public static final double WHEEL_INTAKE = 1.0;
    public static final double WHEEL_RELEASE = -1.0;

    public IntakeSubsystem(HardwareMap hardwareMap) {

        //        intakeTouch = hardwareMap.get(TouchSensor.class, "intakeTouch");
        intakeArm = hardwareMap.servo.get("intakeArm");
        intakeWheel = hardwareMap.get(DcMotor.class,"intakeWheel");

        intakeWheel.setDirection(DcMotor.Direction.REVERSE);

    }

    // TODO Test Intake Wheel Power
    /* Use for turning on the Intake Wheel */
    public void powerIntakeWheel(double power) {

        intakeWheel.setPower(power);
    }

    // TODO Determine the intake arm servo position
    /* Set Intake Arm to intake position */
    public void setIntakeArm(double position) {

        intakeArm.setPosition(position);
    }

    // TODO Implement a group command for intake of pieces
    /* Set all parameters for the intake position
     * Rotate arm into the intake (down) position
     * Power the wheel for intake
     * */
    public void groupIntakePosition() {
        // Assume or check that slides are at "out" position
        intakeArm.setPosition(ARM_POSE_DOWN);
        intakeWheel.setPower(WHEEL_INTAKE);

    }

    // TODO Implement a group command for release of pieces
    // Set all parameters for the release position
    public void groupReleasePosition() {
        intakeArm.setPosition(ARM_POSE_UP);
        intakeWheel.setPower(WHEEL_RELEASE);

    }

    // A method that increments a servo position by a given amount (0.05) with every button press
    public void incrementIntakeArm(double turd) {
        // take current
    }

} // End of IntakeSubsystem class
