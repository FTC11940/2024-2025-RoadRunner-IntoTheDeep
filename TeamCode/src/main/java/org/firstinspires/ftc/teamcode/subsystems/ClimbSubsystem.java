package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ClimbSubsystem {

    DcMotor someMotor;

    DcMotor someOtherMotor;


    public ClimbSubsystem(HardwareMap hardwareMap) {

        someMotor = hardwareMap.get(DcMotor.class, "leftBack");
        someOtherMotor = hardwareMap.get(DcMotor.class, "rightBack");


        someMotor.setDirection(DcMotorEx.Direction.FORWARD);
        someMotor.setPower(0);
        someOtherMotor.setDirection(DcMotorEx.Direction.FORWARD);
        someOtherMotor.setPower(0);
        // FIXME
    }
        // this will move the arm up//

        // FIXME
        public void setMotorPowerUp(double power) {
                someMotor.setPower(power);
                someOtherMotor.setPower(power);
        }

        // FIXME
        // this will move the arm down//
        public void setMotorPowerDown(double power){
        someMotor.setPower(-power);
        someOtherMotor.setPower(0);
        }

    }


}