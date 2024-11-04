package org.firstinspires.ftc.teamcode.subsystems;

/*
 * This is a simplified drive subsystem for the robot.
 * It is intended as a backup and demonstration purposes.
 * We will likely use the MecanumDrive class from RoadRunner quickstart for competition.
 *
 * */

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.VoltageSensor;

public class DriveSubsystem {
    private DcMotor leftFront, leftRear, rightRear, rightFront;

    private IMU imu;
    private VoltageSensor batteryVoltageSensor;

    public DriveSubsystem(HardwareMap hardwareMap) {
        leftFront = hardwareMap.get(DcMotor.class,"leftFront");
        leftRear = hardwareMap.get(DcMotor.class,"leftBack");
        rightRear = hardwareMap.get(DcMotor.class,"rightBack");
        rightFront = hardwareMap.get(DcMotor.class,"rightFront");

        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftRear.setDirection(DcMotor.Direction.REVERSE);

        setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void setZeroPowerBehavior(DcMotor.ZeroPowerBehavior zeroPowerBehavior) {
        leftFront.setZeroPowerBehavior(zeroPowerBehavior);
        leftRear.setZeroPowerBehavior(zeroPowerBehavior);
        rightRear.setZeroPowerBehavior(zeroPowerBehavior);
        rightFront.setZeroPowerBehavior(zeroPowerBehavior);
    }

    public void drive(double forward,double strafe,double turn) {
        double leftFrontPower = forward + strafe + turn;
        double leftRearPower = forward - strafe + turn;
        double rightFrontPower = forward - strafe - turn;
        double rightRearPower = forward + strafe - turn;

        double max = Math.max(1.0,Math.abs(leftFrontPower));
        max = Math.max(max,Math.abs(leftRearPower));
        max = Math.max(max,Math.abs(rightFrontPower));
        max = Math.max(max,Math.abs(rightRearPower));

        leftFront.setPower(leftFrontPower / max);
        leftRear.setPower(leftRearPower / max);
        rightFront.setPower(rightFrontPower / max);
        rightRear.setPower(rightRearPower / max);
    }

    public void stop() {
        leftFront.setPower(0);
        leftRear.setPower(0);
        rightFront.setPower(0);
        rightRear.setPower(0);
    }
}

