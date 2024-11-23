package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.IntakeArmStatus.ARM_DOWN;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.sensors.Sensors;

public class BucketSubsystem {

    public static class Constants {
        public static final double BUCKET_DOWN = 0.90;
        public static final double BUCKET_UP = 0.15;
        public static final int LIFT_HIGH = 3000;
        public static final int LIFT_LOW = 1400;
        public static final int LIFT_DOWN = 0;
        public static final double LIFT_TOLERANCE = 0.03;
        public static final double APPROX_LIFT_POSE = 10;
        public static final double LIFT_HOLD_POWER = 0.15;
        public static final double LIFT_MOVING_POWER = 0.60;
        public static final double BUCKET_POSITION_TOLERANCE = 0.05;
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

        lift.setDirection(DcMotor.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setIntakeSubsystem(IntakeSubsystem intakeSub) {
        this.intakeSub = intakeSub;
    }

    // Helper method to check if it's safe to move bucket up
    private boolean isSafeToBucketUp() {
        if (intakeSub == null) {
            telemetry.addData("Bucket Safety", "Cannot verify safety - intake subsystem not connected");
            return false;
        }

        boolean armIsUp = intakeSub.getIntakeArmStatus() == IntakeSubsystem.IntakeArmStatus.ARM_UP;

        if (armIsUp) {
            telemetry.addData("Bucket Safety", "Cannot move bucket up while arm is up");
            telemetry.update();
            return false;
        }
        return true;
    }

    public void setBucket(double pose) {
        // If trying to move bucket up and it's not safe, don't move
        if (Math.abs(pose - Constants.BUCKET_UP) < Constants.BUCKET_POSITION_TOLERANCE) {
            if (!isSafeToBucketUp()) {
                return;
            }
        }
        bucketServo.setPosition(pose);
    }

    public void setBucketDown() {
        setBucket(Constants.BUCKET_DOWN);
    }

    public void setBucketUp() {
        if (isSafeToBucketUp()) {
            setBucket(Constants.BUCKET_UP);
        }
    }

    public void setLift(int pose, double power) {
        // Can't move lift if arm isn't down
        if (intakeSub != null && intakeSub.getIntakeArmStatus() != ARM_DOWN) {
            lift.setPower(0);
            telemetry.addData("Lift Error", "Cannot move lift while arm is up");
            telemetry.update();
            return;
        }

        boolean needsHoldingPower = pose == Constants.LIFT_HIGH || pose == Constants.LIFT_LOW;

        if (Math.abs(lift.getCurrentPosition() - pose) < Constants.APPROX_LIFT_POSE) {
            lift.setPower(needsHoldingPower ? Constants.LIFT_HOLD_POWER : 0);
            return;
        }

        lift.setTargetPosition(pose);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(power);
    }

    public void updateLift() {
        int currentPosition = lift.getCurrentPosition();
        int targetPosition = lift.getTargetPosition();

        if (!lift.isBusy() || Math.abs(currentPosition - targetPosition) < Constants.APPROX_LIFT_POSE) {
            if (Math.abs(currentPosition - Constants.LIFT_HIGH) < Constants.APPROX_LIFT_POSE ||
                    Math.abs(currentPosition - Constants.LIFT_LOW) < Constants.APPROX_LIFT_POSE) {
                lift.setPower(Constants.LIFT_HOLD_POWER);
            } else {
                lift.setPower(0);
            }
        }
    }

    public void setLiftHigh() {
        if (isSafeToBucketUp()) {
            setLift(Constants.LIFT_HIGH, 0.60);
        }
    }

    public void setLiftLow() {
        if (isSafeToBucketUp()) {
            setLift(Constants.LIFT_LOW, 0.80);
        }
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
        DOWN("Bucket down"),
        UP("Bucket up"),
        UNKNOWN("Position unknown");

        private final String description;
        BucketStatus(String description) {
            this.description = description;
        }
        public String getDescription() { return description; }
    }

    public BucketStatus getBucketStatus() {
        double currentPosition = bucketServo.getPosition();

        if (Math.abs(currentPosition - Constants.BUCKET_DOWN) <= Constants.BUCKET_POSITION_TOLERANCE) {
            return BucketStatus.DOWN;
        } else if (Math.abs(currentPosition - Constants.BUCKET_UP) <= Constants.BUCKET_POSITION_TOLERANCE) {
            return BucketStatus.UP;
        }
        return BucketStatus.UNKNOWN;
    }

    public boolean isBucketInPosition(double targetPosition) {
        return Math.abs(bucketServo.getPosition() - targetPosition) <= Constants.BUCKET_POSITION_TOLERANCE;
    }

    public enum LiftStatus {
        HIGH_BASKET("High basket position"),
        LOW_BASKET("Low basket position"),
        DOWN("Down position"),
        UNKNOWN("Position unknown");

        private final String description;
        LiftStatus(String description) {
            this.description = description;
        }
        public String getDescription() { return description; }
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