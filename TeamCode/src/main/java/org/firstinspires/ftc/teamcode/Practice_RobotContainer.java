package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.subsystems.PracticeServoSubsystem.SERVO_CLOSED;
import static org.firstinspires.ftc.teamcode.subsystems.PracticeServoSubsystem.SERVO_OPEN;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.sensors.SomeDistanceSensor;
import org.firstinspires.ftc.teamcode.sensors.SomeMagSensors;
import org.firstinspires.ftc.teamcode.sensors.SomeTouchSensors;
import org.firstinspires.ftc.teamcode.sensors.SomeColorSensors;
import org.firstinspires.ftc.teamcode.subsystems.PracticeMotorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PracticeServoSubsystem;

// @Disabled

@TeleOp(group = "drive", name = "Practice TeleOp")

public class Practice_RobotContainer extends LinearOpMode {
    
    @Override
    public void runOpMode() throws InterruptedException {

        // Subsystems
        // Create new instances of classes, including subsystems, and assign to a variable
        // FIXME RC1. Replace these with the names of actual subsystems
        PracticeServoSubsystem servoSub = new PracticeServoSubsystem(hardwareMap);
        PracticeMotorSubsystem motorSub = new PracticeMotorSubsystem(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        SomeTouchSensors touch = new SomeTouchSensors(hardwareMap);
        SomeMagSensors mag = new SomeMagSensors(hardwareMap);
        SomeColorSensors color = new SomeColorSensors(hardwareMap);
        SomeDistanceSensor distance = new SomeDistanceSensor(hardwareMap);

        // Required to initialize the subsystems when starting the OpMode
        waitForStart();

        // ! negation operator, i.e. the running code is not stopped
        // alternative -- opModeIsActive() and `isStarted()`
        // While loop to keep the robot running
        while (opModeIsActive()) {

            // Call methods from the subsystems and assign them to button presses
            // FIXME RC2.Replace these with button presses with current Methods (actions in Subsystems).
            if (gamepad1.a) {
                motorSub.rotateMotor(0.25); // Run the motor with 0.5 power
            } else if (gamepad1.b) {
                motorSub.rotateMotorReverse(0.25); // Run the motor with 0.5 power in reverse
            } else {
                motorSub.stopMotor(); // Stop motor if neither button is pressed
            } // end of if statement for A and B buttons

            // Open gripper with gamepad Y button
            if (gamepad1.y) {
                servoSub.openServo();
            } else if (gamepad1.x) {
                servoSub.closeServo();
            } // end of if statement for Y button

            // Rotate servo 90 degrees with gamepad2 A button
            if (gamepad2.a) {
                servoSub.setServoZeroPose(SERVO_OPEN);
            } // end of if statement for A button
            if (gamepad2.b) {
                servoSub.setServoZeroPose(SERVO_CLOSED);
            } // end of if statement for B button

            if (gamepad2.x) {
                servoSub.setServoOnePose(0.75); // Backward
            } // end of if statement for X button
            if (gamepad2.y) {
                servoSub.setServoOnePose(0.25); // Forward
            } // end of if statement for Y button 
            

            if (touch.isTouchOnePressed()) {
                //TRUE - run the motor
                motorSub.rotateMotor(0.5);
            } else {
                //FALSE - stop the motor
                motorSub.rotateMotor(0);


            } // End of if statement for touch sensor


            // Display touch sensor data in telemetry
            if (touch.isTouchOnePressed()) {
                telemetry.addData("Touch Sensor", "Pressed");
            } else {
                telemetry.addData("Touch Sensor", "Not Pressed");
            }

            // Get color sensor data
            SomeColorSensors.ColorSensorData colorData = color.getColorSensorData();

            if (mag.magStatus()) {
                telemetry.addData("Mag Sensor", "Pressed");
            } else {
                telemetry.addData("Mag Sensor", "Not Pressed");
            }

            // Get distance sensor data
            // SomeDistanceSensor distance = new SomeDistanceSensor(hardwareMap);
            double distanceData = distance.getDistance();
            double distanceDataInches = distance.getDistanceInches();


            telemetry.addData("Distance (cm)", distanceData);
            telemetry.addData("Distance (in)", distanceDataInches);

            // Display color sensor data in telemetry
            telemetry.addData("Red", colorData.red);
            telemetry.addData("Green", colorData.green);
            telemetry.addData("Blue", colorData.blue);
            telemetry.addData("Alpha", colorData.alpha);
            
            telemetry.update();

        } // end of while loop

    } // end of runOpMode method

} // end of the class