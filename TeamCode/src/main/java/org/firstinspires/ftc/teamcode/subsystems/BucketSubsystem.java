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
    public final static double BUCKET_INTAKE_POSE = 0.0;
    public final static double BUCKET_RELEASE_POSE = 1.00;

    public final static double HIGH_BASKET = 200;
    public final static double LOW_BASKET = 100;
    public final static double LIFT_DOWN = 0;

    public BucketSubsystem(HardwareMap hardwareMap) {
        bucketServo = hardwareMap.get(Servo.class,"bucket");
        lift = hardwareMap.get(DcMotor.class, "lift");
    }

    /* Use to set the bucket to intake or release position */
    public void setBucket(double pose) {

        bucketServo.setPosition(pose);
    }

    public void setLift(int pose) {
        lift.setTargetPosition(pose);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(0.25);
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

} // End of class