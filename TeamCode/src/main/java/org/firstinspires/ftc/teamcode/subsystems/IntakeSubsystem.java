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
    }

    public final Servo intakeArm;
    public final DcMotor intakeWheel;
    private final Sensors sensors;

    public IntakeSubsystem(HardwareMap hardwareMap, Sensors sensors) {
        intakeArm = hardwareMap.servo.get("intakeArm");
        intakeWheel = hardwareMap.get(DcMotor.class, "intakeWheel");
        intakeWheel.setDirection(DcMotor.Direction.REVERSE);
        this.sensors = sensors;
    }

    public void powerIntakeWheel(double power) {
        intakeWheel.setPower(power);
    }

    public void setIntakeArm(double position) {
        intakeArm.setPosition(position);
    }

    public void groupIntakeArmUp() {
        if (intakeWheel.getPower() <= 0.05) {
            intakeArm.setPosition(Constants.ARM_POSE_UP);
        }
    }

    public void smartPowerIntakeWheel(double rightTrigger, double leftTrigger) {
        double wheelPower = rightTrigger - leftTrigger;

        if (sensors.getSampleStatus() == Sensors.SampleStatus.SAMPLE_GRABBED && wheelPower > 0) {
            wheelPower *= Constants.POWER_REDUCTION;
        }
        intakeWheel.setPower(wheelPower);
    }

    public void groupIntakePosition() {
        intakeArm.setPosition(Constants.ARM_POSE_DOWN);
        intakeWheel.setPower(Constants.WHEEL_INTAKE);
    }

    public void groupReleasePosition() {
        intakeArm.setPosition(Constants.ARM_POSE_UP);
        intakeWheel.setPower(Constants.WHEEL_RELEASE);
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

        if (Math.abs(currentPosition - Constants.ARM_POSE_DOWN) <= tolerance * Constants.ARM_POSE_DOWN) {
            return IntakeArmStatus.ARM_DOWN;
        } else if (Math.abs(currentPosition - Constants.ARM_POSE_UP) <= tolerance * Constants.ARM_POSE_UP) {
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
            setIntakeArm(Constants.ARM_POSE_UP);
        }
    }
}