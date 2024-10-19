package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.Servo.Direction;

public class FakeSubsystem {
    private DcMotor armMotor;
//    private Servo gripperServo;

    // Constants for servo positions
    private static final double GRIPPER_OPEN = 0.0;
    private static final double GRIPPER_CLOSED = 1.0;

    /*** Initializes the ArmSubsystem with the necessary hardware.
     *  @param hardwareMap The hardware map from the OpMode.
     *
     */
    public void init(HardwareMap hardwareMap) {
        // FIXME: Replace "leftBack" with the correct motor name
        // Borrowing a drive motor
        armMotor = hardwareMap.get(DcMotor.class, "leftBack");
//        gripperServo = hardwareMap.get(Servo.class, "");

        // Set motor direction
        armMotor.setDirection(DcMotorEx.Direction.FORWARD);

        // Set servo direction
//        gripperServo.setDirection(Direction.FORWARD);

        // Initialize arm and gripper to default positions
        armMotor.setPower(0);
//        gripperServo.setPosition(GRIPPER_OPEN);

//        telemetry.addData("Arm Subsystem", "Initialized");
//        telemetry.update();
    }

    /**
     * Raises the arm.
     *
     * @param power The power to apply to the arm motor.
     */
    public void raiseArm(double power) {
        armMotor.setPower(power);
    }

    /*** Lowers the arm.
     *
     * @param power The power to apply to the arm motor.
     */
    public void lowerArm(double power) {

        armMotor.setPower(-power);
    }

    /**
     * Stops the arm motor.
     */
    public void stopArm() {
        armMotor.setPower(0);
    }

    /**
     * Opens the gripper.
     */
//    public void openGripper() {
//        gripperServo.setPosition(GRIPPER_OPEN);
//    }

    /**
     * Closes the gripper.
     */
//    public void closeGripper() {
//        gripperServo.setPosition(GRIPPER_CLOSED);
//    }

}
