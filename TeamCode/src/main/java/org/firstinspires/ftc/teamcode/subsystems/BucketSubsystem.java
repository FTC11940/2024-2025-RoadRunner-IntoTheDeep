package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.IntakeArmStatus.ARM_DOWN;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.IntakeArmStatus.ARM_UP;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.sensors.Sensors;

public class BucketSubsystem {

    // Hardware
    public final Servo bucketServo;
    public final DcMotorEx lift;
    private final ElapsedTime delayTimer = new ElapsedTime();
    private final TouchSensor liftTouch;
    private Sensors sensors;
    private IntakeSubsystem intakeSub;

    // Constructor
    public BucketSubsystem(HardwareMap hardwareMap,Sensors sensors) {
        // this.sensors = new Sensors(hardwareMap);
        //this.telemetry = telemetry;

        bucketServo = hardwareMap.get(Servo.class,"bucket");
        lift = hardwareMap.get(DcMotorEx.class,"lift");
        liftTouch = hardwareMap.get(TouchSensor.class,"liftTouch");

        lift.setDirection(DcMotor.Direction.REVERSE);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Subsystem connections
    public void setIntakeSubsystem(IntakeSubsystem intakeSub) {
        this.intakeSub = intakeSub;
    }

    // Bucket methods
    private boolean isSafeToBucketUp() {
        if (intakeSub == null) {
//            telemetry.addData("Bucket Safety", "Cannot verify safety - intake subsystem not connected");
            return false;
        }

        boolean armIsUp = intakeSub.getIntakeArmStatus() == ARM_UP;

        return !armIsUp;
    }

    public void setBucket(double pose) {
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

    public BucketStatus getBucketStatus() {
        double currentPosition = bucketServo.getPosition();

        if (Math.abs(currentPosition - Constants.BUCKET_DOWN) <= Constants.BUCKET_POSITION_TOLERANCE) {
            return BucketStatus.DOWN;
        } else if (Math.abs(currentPosition - Constants.BUCKET_MID) <= Constants.BUCKET_POSITION_TOLERANCE) {
            return BucketStatus.MID;
        } else if (Math.abs(currentPosition - Constants.BUCKET_UP) <= Constants.BUCKET_POSITION_TOLERANCE) {
            return BucketStatus.UP;
        }
        return BucketStatus.UNKNOWN;
    }

    public boolean isBucketInPosition(double targetPosition) {
        return Math.abs(bucketServo.getPosition() - targetPosition) <= Constants.BUCKET_POSITION_TOLERANCE;
    }

    // Lift methods
    public void setSimpleLift(int pose,double power) {
        if (intakeSub != null && intakeSub.getIntakeArmStatus() != ARM_DOWN) {
            lift.setPower(0);
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

    public void setLift(int pose,double power) {
        if (intakeSub.getIntakeArmStatus() == ARM_UP) {
            lift.setPower(0);
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
            if (Math.abs(currentPosition - Constants.LIFT_HIGH) < Constants.APPROX_LIFT_POSE || Math.abs(currentPosition - Constants.LIFT_LOW) < Constants.APPROX_LIFT_POSE) {
                // Hold position for HIGH and LOW positions
                lift.setPower(Constants.LIFT_HOLD_POWER);
            } else {
                lift.setPower(0);
            }
        }
    }

    public void setLiftHigh() {
        if (isSafeToBucketUp()) {
            setLift(Constants.LIFT_HIGH,0.60);
        }
    }

    public void setLiftLow() {
        if (isSafeToBucketUp()) {
            setLift(Constants.LIFT_LOW,0.80);
        }
    }

    public void setLiftDown() {
        if (isSafeToBucketUp()) {
            setLift(Constants.LIFT_DOWN,0.60);
            // After reaching down position, ensure motor is stopped
            if (Math.abs(lift.getCurrentPosition()) < Constants.APPROX_LIFT_POSE) {
                lift.setPower(0);
            }
        }
    }

    public void moveLiftUp() {
        if (isSafeToBucketUp()) {
            int currentPosition = lift.getCurrentPosition();
            setLift(currentPosition + 50,0.60);
        }
    }

    public void moveLiftDown() {
        if (isSafeToBucketUp()) {
            int currentPosition = lift.getCurrentPosition();
            setLift(currentPosition - 50,0.80);
        } else {
            lift.setPower(0); // Set power to 0 when not in use
        }
    }

    public void tareLift() {
        resetLiftEncoder();
        lift.setPower(0);
    }

    public void resetLiftEncoder() {
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleepy(0.1);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lift.setPower(0);
    }

    public void resetLiftEncoderOnTouch() {
        if (liftTouch.isPressed()) {
            resetLiftEncoder();
//            lift.setPower(0);
        }

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

    // Utility methods
    private void sleepy(double seconds) {
        delayTimer.reset();
        while (delayTimer.seconds() < seconds) {
            // Wait
        }
    }

    // Enums
    public enum BucketStatus {
        DOWN("Bucket down"),
        MID("Bucket mid position"),
        UP("Bucket up"),
        UNKNOWN("Position unknown");

        private final String description;

        BucketStatus(String description) {

            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }


    public enum LiftStatus {
        HIGH_BASKET("High basket position"),LOW_BASKET("Low basket position"),DOWN("Down position"),UNKNOWN("Position unknown");

        private final String description;

        LiftStatus(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    // Constants
    public static class Constants {
        // Bucket positions
        public static final double BUCKET_DOWN = 1.20; // 0.90
        public static final double BUCKET_MID = 0.9; // 0.575
        public static final double BUCKET_UP = 0.5; // 0.15
        public static final double BUCKET_POSITION_TOLERANCE = 0.05;

        // Lift positions
        public static final int LIFT_HIGH = 3000;
        public static final int LIFT_LOW = 1400;
        public static final int LIFT_DOWN = 0;
        public static final double LIFT_TOLERANCE = 0.03;
        public static final double APPROX_LIFT_POSE = 10;

        // Lift powers
        public static final double LIFT_HOLD_POWER = 0.15;
        public static final double LIFT_MOVING_POWER = 0.60;
    }
}