package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class FakeMotorSubsystem {

    /**
     * Borrowing a drive motor for demonstration purposes. This would be a TERRIBLE
     * variable name.
     */
    private final DcMotor someMotor;

    public FakeMotorSubsystem(HardwareMap hardwareMap) {

        /**
         * Borrowing a drive motor for demonstration purposes.
         * Note that the string name, "leftBack" has to match the name in the Driver
         * Station & Control Hub setup
         */
        someMotor = hardwareMap.get(DcMotor.class, "leftBack");

        // Set motor direction
        someMotor.setDirection(DcMotorEx.Direction.FORWARD);
        someMotor.setPower(0);

    }

    /**
     * Turns the motor in one direction
     * @param power The power to apply to the motor.
     * The power can be any value between -1.0 and 1.0 and is generally set in the OpMode.
     */
    public void rotateMotor(double power) {
        someMotor.setPower(power);
    }
    /***
     * Turns the motor in the opposite direction as rotate motor method
     * @param power The power to apply to the motor.
     */
    public void rotateMotorReverse(double power) {
        someMotor.setPower(-power);
    }

    /**
     * Stops the motor.
     */
    public void stopMotor() {
        someMotor.setPower(0);
    }

    /** increment the motor with every button press,
     * increase the motor by 10 degrees per button press
    */

    public void incrementMotor(double rotSomeDegrees) {
        // someMotor.get (+rotSomeDegrees);
    }
}
