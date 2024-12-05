package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.sensors.Sensors;

public class IntakeSubsystem {

    public final Servo intakeArm;
    public final DcMotor intakeWheel;
    private final Sensors sensors;
    private BucketSubsystem bucketSub; // Add reference to BucketSubsystem

    public IntakeSubsystem(HardwareMap hardwareMap,Sensors sensors) {
        intakeArm = hardwareMap.servo.get("intakeArm");
        intakeWheel = hardwareMap.get(DcMotor.class,"intakeWheel");
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

    public void smartPowerIntakeWheel(double power) {
        /* If Sensors.getSampleStatus() is SAMPLE_DETECTED, set power to 0.1 */
        if (sensors.getSampleStatus() == Sensors.SampleStatus.SAMPLE_DETECTED) {
            intakeWheel.setPower(0.15);
            /* Else set power to 1.0 */
        } else {
            intakeWheel.setPower(1.0);
        }
    }

    public double smartPowerIntakeTrigger(double inPower, double outPower) {
        // Return early if power inputs are too small
        if (Math.abs(inPower) < 0.05 && Math.abs(outPower) < 0.05) {
            powerIntakeWheel(0);
            return 0;
        }

        double power;
        if (inPower > outPower) {
            // Intake direction
            if (sensors.getSampleStatus() == Sensors.SampleStatus.SAMPLE_GRABBED) {
                power = 0.15; // Hold sample with reduced power
            } else {
                power = inPower; // Full intake power
            }
        } else {
            power = -outPower; // Release direction
        }

        powerIntakeWheel(power);
        return power;
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

    public IntakeArmStatus getIntakeArmStatus() {
        double currentPosition = intakeArm.getPosition();
        double tolerance = Constants.ARM_POSITION_TOLERANCE;

        if (Math.abs(currentPosition - Constants.ARM_POSE_DOWN) <= tolerance) {
            return IntakeArmStatus.ARM_DOWN;
        } else if (Math.abs(currentPosition - Constants.ARM_POSE_UP) <= tolerance) {
            return IntakeArmStatus.ARM_UP;
        } else if (Math.abs(currentPosition - Constants.ARM_POSE_MID) <= tolerance) {
            return IntakeArmStatus.ARM_MID;
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

    public enum IntakeArmStatus {
        ARM_DOWN("Arm is DOWN"),
        ARM_UP("Arm is UP"),
        ARM_MID("Arm is MID"),
        UNKNOWN("Arm position Unknown");

        private final String description;

        IntakeArmStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

        public static class Constants {
            // Move from IntakeConstants to static Constants
            public static final double ARM_POSE_DOWN = 0.20;
            public static final double ARM_POSE_UP = 0.75;
            public static final double ARM_POSE_MID = 0.475;
            public static final double WHEEL_INTAKE = 1.0;
            public static final double WHEEL_RELEASE = -1.0;
            public static final double POWER_REDUCTION = 0.10;
            public static final double ARM_POSITION_TOLERANCE = 0.05;
            public static final int LIFT_SAFE_THRESHOLD = 500;
        }
} // end