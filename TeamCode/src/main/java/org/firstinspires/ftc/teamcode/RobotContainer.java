package org.firstinspires.ftc.teamcode;


import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.ARM_POSE_INTAKE;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.ARM_POSE_RELEASE;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.WHEEL_INTAKE;
import static org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem.WHEEL_RELEASE;
import static org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem.SLIDE_POSE_IN;
import static org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem.SLIDE_POSE_OUT;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
//import org.firstinspires.ftc.teamcode.sensors.Sensors;
//import org.firstinspires.ftc.teamcode.subsystems.BucketSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.ClimbSubsystem;
//import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.SlidesSubsystem;

// @Disabled

@TeleOp(group = "drive", name = "TeleOp")

public class RobotContainer extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        // Subsystems
        // Create new instances of classes, including subsystems, and assign to a variable

//        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);
//        Sensors sensorsSub = new Sensors(hardwareMap);
//        BucketSubsystem bucketSub = new BucketSubsystem(hardwareMap);
        IntakeSubsystem intakeSub = new IntakeSubsystem(hardwareMap);
        SlidesSubsystem slidesSub = new SlidesSubsystem(hardwareMap);
//        DriveSubsystem driveSub = new DriveSubsystem(hardwareMap);
//        ClimbSubsystem climbSub = new ClimbSubsystem(hardwareMap);

        // Required to initialize the subsystems when starting the OpMode
        waitForStart();

        /* Reset the motor encoder position */

        // TODO Test the reset
        slidesSub.resetSlideEncoder();
        // TODO add a reset for climb

        // While loop to keep the robot running
        while (opModeIsActive()) {

            // FIXME RC2.Replace these with button presses with current Methods (actions in Subsystems).
            /*
            * Map methods from the subsystems to gamepad inputs
            * See `ControllerMapping.md` for gamepad field names
            */


            /* TODO Uncomment for CompBot
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );
            */

            //
            if (gamepad1.a) {
                intakeSub.setIntakeArmPosition(ARM_POSE_INTAKE);
            }

            //
            if (gamepad1.b) {
                intakeSub.setIntakeArmPosition(ARM_POSE_RELEASE);
            }

            // TODO Test the SlideSubsystem
            /* Use the X button to incrementally move the slide motor in a reverse direction
            * Use the Y button to incrementally move the slide motor in a forward direction */
            if (gamepad1.x) {
                slidesSub.setSlidePose(SLIDE_POSE_IN);
            }
            if (gamepad1.y) {
                slidesSub.setSlidePose(SLIDE_POSE_OUT); // TODO determine experimentally
            }

            // TODO Test the IntakeSubsystem
            /* Use the right bumper to Power Intake Wheel (for picking up pieces)
            * Use the left bumper to reverse Power Intake Wheel (for dropping pieces into the bucket) */
            if (gamepad1.right_bumper) {
                intakeSub.powerIntakeWheel(WHEEL_INTAKE);
            } else if (gamepad1.left_bumper) {
                intakeSub.powerIntakeWheel(WHEEL_RELEASE);
            } else {
                intakeSub.powerIntakeWheel(0);
            }

            // Add telemetry for slide encoder
            int encoderPosition = slidesSub.slideMotor.getCurrentPosition();
            telemetry.addData("Slide Encoder", encoderPosition);

            telemetry.update();

        } // end of while loop

    } // end of runOpMode method

} // end of the class