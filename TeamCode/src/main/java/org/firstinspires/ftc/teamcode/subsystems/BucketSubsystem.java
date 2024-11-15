package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.IntakeArmStatus.ARM_DOWN;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.sensors.Sensors;

public class BucketSubsystem {

    public final Servo bucketServo;
    public final DcMotor lift;
    private final Telemetry telemetry;
    private final ElapsedTime delayTimer = new ElapsedTime();
    private Sensors sensors;
    private IntakeSubsystem intakeSub;

    // Bucket positions
    public final static double BUCKET_DOWN_POSE = 0.88;
    public final static double BUCKET_UP_POSE = 0.15;

    // Lift positions
    public final static int HIGH_BASKET = 2800;
    public final static int LOW_BASKET = 1400;
    public final static int LIFT_DOWN = 0;

    // Tolerance for lift position checks
    private static final double LIFT_TOLERANCE = 0.03;

    public BucketSubsystem(HardwareMap hardwareMap, Telemetry telemetry) {

        this.sensors = new Sensors(hardwareMap);
        this.telemetry = telemetry;

        bucketServo = hardwareMap.get(Servo.class, "bucket");
        lift = hardwareMap.get(DcMotor.class, "lift");
        lift.setDirection(DcMotor.Direction.REVERSE);
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
        // Only move lift if intake arm is down
        if (intakeSub.getIntakeArmStatus() == ARM_DOWN) {
            lift.setTargetPosition(pose);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(power);
        } else {
            // Optional: Add telemetry or logging here to indicate why lift didn't move
            telemetry.addData("Lift Error", "Cannot move lift while arm is up");
            telemetry.update();
        }
    }
    public void setLiftHigh() { setLift(HIGH_BASKET, 0.60); }

    public void setLiftLow() {
        setLift(LOW_BASKET, 0.80);
    }

    public void setLiftDown() {
        setLift(LIFT_DOWN, 0.80);
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
        return servoPosition == BUCKET_DOWN_POSE ? BucketStatus.DOWN :
                servoPosition == BUCKET_UP_POSE ? BucketStatus.UP :
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

        if (Math.abs(liftPosition - HIGH_BASKET) <= LIFT_TOLERANCE * HIGH_BASKET) {
            return LiftStatus.HIGH_BASKET;
        } else if (Math.abs(liftPosition - LOW_BASKET) <= LIFT_TOLERANCE * LOW_BASKET) {
            return LiftStatus.LOW_BASKET;
        } else if (liftPosition >= -35 && liftPosition <= 35) {
            return LiftStatus.DOWN;
        } else {
            return LiftStatus.UNKNOWN;
        }
    }
}