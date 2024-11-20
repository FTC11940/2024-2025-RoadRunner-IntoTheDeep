package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ClimbSubsystem {

    public static class Constants {
        public static final int CLIMB_UP = 100;
        public static final int CLIMB_DOWN = 0;
        public static final double CLIMB_POWER = 0.25;

        // You might want to add other constants here like:
        // public static final String CLIMBER_ONE_NAME = "climberOne";
        // public static final String CLIMBER_TWO_NAME = "climberTwo";
    }

    public DcMotorEx climberOneMotor;
//    public DcMotorEx climberTwoMotor;

//    public ClimbMotorGroup;

    private final ElapsedTime delayTimer = new ElapsedTime();

    public ClimbSubsystem(HardwareMap hardwareMap) {
        climberOneMotor = hardwareMap.get(DcMotorEx.class, "climberOne");
//        climberOneMotor = hardwareMap.get(DcMotorEx.class,"climberTwo");

        climberOneMotor.setDirection(DcMotorEx.Direction.FORWARD);
        climberOneMotor.setPower(0);

//        climberTwoMotor.setDirection(DcMotorEx.Direction.FORWARD);
//        climberTwoMotor.setPower(0);

//        motorGroup = new ClimbMotorGroup(climbMotorOne, climbMotorTwo);
    }

    /* This will move the climber up */
    public void setClimber(int position) {
        climberOneMotor.setTargetPosition(position);
        climberOneMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        climberOneMotor.setPower(Constants.CLIMB_POWER);
    }

    public void powerClimber(double power) {
        climberOneMotor.setPower(power);
        climberOneMotor.setPower(0);
    }

    /* Reset the slide motor encoder */
    public void resetClimberEncoder() {
        climberOneMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleepy(0.1);
        climberOneMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void sleepy(double seconds) {
        delayTimer.reset();
        while (delayTimer.seconds() < seconds) {
        }
    } // end of sleepy method
}