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

    Servo intakeArmServo;
    DcMotor intakeWheel;
    TouchSensor intakeTouch;

    /* Motor and Servo Positions    */

    /* Position to pick up pieces (for picking up pieces) and releasing */
    public static final double ARM_POSE_INTAKE = 0.05;
    public static final double ARM_POSE_RELEASE = 0.50;

    /* The intake wheel power for picking up and releasing pieces */
    public static final double WHEEL_INTAKE = 1.0;
    public static final double WHEEL_RELEASE = -1.0;

    public IntakeSubsystem(HardwareMap hardwareMap) {

        //        intakeTouch = hardwareMap.get(TouchSensor.class, "intakeTouch");

        intakeArmServo = hardwareMap.servo.get("intakeArmServo");
        intakeWheel = hardwareMap.get(DcMotor.class, "intakeWheel");
        intakeWheel.setDirection(DcMotor.Direction.REVERSE);

    }

    // TODO Test with low power
    /* Use for turning on the Intake Wheel */
    public void powerIntakeWheel(double power) {

        intakeWheel.setPower(power);
    }

    // TODO
    /* Set Intake Arm to intake position */
    public void setIntakeArmPosition(double position) {

        intakeArmServo.setPosition(position);
    }

    // TODO Test
    /* Set all parameters for the intake position
    * Rotate arm into the intake (down) position
    * Power the wheel for intake
    * */
    public void groupIntakePosition() {
        // Assume or check that slides are at "out" position
        intakeArmServo.setPosition(ARM_POSE_INTAKE);
        intakeWheel.setPower(WHEEL_INTAKE);

    }

    // TODO Test
    // Set all parameters for the release position
    public void groupReleasePosition() {
        intakeArmServo.setPosition(ARM_POSE_RELEASE);
        intakeWheel.setPower(WHEEL_RELEASE);

    }



} // End of IntakeSubsystem class
