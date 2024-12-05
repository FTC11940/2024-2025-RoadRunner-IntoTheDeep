package org.firstinspires.ftc.teamcode;

import static org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem.Constants.BUCKET_DOWN;
import static org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem.Constants.BUCKET_MID;
import static org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem.Constants.BUCKET_UP;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.Constants.ARM_POSE_DOWN;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.Constants.ARM_POSE_MID;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.Constants.ARM_POSE_UP;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.Constants.WHEEL_INTAKE;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.Constants.WHEEL_RELEASE;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.RoadrunnerOneThreeDeads;
import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.sensors.Sensors;
import org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ClimbSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem;

// @Disabled

@TeleOp(group = "drive", name = "TeleOp")

public class RobotContainer extends LinearOpMode {

    private Sensors sensors;
    private BucketSubsystem bucketSub;
    private IntakeSubsystem intakeSub;
    private SlidesSubsystem slidesSub;
    private SampleMecanumDrive driveSub;
    private RoadrunnerOneThreeDeads roadRunner;
    private ClimbSubsystem climbSub;

    @Override

    public void runOpMode() throws InterruptedException {

        /* Subsystems */
        sensors = new Sensors(hardwareMap);
        bucketSub = new BucketSubsystem(hardwareMap,sensors);
        intakeSub = new IntakeSubsystem(hardwareMap,sensors);
        slidesSub = new SlidesSubsystem(hardwareMap,sensors);
        driveSub = new SampleMecanumDrive(hardwareMap);
        roadRunner = new RoadrunnerOneThreeDeads(hardwareMap,telemetry);
        climbSub = new ClimbSubsystem(hardwareMap);

        bucketSub.setIntakeSubsystem(intakeSub);
        intakeSub.setBucketSubsystem(bucketSub);

        // Required to initialize the subsystems when starting the OpMode
        waitForStart();

        /* Reset the motor encoder position after starting the OpMode */
        slidesSub.resetSlideEncoder();
        bucketSub.resetLiftEncoder();

        // While loop to keep the robot running
        while (opModeIsActive()) {

            /*
            bucketSub.setIntakeSubsystem(intakeSub);
            intakeSub.setBucketSubsystem(bucketSub);
            */

            /* Touch Sensors */
            slidesSub.resetSlideEncoderOnTouch();
            bucketSub.resetLiftEncoderOnTouch();

            /* Driver 1 Controls (Movement & Intake Arm) */
            // Driving controls
            driveSub.setWeightedDrivePower(new Pose2d(
                    -gamepad1.left_stick_y,
                    -gamepad1.left_stick_x,
                    -gamepad1.right_stick_x
            ));
            // Keep right trigger for slow drive mode as you have
            // Could add left trigger for medium speed (75%)
                        if (gamepad1.left_trigger > 0.5) {
                            gamepad1.left_stick_y *= 0.75;
                            gamepad1.left_stick_x *= 0.75;
                            gamepad1.left_stick_x *= 0.75;
                        }

            // Intake arm controls
            if (gamepad1.a) intakeSub.setIntakeArm(ARM_POSE_DOWN);
            if (gamepad1.b) intakeSub.setIntakeArm(ARM_POSE_MID);
            if (gamepad1.y) intakeSub.setIntakeArm(ARM_POSE_UP);

            // Intake wheel controls
            if (gamepad1.right_bumper) intakeSub.powerIntakeWheel(WHEEL_INTAKE);
            else if (gamepad1.left_bumper) intakeSub.powerIntakeWheel(WHEEL_RELEASE);
            else intakeSub.powerIntakeWheel(0);

            /* Driver 2 Controls (Scoring & Mechanisms) */
            // Bucket controls
            if (gamepad2.a) bucketSub.setBucket(BUCKET_DOWN);
            if (gamepad2.b) bucketSub.setBucket(BUCKET_MID);
            if (gamepad2.y) bucketSub.setBucket(BUCKET_UP);

            // Lift controls
            if (gamepad2.dpad_up) bucketSub.moveLiftUp();
            else if (gamepad2.dpad_left) bucketSub.setLiftLow();
            else if (gamepad2.dpad_right) bucketSub.setLiftHigh();
            else if (gamepad2.dpad_down) bucketSub.setLiftDown();

            // Slides control with Triggers
            slidesSub.controlSlides(gamepad2);

            // Utility controls
            if (gamepad2.back) bucketSub.tareLift();


            /* Use the right bumper to Power Intake Wheel (for picking up pieces)
             * Use the left bumper to reverse Power Intake Wheel (for dropping pieces into the bucket) */

            // End of Button Bindings

            telemetry.clearAll(); // Clear previous telemetry data

            // Intake Subsystem
            telemetry.addLine("--- INTAKE ---");
            telemetry.addData("Wheel Power",String.format("%.2f",intakeSub.intakeWheel.getPower()));
            telemetry.addData("",intakeSub.getIntakeArmStatus().getDescription());
            // Telemetry to return the Sensors enum status of the Sample

            // Slide Subsystem
            telemetry.addLine("--- SLIDE ---");
            telemetry.addData("Slide",String.format("%s, (%d)",slidesSub.getSlideStatus(),slidesSub.slide.getCurrentPosition()));
            telemetry.addData("Slide Touch Sensor",sensors.isSlideTouchPressed());

            // Bucket Subsystem
            telemetry.addLine("--- BUCKET ---");
            telemetry.addData("Bucket Status",String.format("%s, (%.2f)",bucketSub.getBucketStatus(),bucketSub.bucketServo.getPosition()));
            telemetry.addData("Lift Status",String.format("%s, (%d)",bucketSub.getLiftStatus(),bucketSub.lift.getCurrentPosition()));
            telemetry.addData("Lift Motor Power",String.format("%.2f A",bucketSub.lift.getPower()));

            bucketSub.updateLift();
            telemetry.update();

        } // end of while loop

    } // end of runOpMode method

}// end of the class