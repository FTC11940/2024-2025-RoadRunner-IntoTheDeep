package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.Dcmotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Climbsubsystem {

    private final DcMotor someMotor;
    private final DcMotor someMotor;

    public Climbsubsystem(HardwareMap hardwareMap ) {


        someMotor = hardwareMap.get(DcMotor.class, "leftBack");
        someOtherMotor = hardwareMap.get(DcMotor.class, "rightBack");

        somemoter.setDirection(DcMotorEx.direction.FORWARD);
        someMotor.setPower(0);
        someOtherMotor.setDirection(DcMotorEx.Direction.FOWARD);
        someOthermotor.setPower(0);

        // this will move the arm up//
        public void setMotorpowerup(double power)
        someMotor.setPower(power);
        someOtherMotor.setpower(power);

        // this will move the arm down//
        public void setMotorpowerdown(double power ) {
            someMotor.setPower(-power);
            someOtherMotor.setpower(0);

            public void incrementMotor(double rotSomeDegrees) }
        }

    }


}