package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem.BUCKET_DOWN_POSE;
import static org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem.BUCKET_UP_POSE;
import static org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem.HIGH_BASKET;
import static org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem.LIFT_DOWN;
import static org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem.LOW_BASKET;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.ARM_POSE_DOWN;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.ARM_POSE_UP;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.WHEEL_INTAKE;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.WHEEL_RELEASE;
import static org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem.SLIDE_IN_POSE;
import static org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem.SLIDE_OUT_POSE;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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

    @Override
    public void runOpMode() throws InterruptedException {

        // Subsystems
        // Create new instances of classes, including subsystems, and assign to a variable

        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
        Sensors sensorsSub = new Sensors(hardwareMap);
        BucketSubsystem bucketSub = new BucketSubsystem(hardwareMap);
        IntakeSubsystem intakeSub = new IntakeSubsystem(hardwareMap);
        SlidesSubsystem slidesSub = new SlidesSubsystem(hardwareMap);
        DriveSubsystem driveSub = new DriveSubsystem(hardwareMap);
        ClimbSubsystem climbSub = new ClimbSubsystem(hardwareMap);

        // Required to initialize the subsystems when starting the OpMode
        waitForStart();

        /* Reset the motor encoder position */
        // TODO Test the slide reset
        slidesSub.resetSlideEncoder();

        // TODO Test the climber reset
        climbSub.resetClimberEncoder();

        // TODO Test the Lift reset
        bucketSub.resetLiftEncoder();

        // While loop to keep the robot running
        while (opModeIsActive()) {

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
                intakeSub.setIntakeArm(ARM_POSE_UP);
            }

            if (gamepad1.b) {
                intakeSub.setIntakeArm(ARM_POSE_DOWN);
            }


            /*
            if (gamepad1.a) {
                intakeSub.incrementIntakeArm (-0.05);
            }

            if (gamepad1.b) {
                intakeSub.incrementIntakeArm (0.05);
            }

             */

            // TODO Test bucket servo
            if (gamepad1.x) {
                bucketSub.setBucket(BUCKET_DOWN_POSE);
            }
            if (gamepad1.y) {
                bucketSub.setBucket(BUCKET_UP_POSE);
            }

            // TODO Test the SlideSubsystem
            if (gamepad1.left_bumper) {
                slidesSub.setSlidePose(SLIDE_IN_POSE);
            }
            if (gamepad1.right_bumper) {
                slidesSub.setSlidePose(SLIDE_OUT_POSE);
            }

            // TODO Test the IntakeSubsystem
            // Use the right trigger to power the intake wheel (for picking up pieces)
            // Use the left trigger to reverse the intake wheel (for dropping pieces into the bucket)
            if (gamepad1.right_trigger > 0) {
                intakeSub.powerIntakeWheel(gamepad1.right_trigger * WHEEL_INTAKE); // Scale power with trigger value
            } else if (gamepad1.left_trigger > 0) {
                intakeSub.powerIntakeWheel(gamepad1.left_trigger * WHEEL_RELEASE); // Scale power and reverse direction
            } else {
                intakeSub.powerIntakeWheel(0); // Stop the intake wheel
            }

            /* TODO Test BucketSubsystem Lift */
            if (gamepad1.dpad_right) {
                bucketSub.setLift(HIGH_BASKET);
            }
            if (gamepad1.dpad_left) {
                bucketSub.setLift(LOW_BASKET);
            }
            if (gamepad1.dpad_down) {
                bucketSub.setLift(LIFT_DOWN);

            }


            /*
             * OPERATOR INPUT MAPPING
             * Map methods (actions) from the subsystems to gamepad inputs for the second controller
             * See `ControllerMapping.md` for gamepad field names
             */

            /* TODO Add and Test BucketSubsystem */
            if (gamepad2.a) {
                bucketSub.setBucket(BUCKET_DOWN_POSE);
            }
            if (gamepad2.b) {
                bucketSub.setBucket(BUCKET_UP_POSE);
            }

            /* TODO Test ClimberSubsystem */
            if (gamepad2.dpad_up) {
                climbSub.setClimber(climbSub.CLIMB_UP);
            } else if (gamepad2.dpad_down) {
                climbSub.setClimber(climbSub.CLIMB_DOWN);
            } else {
                climbSub.setClimber(0);
            }

            // TODO Test IntakeSubsystem version 2
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
            // TODO Add manual power for SlideSubsystem
            if (gamepad2.x) {
//                slidesSub.setSlidePose(SLIDE_POSE_IN);
            }
            if (gamepad2.y) {
//                slidesSub.setSlidePose(SLIDE_POSE_OUT);
            }

            /* Add telemetry for slide encoder */
            telemetry.addData("Intake Wheel Power",intakeSub.intakeWheel.getPower());
            telemetry.addData("Intake Arm Servo",intakeSub.intakeArm.getPosition());
            telemetry.addData("Bucket Servo",bucketSub.bucketServo.getPosition());
            telemetry.addData("Slide Encoder",slidesSub.slide.getCurrentPosition());
            telemetry.addData("Bucket Lift Encoder",bucketSub.lift.getCurrentPosition());

            /* Add telemetry for slide touch sensor to reset the encoder to zero when it touches */
            telemetry.addData("Slide Touch",sensorsSub.slideTouch.isPressed());

            telemetry.update();

        } // end of while loop

    } // end of runOpMode method

} // end of the class