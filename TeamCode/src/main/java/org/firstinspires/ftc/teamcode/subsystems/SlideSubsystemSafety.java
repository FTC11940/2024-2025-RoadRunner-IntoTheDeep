package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.Const;
import org.firstinspires.ftc.teamcode.sensors.Sensors;


public class SlideSubsystemSafety {

    public static class Constants {
        public static final int SLIDE_IN_POSE = 0;
        public static final int SLIDE_OUT_POSE = 2000;
        public static final double SLIDE_TOLERANCE = 0.03;

        // New constant for safe arm position
        public static final double ARM_SAFE_POSITION = 0.475; // Halfway between down (0.20) and up (0.75)
    }

    public final DcMotor slide;
    private final Sensors sensors;
    private IntakeSubsystem intakeSub; // Add reference to IntakeSubsystem

    public SlideSubsystemSafety(HardwareMap hardwareMap, Sensors sensors) {
        this.sensors = sensors;
        slide = hardwareMap.get(DcMotor.class, "slide");
        slide.setDirection(DcMotor.Direction.REVERSE);
        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    // Add method to set IntakeSubsystem reference
    public void setIntakeSubsystem(IntakeSubsystem intakeSub) {
        this.intakeSub = intakeSub;
    }

    // Helper method to check if arm is in safe position
    private boolean isArmInSafePosition() {
        if (intakeSub == null) {
            return false; // If intakeSub not set, don't allow movement for safety
        }
        return Math.abs(intakeSub.intakeArm.getPosition() - Constants.ARM_SAFE_POSITION) <= IntakeSubsystem.Constants.ARM_POSITION_TOLERANCE;
    }

    // Helper method to move arm to safe position
    private void moveArmToSafePosition() {
        if (intakeSub != null) {
            intakeSub.setIntakeArm(Constants.ARM_SAFE_POSITION);
        }
    }

    public void setSlidePose(int pose, double power) {
        // If trying to extend slides (move away from SLIDE_IN_POSE)
        if (pose > Constants.SLIDE_IN_POSE) {
            // First ensure arm is in safe position
            if (!isArmInSafePosition()) {
                moveArmToSafePosition();
                // Give a short delay to allow arm to move
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    // Handle interruption if needed
                }
            }

            // Double check arm position before allowing slide movement
            if (!isArmInSafePosition()) {
                powerSlide(0); // Stop slides if arm isn't in position
                return;
            }
        }

        slide.setTargetPosition(pose);
        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        slide.setPower(power);
    }

    public void powerSlide(double power) {
        // If trying to extend slides (positive power)
        if (power > 0 && slide.getCurrentPosition() > Constants.SLIDE_IN_POSE) {
            if (!isArmInSafePosition()) {
                moveArmToSafePosition();
                power = 0; // Don't move slides until arm is in position
            }
        }

        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        slide.setPower(power);
    }

    public void resetSlideEncoder() {
        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        slide.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void resetSlideEncoderOnTouch() {
        if (sensors.isSlideTouchPressed()) {
            resetSlideEncoder();
        }
    }

    public enum SlideStatus {
        IN("Slides retracted"),
        OUT("Slides extended"),
        UNKNOWN("Position unknown");

        private final String description;
        SlideStatus(String description) {
            this.description = description;
        }
        public String getDescription() { return description; }
    }

    public SlideStatus getSlideStatus() {
        int slidePosition = slide.getCurrentPosition();

        if (Math.abs(slidePosition - Constants.SLIDE_IN_POSE) <= Constants.SLIDE_TOLERANCE * Constants.SLIDE_IN_POSE) {
            return SlideStatus.IN;
        } else if (Math.abs(slidePosition - Constants.SLIDE_OUT_POSE) <= Constants.SLIDE_TOLERANCE * Constants.SLIDE_OUT_POSE) {
            return SlideStatus.OUT;
        } else {
            return SlideStatus.UNKNOWN;
        }
    }
}