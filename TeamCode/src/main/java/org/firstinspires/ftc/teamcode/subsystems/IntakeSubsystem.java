package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.sensors.Sensors;

public class IntakeSubsystem {

    public static class Constants {
        // Servo Positions
        public static final double ARM_POSE_DOWN = 0.20;
        public static final double ARM_POSE_UP = 0.75;

        // Motor Powers
        public static final double WHEEL_INTAKE = 1.0;
        public static final double WHEEL_RELEASE = -1.0;
        public static final double POWER_REDUCTION = 0.10;

        // Tolerances
        public static final double ARM_POSITION_TOLERANCE = 0.05;

        // Safety Limits
        public static final int LIFT_SAFE_THRESHOLD = 500;
    }

    public final Servo intakeArm;
    public final DcMotor intakeWheel;
    private final Sensors sensors;
    private BucketSubsystem bucketSub; // Add reference to BucketSubsystem

    public IntakeSubsystem(HardwareMap hardwareMap, Sensors sensors) {
        intakeArm = hardwareMap.servo.get("intakeArm");
        intakeWheel = hardwareMap.get(DcMotor.class, "intakeWheel");
        intakeWheel.setDirection(DcMotor.Direction.REVERSE);
        this.sensors = sensors;
    }

    // Add method to set BucketSubsystem reference
    public void setBucketSubsystem(BucketSubsystem bucketSub) {
        this.bucketSub = bucketSub;
    }

    // Helper method to check if it's safe to move arm up
    private boolean isSafeToMoveArmUp() {
        if (bucketSub == null) {
            return false; // If bucketSub not set, don't allow movement for safety
        }

        // Check if bucket is up or lift is too high
        boolean bucketIsUp = bucketSub.getBucketStatus() == BucketSubsystem.BucketStatus.UP;
        boolean liftTooHigh = bucketSub.lift.getCurrentPosition() > Constants.LIFT_SAFE_THRESHOLD;

        return !bucketIsUp && !liftTooHigh;
    }

    public void powerIntakeWheel(double power) {
        intakeWheel.setPower(power);
    }

    public void setIntakeArm(double position) {
        // Only allow moving to up position if it's safe
        if (position >= Constants.ARM_POSE_UP && !isSafeToMoveArmUp()) {
            return; // Silently fail for now
        }
        intakeArm.setPosition(position);
    }

    public void groupIntakeArmUp() {
        if (intakeWheel.getPower() <= 0.05 && isSafeToMoveArmUp()) {
            intakeArm.setPosition(Constants.ARM_POSE_UP);
        }
    }

    public double smartPowerIntakeWheel(double rightTrigger, double leftTrigger) {
        double wheelPower = rightTrigger - leftTrigger;

        if (sensors.getSampleStatus() == Sensors.SampleStatus.SAMPLE_GRABBED && wheelPower > 0) {
            wheelPower *= Constants.POWER_REDUCTION;
        }

        intakeWheel.setPower(wheelPower);
        return intakeWheel.getPower();
    }

    public void groupIntakePosition() {
        intakeArm.setPosition(Constants.ARM_POSE_DOWN);
        intakeWheel.setPower(Constants.WHEEL_INTAKE);
    }

    public void groupReleasePosition() {
        if (isSafeToMoveArmUp()) {
            intakeArm.setPosition(Constants.ARM_POSE_UP);
            intakeWheel.setPower(Constants.WHEEL_RELEASE);
        }
    }

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
        double tolerance = Constants.ARM_POSITION_TOLERANCE;

        if (Math.abs(currentPosition - Constants.ARM_POSE_DOWN) <= tolerance) {
            return IntakeArmStatus.ARM_DOWN;
        } else if (Math.abs(currentPosition - Constants.ARM_POSE_UP) <= tolerance) {
            return IntakeArmStatus.ARM_UP;
        } else {
            return IntakeArmStatus.UNKNOWN;
        }
    }

    public void autoIntake() {
        if (sensors.getSampleStatus() == Sensors.SampleStatus.NO_SAMPLE) {
            setIntakeArm(Constants.ARM_POSE_DOWN);
            powerIntakeWheel(Constants.WHEEL_INTAKE);
        } else {
            powerIntakeWheel(0);
            if (isSafeToMoveArmUp()) {
                setIntakeArm(Constants.ARM_POSE_UP);
            }
        }
    }

    public double getIntakeWheelPower() {
        return intakeWheel.getPower();
    }
}