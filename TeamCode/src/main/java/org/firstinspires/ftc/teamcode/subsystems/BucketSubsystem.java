package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.IntakeArmStatus.ARM_DOWN;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.sensors.Sensors;

public class BucketSubsystem {

    public static class Constants {

        public static final double BUCKET_DOWN = 0.90; //

        public static final double BUCKET_UP = 0.15;
        public static final int LIFT_HIGH = 3000;
        public static final int LIFT_LOW = 1400;
        public static final int LIFT_DOWN = 0;
        public static final double LIFT_TOLERANCE = 0.03;
        public static final double APPROX_LIFT_POSE = 10;

        public static final double LIFT_HOLD_POWER = 0.15;  // Power to maintain position
        public static final double LIFT_MOVING_POWER = 0.60; // Default power when moving

    }

    public final Servo bucketServo;
    public final DcMotor lift;
    private final Telemetry telemetry;
    private final ElapsedTime delayTimer = new ElapsedTime();
    private Sensors sensors;
    private IntakeSubsystem intakeSub;

    public BucketSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {

        this.sensors = new Sensors(hardwareMap);
        this.telemetry = telemetry;

        bucketServo = hardwareMap.get(Servo.class, "bucket");
        lift = hardwareMap.get(DcMotor.class, "lift");

        // Configs for the lift motor
        lift.setDirection(DcMotor.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Method to set the IntakeSubsystem reference after initialization
    public void setIntakeSubsystem(IntakeSubsystem intakeSub) {
        this.intakeSub = intakeSub;
    }

    public void setBucket(double pose) {
        bucketServo.setPosition(pose);
    }

    /*
    public void setLift(int pose) {
        lift.setTargetPosition(pose);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(0.60);
    }
     */

    public void setLift(int pose, double power) {
        if (intakeSub.getIntakeArmStatus() != ARM_DOWN) {
            lift.setPower(0);
            telemetry.addData("Lift Error", "Cannot move lift while arm is up");
            telemetry.update();
            return;
        }

        // Check if we need to apply holding power
        boolean needsHoldingPower = pose == Constants.LIFT_HIGH || pose == Constants.LIFT_LOW;

        // If we're very close to target position
        if (Math.abs(lift.getCurrentPosition() - pose) < Constants.APPROX_LIFT_POSE) {
            // Apply holding power if at HIGH or LOW position, otherwise use zero power
            lift.setPower(needsHoldingPower ? Constants.LIFT_HOLD_POWER : 0);
            return;
        }

        // Moving to new position
        lift.setTargetPosition(pose);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(power);
    }

    public void updateLift() {
        int currentPosition = lift.getCurrentPosition();
        int targetPosition = lift.getTargetPosition();

        // If not actively moving (within tolerance of target)
        if (!lift.isBusy() || Math.abs(currentPosition - targetPosition) < Constants.APPROX_LIFT_POSE) {
            // Check if we're at a position that needs holding power
            if (Math.abs(currentPosition - Constants.LIFT_HIGH) < Constants.APPROX_LIFT_POSE ||
                    Math.abs(currentPosition - Constants.LIFT_LOW) < Constants.APPROX_LIFT_POSE) {
                lift.setPower(Constants.LIFT_HOLD_POWER);
            } else {
                lift.setPower(0);
            }
        }
    }

    public void setLiftHigh() { setLift(Constants.LIFT_HIGH, 0.60); }

    public void setLiftLow() {
        setLift(Constants.LIFT_LOW, 0.80);
    }

    public void setLiftDown() {
        setLift(Constants.LIFT_DOWN, 0.80);
    }

    public void resetLiftEncoder() {
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleepy(0.1);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void sleepy(double seconds) {
        delayTimer.reset();
        while (delayTimer.seconds() < seconds) {
            // Wait
        }
    }

    public enum BucketStatus {
        DOWN,
        UP,
        UNKNOWN
    }

    public BucketStatus getBucketStatus() {
        double servoPosition = bucketServo.getPosition();
        return servoPosition == Constants.BUCKET_DOWN ? BucketStatus.DOWN :
                servoPosition == Constants.BUCKET_UP ? BucketStatus.UP :
                        BucketStatus.UNKNOWN;
    }

    public enum LiftStatus {
        HIGH_BASKET,
        LOW_BASKET,
        DOWN,
        UNKNOWN
    }

    public LiftStatus getLiftStatus() {
        int liftPosition = lift.getCurrentPosition();

        if (Math.abs(liftPosition - Constants.LIFT_HIGH) <= Constants.LIFT_TOLERANCE * Constants.LIFT_HIGH) {
            return LiftStatus.HIGH_BASKET;
        } else if (Math.abs(liftPosition - Constants.LIFT_LOW) <= Constants.LIFT_TOLERANCE * Constants.LIFT_LOW) {
            return LiftStatus.LOW_BASKET;
        } else if (liftPosition >= -35 && liftPosition <= 35) {
            return LiftStatus.DOWN;
        } else {
            return LiftStatus.UNKNOWN;
        }
    }
}