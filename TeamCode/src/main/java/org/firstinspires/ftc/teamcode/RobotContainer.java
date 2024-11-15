package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem.*;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.*;
import static org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem.*;

import android.annotation.SuppressLint;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.sensors.Sensors;
import org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ClimbSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem;

// @Disabled

@TeleOp(group = "drive", name = "TeleOp")

public class RobotContainer extends LinearOpMode {

    private Sensors sensors;
    private SampleMecanumDrive drive;
    private BucketSubsystem bucketSub;
    private IntakeSubsystem intakeSub;
    private SlidesSubsystem slidesSub;
    private DriveSubsystem driveSub;

    @SuppressLint("DefaultLocale")

    @Override

    public void runOpMode() throws InterruptedException {

        /* Subsystems */
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Sensors sensors = new Sensors(hardwareMap);
        BucketSubsystem bucketSub = new BucketSubsystem(hardwareMap, telemetry);
        IntakeSubsystem intakeSub = new IntakeSubsystem(hardwareMap, sensors);
        SlidesSubsystem slidesSub = new SlidesSubsystem(hardwareMap, sensors);
        DriveSubsystem driveSub = new DriveSubsystem(hardwareMap);
        ClimbSubsystem climbSub = new ClimbSubsystem(hardwareMap);

        // Required to initialize the subsystems when starting the OpMode
        waitForStart();

        /* Reset the motor encoder position after starting the OpMode */
        slidesSub.resetSlideEncoder();
        climbSub.resetClimberEncoder();
        bucketSub.resetLiftEncoder();

        // While loop to keep the robot running
        while (opModeIsActive()) {

            slidesSub.resetSlideEncoderOnTouch();

            /*
             * DRIVER INPUT MAPPING
             * Map methods (actions) from the subsystems to gamepad inputs
             * See `ControllerMapping.md` for gamepad field names
             */
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            /* Set the intake arm to intake or release position*/

            if (gamepad1.a) {
//                intakeSub.setIntakeArm(ARM_POSE_UP);
                intakeSub.groupIntakeArmUp();
            }

            if (gamepad1.b) {
                intakeSub.setIntakeArm(ARM_POSE_DOWN);
            }

            if (gamepad1.x) {
                bucketSub.setBucket(BUCKET_DOWN_POSE);
            }
            if (gamepad1.y) {
                bucketSub.setBucket(BUCKET_UP_POSE);
            }

            if (gamepad1.left_bumper) {
                slidesSub.setSlidePose(SLIDE_IN_POSE);
            }
            if (gamepad1.right_bumper) {
                slidesSub.setSlidePose(SLIDE_OUT_POSE);
            }

            // Use the right trigger to power the intake wheel (for picking up pieces)
            // Use the left trigger to reverse the intake wheel (for dropping pieces into the bucket)
            if (gamepad1.right_trigger > 0) {
                if (intakeSub.getSampleStatus() == IntakeSubsystem.SampleStatus.SAMPLE_ACQUIRED) {
                    intakeSub.powerIntakeWheel(0.2);
                } else {
                    intakeSub.powerIntakeWheel(gamepad1.right_trigger * WHEEL_INTAKE);
                }

            } else if (gamepad1.left_trigger > 0) {
                intakeSub.powerIntakeWheel(gamepad1.left_trigger * WHEEL_RELEASE); // Scale power and reverse direction
            } else {
                intakeSub.powerIntakeWheel(0); // Stop the intake wheel
            }

            if (gamepad1.dpad_right) {
                bucketSub.setLiftHigh();
            }
            if (gamepad1.dpad_left) {
                bucketSub.setLiftLow();
            }
            if (gamepad1.dpad_down) {
                bucketSub.setLiftDown();
            }

            /*
             * OPERATOR INPUT MAPPING
             * Map methods (actions) from the subsystems to gamepad inputs for the second controller
             * See `ControllerMapping.md` for gamepad field names
             */

            if (gamepad2.a) {
                bucketSub.setBucket(BUCKET_DOWN_POSE);
            }
            if (gamepad2.b) {
                bucketSub.setBucket(BUCKET_UP_POSE);
            }

            if (gamepad2.dpad_up) {
                climbSub.setClimber(climbSub.CLIMB_UP);
            } else if (gamepad2.dpad_down) {
                climbSub.setClimber(climbSub.CLIMB_DOWN);
            } else {
                climbSub.setClimber(0);
            }

            /* Use the right bumper to Power Intake Wheel (for picking up pieces)
             * Use the left bumper to reverse Power Intake Wheel (for dropping pieces into the bucket) */

            if (gamepad2.right_bumper) {
                intakeSub.powerIntakeWheel(WHEEL_INTAKE);
            } else if (gamepad2.left_bumper) {
                intakeSub.powerIntakeWheel(WHEEL_RELEASE);
            } else {
                intakeSub.powerIntakeWheel(0);
            }

            /* Use the X button to incrementally move the slide motor in a reverse direction
             * Use the Y button to incrementally move the slide motor in a forward direction */
            if (gamepad2.x) {
//                slidesSub.setSlidePose(SLIDE_POSE_IN);
            }
            if (gamepad2.y) {
//                slidesSub.setSlidePose(SLIDE_POSE_OUT);
            }

            /* Add telemetry for slide encoder */
            telemetry.addData("Intake Wheel Power",intakeSub.intakeWheel.getPower());

            /* Add telemetry for intake arm status */
            telemetry.addData("Intake Arm", String.format("%s, (%.2f)",
                    intakeSub.getIntakeArmStatus(), intakeSub.intakeArm.getPosition()));

            /* Add telemetry for slide encoder */
            telemetry.addData("Slide", String.format("%s, (%d)",
                    slidesSub.getSlideStatus(), slidesSub.slide.getCurrentPosition()));

            /* Add telemetry for climber encoder */
            telemetry.addData("Bucket", String.format("%s, (%.2f)",
                    bucketSub.getBucketStatus(), bucketSub.bucketServo.getPosition()));

            /* Add telemetry for lift encoder */
            telemetry.addData("Lift", String.format("%s, (%d)",
                    bucketSub.getLiftStatus(), bucketSub.lift.getCurrentPosition()));

            /* Add telemetry for slide touch sensor to reset the encoder to zero when it touches */
            telemetry.addData("Slide Touch",sensors.isSlideTouchPressed());

            /* Add telemetry for intake distance sensor */
            telemetry.addData("Intake Distance", String.format("%s, %.2f (CM)",
                    intakeSub.getSampleStatus(), sensors.intakeSensor.getDistance(DistanceUnit.CM)));

            telemetry.update();



        } // end of while loop

    } // end of runOpMode method

} // end of the class