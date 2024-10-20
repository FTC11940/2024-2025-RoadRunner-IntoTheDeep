package org.firstinspires.ftc.teamcode.subsystems;

import org.firstinspires.ftc.teamcode.subsystems.intake; // Added by CoPilot

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.TouchSensor;

// Removed the extended LinearOpMode
public class intake {
    //Equation for finding 160 degrees: 0.88889*X and X = what position the servo is in when it is 180 degrees.
    //servoName.setPosition(0.88889);
    
    Servo intakeServo;
    DcMotor intakeMotor;
    TouchSensor intakeTouch;
    
    //position to pick up pieces (for picking up pieces) 0 degrees
    public static final double Picking_Up = 0;
    
    //Dropping pieces into the bucket (for scoring in the baskets) about 160 degrees
    public static final double Dropping_Into_Bucket = 0.888889;
    
    //The intake (For picking up pieces using the wheel)
    public static final double Intake = 1;

    //The release (for dropping pieces into the observation zone (specimen zone))
    public static final double Release = -1;


    //@Override
    public void runOpMode() {

        intakeServo = hardwareMap.servo.get("intakeServo");
        intakeMotor = hardwareMap.get(DcMotor.class, "intakeMotor");
        intakeTouch = hardwareMap.get(TouchSensor.class, "intakeTouch");


        //Set the starting position

        waitForStart();

        while (opModeIsActive()) {

        }


    }
}
