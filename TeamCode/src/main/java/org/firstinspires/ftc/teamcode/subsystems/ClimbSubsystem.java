package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ClimbSubsystem {

    DcMotor someMotor;

    DcMotor someOtherMotor;



    public ClimbSubsystem(HardwareMap hardwareMap ) {

        someMotor = hardwareMap.get(DcMotor.class, "leftBack");
        someOtherMotor = hardwareMap.get(DcMotor.class, "rightBack");


        someMotor.setDirection(DcMotorEx.Direction.FORWARD);
        someMotor.setPower(0);
        someOtherMotor.setDirection(DcMotorEx.Direction.FORWARD);
        someOtherMotor.setPower(0);

        // this will move the arm up//
        public void setMotorpowerupdoublepower {
            someMotor.setPower(double power)
            someOtherMotor.setPower(power);
        }
        // this will move the arm down//
        public void setMotorpowerdown ( double power);
        someMotor.setPower(-power);
        someOtherMotor.SetPower(0);
    }
            // FIXME bracket
            public void incrementMotordouble (RotSomeDegrees)
]
        }

    }


}