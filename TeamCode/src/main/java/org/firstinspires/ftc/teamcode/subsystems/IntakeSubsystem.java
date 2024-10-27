package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
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
    public static final double INTAKE_SERVO_POSITION = 0;

    // Position to drop pieces into the bucket (for scoring in the baskets) about 160 degrees
    public static final double RELEASE_SERVO_POSITION = 0.888889;

    // The intake (For picking up pieces using the wheel)
    public static final double INTAKE_MOTOR_POWER = 1;

    // The release (for dropping pieces into the observation zone (specimen zone))
    public static final double INTAKE_RELEASE_MOTOR_POWER = -1;


    //@Override
    public void runOpMode() {

        intakeArmServo = hardwareMap.servo.get("intakeArmServo");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeTouch = hardwareMap.get(TouchSensor.class, "intakeTouch");

    }

    // Set all parameters for the intake position
    public void setIntakePosition() {
        intakeArmServo.setPosition(INTAKE_SERVO_POSITION);
        intakeMotor.setPower(INTAKE_MOTOR_POWER);
    }

    // Set all parameters for the release position
    public void setReleasePosition() {
        intakeArmServo.setPosition(RELEASE_SERVO_POSITION);
        // intakeMotor.setPower(INTAKE_RELEASE_MOTOR_POWER);
    }

} // End of IntakeSubsystem class
