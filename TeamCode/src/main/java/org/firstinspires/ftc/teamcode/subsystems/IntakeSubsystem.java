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
    DcMotor intakeMotor;
    TouchSensor intakeTouch;

    /* Motor and Servo Positions    */

    // Position to pick up pieces (for picking up pieces) 0 degrees
    public static final double SERVO_POSITION_INTAKE = 0;

    // Position to drop pieces into the bucket (for scoring in the baskets) about 160 degrees
    public static final double SERVO_POSITION_RELEASE = 0.888889;

    // The intake (For picking up pieces using the wheel)
    public static final double WHEEL_POWER_INTAKE = 0.25;

    // The release (for dropping pieces into the observation zone (specimen zone))
    public static final double WHEEL_POWER_RELEASE = -0.25;

    public IntakeSubsystem(HardwareMap hardwareMap) {

        intakeArmServo = hardwareMap.servo.get("intakeArmServo");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeTouch = hardwareMap.get(TouchSensor.class, "intakeTouch");

    }

    // TODO Test with low power
    /* Use for turning on the Intake Wheel */
    public void powerIntakeWheel(double power) {
        intakeMotor.setPower(power);
    }

    // Set all parameters for the intake position
    public void groupIntakePosition() {
        intakeArmServo.setPosition(SERVO_POSITION_INTAKE);
        intakeMotor.setPower(WHEEL_POWER_INTAKE);
    }

    // Set all parameters for the release position
    public void groupReleasePosition() {
        intakeArmServo.setPosition(SERVO_POSITION_RELEASE);
        // intakeMotor.setPower(INTAKE_RELEASE_MOTOR_POWER);
    }



} // End of IntakeSubsystem class
