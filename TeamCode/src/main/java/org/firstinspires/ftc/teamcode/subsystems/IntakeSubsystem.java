package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.sensors.Sensors;

public class IntakeSubsystem {

    public Servo intakeArm; // GoBilda
    public DcMotor intakeWheel; // REV Hex Core
    TouchSensor intakeTouch;
//    private final BucketSubsystem bucketSub;
    private final Sensors sensors;

    /* Motor and Servo Positions    */
    /* Position to pick up pieces (for picking up pieces) and releasing */
    public static final double ARM_POSE_DOWN = 0.20; //
    public static final double ARM_POSE_UP = 0.75; //

    /* The intake wheel power for picking up and releasing pieces */
    public static final double WHEEL_INTAKE = 1.0;
    public static final double WHEEL_RELEASE = -1.0;

    /* Distance when sensor mounted on side */
    public double SAMPLE_DISTANCE = 3.70;

//    public DistanceSensor intakeSensor;

    public IntakeSubsystem(HardwareMap hardwareMap, Sensors sensors) {

        intakeArm = hardwareMap.servo.get("intakeArm");
        intakeWheel = hardwareMap.get(DcMotor.class,"intakeWheel");

        intakeWheel.setDirection(DcMotor.Direction.REVERSE);

        this.sensors = sensors;

//        intakeSensor = hardwareMap.get(DistanceSensor.class, "intakeSensor");

    }

    /* Use for turning on the Intake Wheel */
    public void powerIntakeWheel(double power) {

        intakeWheel.setPower(power);
    }

    /* Set Intake Arm to intake position */
    public void setIntakeArm(double position) {

        intakeArm.setPosition(position);
    }

    /*
    public void groupIntakeArmUp(double position) {
        /* Check the status of the bucket servo and bucket lift
        * If `getBucketStatus() == BucketStatus.DOWN and
        * If `getLiftStatus() == LiftStatus.DOWN and
        * If `powerIntakeWheel is equal to or less than 0
        * then move the intake arm to `ARM_POSE_UP`
        /* Move the arm up
        intakeArm.setPosition(ARM_POSE_UP);
    }
    */

    public void groupIntakeArmUp() {
        /* Check the status of the bucket servo, bucket lift, and intake wheel power
        to make sure they are in the DOWN and OFF states */

        if (
//                bucketSub.getBucketStatus() == BucketSubsystem.BucketStatus.DOWN &&
//                bucketSub.getLiftStatus() == BucketSubsystem.LiftStatus.DOWN &&
                intakeWheel.getPower() <= 0.05) {

            // Move the intake arm to ARM_POSE_UP
            intakeArm.setPosition(ARM_POSE_UP);
        }
    }
    public void rotateIntakeArmDown(double position) {

        intakeArm.setPosition(ARM_POSE_DOWN);
    }

    /* Set all parameters for the intake position
     * Rotate arm into the intake (down) position
     * Power the wheel for intake
     * */
    public void groupIntakePosition() {
        // Assume or check that slides are at "out" position
        intakeArm.setPosition(ARM_POSE_DOWN);
        intakeWheel.setPower(WHEEL_INTAKE);

    }

    // Set all parameters for the release position
    public void groupReleasePosition() {
        intakeArm.setPosition(ARM_POSE_UP);
        intakeWheel.setPower(WHEEL_RELEASE);

    }

    // A method that increments a servo position by a given amount (0.05) with every button press
    public void incrementIntakeArm(double turd) {
        // take current
    }

    public enum intakeArmStatus {
        ARM_DOWN,
        ARM_UP,
        UNKNOWN
    }

    /*
    public intakeArmStatus getIntakeArmStatus() {
        double currentPosition = intakeArm.getPosition();
        double tolerance = 0.05; // 5% tolerance

        if (Math.abs(currentPosition - ARM_POSE_DOWN) <= tolerance * ARM_POSE_DOWN) {
            return intakeArmStatus.ARM_DOWN;
        } else if (Math.abs(currentPosition - ARM_POSE_UP) <= tolerance * ARM_POSE_UP) {
            return intakeArmStatus.ARM_UP;
        } else {
            return intakeArmStatus.UNKNOWN;
        }
    }
    */


    // Keep mechanism-related enums and states
    public enum IntakeArmStatus {
        ARM_DOWN("Arm in down position"),
        ARM_UP("Arm in up position"),
        UNKNOWN("Arm position unknown");

        private final String description;
        IntakeArmStatus(String description) {
            this.description = description;
        }
        public String getDescription() { return description; }
    }

    public IntakeArmStatus getIntakeArmStatus() {
        double currentPosition = intakeArm.getPosition();
        double tolerance = 0.05;

        if (Math.abs(currentPosition - ARM_POSE_DOWN) <= tolerance * ARM_POSE_DOWN) {
            return IntakeArmStatus.ARM_DOWN;
        } else if (Math.abs(currentPosition - ARM_POSE_UP) <= tolerance * ARM_POSE_UP) {
            return IntakeArmStatus.ARM_UP;
        } else {
            return IntakeArmStatus.UNKNOWN;
        }
    }

    // Example of using both together
    public void autoIntake() {
        if (sensors.getSampleStatus() == Sensors.SampleStatus.NO_SAMPLE) {
            // No sample detected, keep intake running
            setIntakeArm(ARM_POSE_DOWN);
            powerIntakeWheel(WHEEL_INTAKE);
        } else {
            // Sample acquired, stop intake
            powerIntakeWheel(0);
            setIntakeArm(ARM_POSE_UP);
        }

    }

} // End of IntakeSubsystem class
