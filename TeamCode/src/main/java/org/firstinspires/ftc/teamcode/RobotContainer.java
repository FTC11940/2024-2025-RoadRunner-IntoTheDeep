package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem.Constants.BUCKET_DOWN;
import static org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem.Constants.BUCKET_MID;
import static org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem.Constants.BUCKET_UP;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.Constants.ARM_POSE_DOWN;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.Constants.ARM_POSE_MID;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.Constants.ARM_POSE_UP;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.Constants.WHEEL_INTAKE;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.Constants.WHEEL_RELEASE;

import android.annotation.SuppressLint;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.RoadrunnerOneThreeDeads;
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
//        DriveSubsystem driveSub = new DriveSubsystem(hardwareMap);
        RoadrunnerOneThreeDeads localizer = new RoadrunnerOneThreeDeads(hardwareMap, telemetry);
        ClimbSubsystem climbSub = new ClimbSubsystem(hardwareMap);

        // Added by Claude
        bucketSub.setIntakeSubsystem(intakeSub);
        intakeSub.setBucketSubsystem(bucketSub);


        // Required to initialize the subsystems when starting the OpMode
        waitForStart();

        /* Reset the motor encoder position after starting the OpMode */
        slidesSub.resetSlideEncoder();
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


            double wheelPower = intakeSub.smartPowerIntakeWheel(gamepad1.right_trigger,gamepad1.left_trigger);

            if (gamepad1.a) {
                intakeSub.setIntakeArm(ARM_POSE_DOWN);
            }

            if (gamepad1.b) {
                intakeSub.setIntakeArm(ARM_POSE_MID);
            }

            /*if (gamepad1.x) {
            }*/
            if (gamepad1.y) {
                intakeSub.setIntakeArm(ARM_POSE_UP);
            }

            if (gamepad1.left_bumper) {
                slidesSub.setSlideIn();
            } else {
                slidesSub.powerSlide(0);

                if (gamepad1.right_bumper) {
                    slidesSub.setSlideOut();
                } else {
                    slidesSub.powerSlide(0);
                }

            }

            /*
             * OPERATOR INPUT MAPPING
             * Map methods (actions) from the subsystems to gamepad inputs for the second controller
             * See `ControllerMapping.md` for gamepad field names
             */

            if (gamepad2.a) {
                bucketSub.setBucket(BUCKET_DOWN);
            }
            if (gamepad2.y) {
                bucketSub.setBucket(BUCKET_UP);
            }
            if (gamepad2.b) {
                bucketSub.setBucket(BUCKET_MID);
            }

            // Lift Functions
            /* Bind cammands related to the bucketSub (BucketSubstem) to the gamepad buttons
             The dpad_up should move the lift up increments of 50 encoder ticks and set to hold power,
             the dpad_down should move the lift down increments of 50 encoder ticks and set power to zero,
             the dpad_left should move the lift to the low basket position with holding power after reaching the pose,
             the dpad_right should move the lift to the high basket position with holding power after reaching the pose*/

            if (gamepad2.dpad_up) {
                bucketSub.moveLiftUp();
            } else if (gamepad2.dpad_left) {
                bucketSub.setLiftLow();
            } else if (gamepad2.dpad_right) {
                bucketSub.setLiftHigh();
            } else if (gamepad2.dpad_down) { // Changed to else if
                bucketSub.moveLiftDown();
            }

            if (gamepad2.back) {
                bucketSub.tareLift();
            }

<<<<<<< HEAD
            if (gamepad2.start) {
                climbSub.powerClimber(1);
                } else {
                climbSub.stopClimber();
            }
=======
>>>>>>> parent of 7f13a3a (update to climb and bucket lift)
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

            telemetry.clearAll(); // Clear previous telemetry data

            // Intake Subsystem
            telemetry.addLine("--- INTAKE ---");
            telemetry.addData("Wheel Power", String.format("%.2f", intakeSub.intakeWheel.getPower()));
            telemetry.addData("", intakeSub.getIntakeArmStatus().getDescription());
            telemetry.addData("Calculated Wheel Power", String.format("%.2f", wheelPower));
            telemetry.addData("Triggers", String.format("R: %.2f, L: %.2f",
                    gamepad1.right_trigger, gamepad1.left_trigger));

            // Slide Subsystem
            telemetry.addLine("--- SLIDE ---");
            telemetry.addData("Slide", String.format("%s, (%d)",
                    slidesSub.getSlideStatus(), slidesSub.slide.getCurrentPosition()));
            telemetry.addData("Slide Touch Sensor", sensors.isSlideTouchPressed());

            // Bucket Subsystem
            telemetry.addLine("--- BUCKET ---");

            telemetry.addData("Bucket Status", String.format("%s, (%.2f)",
                    bucketSub.getBucketStatus(), bucketSub.bucketServo.getPosition()));
            telemetry.addData("Lift Status", String.format("%s, (%d)",
                    bucketSub.getLiftStatus(), bucketSub.lift.getCurrentPosition()));
            telemetry.addData("Lift Motor Power", String.format("%.2f A",
                    bucketSub.lift.getPower()));

            // Other Data


            telemetry.update(); // Send telemetry data to DriverStation


            localizer.update();

            // Updates position of the lift motor periodically

            bucketSub.updateLift();

            telemetry.update();



        } // end of while loop

    } // end of runOpMode method

} // end of the class