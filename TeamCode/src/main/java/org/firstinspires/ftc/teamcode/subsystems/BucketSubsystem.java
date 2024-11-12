package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class BucketSubsystem {

    public Servo bucketServo;

    // Lift motor for bucket slide (GoBilda 5203)
    public DcMotor lift;

    // TODO Determine the bucket intake and release poses
    public final static double BUCKET_DOWN_POSE = 1.20;
    public final static double BUCKET_UP_POSE = 0.00;

    public final static int HIGH_BASKET = 2800;
    public final static int LOW_BASKET = 1000;
    public final static int LIFT_DOWN = 0;

    // TODO Determine the lift encoder positions

    /*
    Encoder  | Bucket height Approx
    --------------------------------
    2400     | 37.5 inches
    2000     | 33.0 inches
    1000     | 20.0 inches
    0000     | 08.0 inches
    * */

    public BucketSubsystem(HardwareMap hardwareMap) {
        bucketServo = hardwareMap.get(Servo.class,"bucket");
        lift = hardwareMap.get(DcMotor.class, "lift");

        // Motor needed to be reversed for the bucket to work with positive values
        lift.setDirection(DcMotor.Direction.REVERSE);

    }

    /* Use to set the bucket to intake or release position */
    public void setBucket(double pose) {

        bucketServo.setPosition(pose);
    }

    public void setLift(int pose) {
        lift.setTargetPosition(pose);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(0.30);
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

    private ElapsedTime delayTimer = new ElapsedTime();

    public void sleepy(double seconds) {
        delayTimer.reset();
        while (delayTimer.seconds() < seconds) {
        }
    } // end of sleepy method

    // TODO Bucket status
     public enum BucketStatus {
        BUCKET_DOWN,
        BUCKET_UP,
        UNKNOWN
    }
    public BucketStatus getBucketStatus() {
        double servoPosition = bucketServo.getPosition();

        if (servoPosition == BUCKET_DOWN_POSE) {
            return BucketStatus.BUCKET_DOWN;
        } else if (servoPosition == BUCKET_UP_POSE) {
            return BucketStatus.BUCKET_UP;
        } else {
            return BucketStatus.UNKNOWN;
        }
    }

    // TODO Lift Status
    public enum LiftStatus {
        HIGH_BASKET,
        LOW_BASKET,
        UNKNOWN
    }

    /* STRICT definition of status
    public LiftStatus getLiftStatus() {
        int liftPosition = lift.getCurrentPosition();

        if (liftPosition == HIGH_BASKET) {
            return LiftStatus.HIGH_BASKET;
        } else if (liftPosition == LOW_BASKET) {
            return LiftStatus.LOW_BASKET;
        } else {
            return LiftStatus.UNKNOWN;
        }
    }
    */

    public LiftStatus getLiftStatus() {
        int liftPosition = lift.getCurrentPosition();
        double tolerance = 0.05; // 5% tolerance

        if (Math.abs(liftPosition - HIGH_BASKET) <= tolerance * HIGH_BASKET) {
            return LiftStatus.HIGH_BASKET;
        } else if (Math.abs(liftPosition - LOW_BASKET) <= tolerance * LOW_BASKET) {
            return LiftStatus.LOW_BASKET;
        } else {
            return LiftStatus.UNKNOWN;
        }
    }


} // End of class