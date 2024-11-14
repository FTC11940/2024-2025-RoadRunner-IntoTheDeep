package org.firstinspires.ftc.teamcode.subsystems;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class BucketSubsystem {

    public Servo bucketServo;

    public DcMotor lift; // Lift motor for bucket slide (GoBilda 5203)

    /* Bucket */
    public final static double BUCKET_DOWN_POSE = 0.88; // Right programming side
    public final static double BUCKET_UP_POSE = 0.15;   // Left programming side

    public final static int HIGH_BASKET = 2800;
    public final static int LOW_BASKET = 1400;
    public final static int LIFT_DOWN = 0;

    private ElapsedTime delayTimer = new ElapsedTime();

    private final IntakeSubsystem intakeSub;

    public BucketSubsystem(HardwareMap hardwareMap, IntakeSubsystem intakeSub) {
        bucketServo = hardwareMap.get(Servo.class,"bucket");
        lift = hardwareMap.get(DcMotor.class, "lift");

        // Motor needed to be reversed for the bucket to work with positive values
        lift.setDirection(DcMotor.Direction.REVERSE);

        this.intakeSub = intakeSub;

    }

    /* Use to set the bucket to intake or release position */
    public void setBucket(double pose) {
        /* Check to make sure arm is out of the way */

        bucketServo.setPosition(pose);
    }

    public void setLift(int pose) {
        /* Check to make sure arm is out of the way */

        lift.setTargetPosition(pose);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(0.60);
    }

    public void setLiftHigh() {
        // Check if the intake arm has a DOWN status
        if (intakeSub.getIntakeArmStatus() == IntakeSubsystem.intakeArmStatus.ARM_DOWN) {
            // Move the lift to the HIGH_BASKET position
            lift.setTargetPosition(HIGH_BASKET);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.60);
        } else {
            // Send a message to the Driver Station via telemetry
            telemetry.addData("!!", "MOVE THE ARM DOWN !!");
            telemetry.update();
        }
    }

    public void setLiftLow() {
        // Check if the intake arm has a DOWN status
        if (intakeSub.getIntakeArmStatus() == IntakeSubsystem.intakeArmStatus.ARM_DOWN) {
            // Move the lift to the LOW BASKET position
            lift.setTargetPosition(LOW_BASKET);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.80);
        } else {
            // Send a message to the Driver Station via telemetry
            telemetry.addData("!!", "MOVE THE ARM DOWN !!");
            telemetry.update();
        }
    }

    public void setLiftDown() {
        // Check if the intake arm has a DOWN status
        if (intakeSub.getIntakeArmStatus() == IntakeSubsystem.intakeArmStatus.ARM_DOWN) {
            lift.setTargetPosition(LIFT_DOWN);
            lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            lift.setPower(0.80);
        } else {
            // Send a message to the Driver Station via telemetry
            telemetry.addData("!!", "MOVE THE ARM DOWN !!");
            telemetry.update();
        }
    }

    public void powerLift(double power) {
        lift.setPower(power);
    }

    /* Reset the slide motor encoder */
    public void resetLiftEncoder() {
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleepy(0.1);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void sleepy(double seconds) {
        delayTimer.reset();
        while (delayTimer.seconds() < seconds) {
        }
    } // end of sleepy method

    public enum BucketStatus {
        DOWN,
        UP,
        UNKNOWN
    }

    public BucketStatus getBucketStatus() {
        double servoPosition = bucketServo.getPosition();

        if (servoPosition == BUCKET_DOWN_POSE) {
            return BucketStatus.DOWN;
        } else if (servoPosition == BUCKET_UP_POSE) {
            return BucketStatus.UP;
        } else {
            return BucketStatus.UNKNOWN;
        }
    }

    public enum LiftStatus {
        HIGH_BASKET,
        LOW_BASKET,
        DOWN,
        UNKNOWN
    }

    public LiftStatus getLiftStatus() {
        int liftPosition = lift.getCurrentPosition();

        //      +|- of 30 on the low end and 84 on the high end
        double tolerance = 0.03;

        if (Math.abs(liftPosition - HIGH_BASKET) <= tolerance * HIGH_BASKET) {
            return LiftStatus.HIGH_BASKET;
        } else if (Math.abs(liftPosition - LOW_BASKET) <= tolerance * LOW_BASKET) {
            return LiftStatus.LOW_BASKET;
        } else if (liftPosition >= -35 && liftPosition <= 35) {
            return LiftStatus.DOWN;
        } else {
            return LiftStatus.UNKNOWN;
        }
    }

} // End of class