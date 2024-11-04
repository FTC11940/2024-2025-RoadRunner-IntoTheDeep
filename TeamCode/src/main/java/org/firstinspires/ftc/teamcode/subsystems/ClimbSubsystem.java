package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class ClimbSubsystem {

    public DcMotorEx climberOneMotor;
//    public DcMotorEx climberTwoMotor;

//    public ClimbMotorGroup;

    public final double CLIMB_UP = 1.0;
    public final double CLIMB_DOWN = -0.5;


    public ClimbSubsystem(HardwareMap hardwareMap) {

        climberOneMotor = hardwareMap.get(DcMotorEx.class,"climberOne");
//        climberOneMotor = hardwareMap.get(DcMotorEx.class,"climberTwo");

        climberOneMotor.setDirection(DcMotorEx.Direction.FORWARD);
        climberOneMotor.setPower(0);

//        climberTwoMotor.setDirection(DcMotorEx.Direction.FORWARD);
//        climberTwoMotor.setPower(0);

//        motorGroup = new ClimbMotorGroup(climbMotorOne, climbMotorTwo);
    }

    /* This will move the climber up */
    // TODO Determine and Set the encoder position of the motor
    public void setClimber(double position) {
        climberOneMotor.setPower(position);
        climberOneMotor.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        climberOneMotor.setPower(position);
    }

    // TODO Test the power of the climber motor
    public void powerClimber(double power) {
        climberOneMotor.setPower(power);
        climberOneMotor.setPower(0);
    }

    // TODO Test the reset of climb encoder. Currently no sensor
    /* Reset the slide motor encoder */
    public void resetClimberEncoder() {
        climberOneMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sleepy(0.1);
        climberOneMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private ElapsedTime delayTimer = new ElapsedTime();

    public void sleepy(double seconds) {
        delayTimer.reset();
        while (delayTimer.seconds() < seconds) {
        }
    } // end of sleepy method

}