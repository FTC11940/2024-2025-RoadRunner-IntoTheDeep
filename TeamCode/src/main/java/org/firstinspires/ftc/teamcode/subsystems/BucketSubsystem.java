package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class BucketSubsystem {

    public Servo bucketServo;

    // Lift motor for bucket slide (GoBilda 5203)
    public DcMotor lift;

    /* Bucket */
    // Right programming side
    public final static double BUCKET_DOWN_POSE = 0.88;
    // Left programming side
    public final static double BUCKET_UP_POSE = 0.15;

    public final static int HIGH_BASKET = 2800;
    public final static int LOW_BASKET = 1000;
    public final static int LIFT_DOWN = 0; // Li

    /*
    Encoder  | Bucket height Approx
    --------------------------------
    2400     | 37.5 inches
    2000     | 33.0 inches
    1000     | 20.0 inches
    0000     | 08.0 inches
    * */

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