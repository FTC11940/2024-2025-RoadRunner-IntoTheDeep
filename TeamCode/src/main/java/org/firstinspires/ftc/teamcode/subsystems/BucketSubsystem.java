package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class BucketSubsystem {

    Servo bucketServo;

    public final static double BUCKET_INTAKE_POSE = 0;
    public final static double BUCKET_RELEASE_POSE = 1;

    public BucketSubsystem(HardwareMap hardwareMap) {
        bucketServo = hardwareMap.get(Servo.class, "bucketServo");
    }

    /* Use to set the bucket to intake or release position */
    public void setBucketPose(double pose) {
        bucketServo.setPosition(pose);
    }

}