package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ClimbSubsystem {

    public DcMotorEx climbMotorOne;
    public DcMotorEx climbMotorTwo;



    public ClimbSubsystem(HardwareMap hardwareMap) {

        climbMotorOne = hardwareMap.get(DcMotorEx.class, "leftBack");

        climbMotorOne.setDirection(DcMotorEx.Direction.FORWARD);
        climbMotorOne.setPower(0);
        climbMotorTwo.setDirection(DcMotorEx.Direction.FORWARD);
        climbMotorTwo.setPower(0);

    }
        /* This will move the climber up */
        // FIXME
        public void setMotorPowerUp(double power) {
            climbMotorOne.setPower(power);
            climbMotorOne.setPower(power);
        }

        // this will move the arm down//
        public void setMotorPowerDown(double power){
            climbMotorOne.setPower(-power);
            climbMotorTwo.setPower(0);
        }


}