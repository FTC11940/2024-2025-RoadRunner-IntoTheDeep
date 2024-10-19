package org.firstinspires.ftc.teamcode.drive.opmode;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.FakeMotorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.FakeServoSubsystem;

/**
 * This is a simple teleop routine for testing localization. Drive the robot around like a normal
 * teleop routine and make sure the robot's estimated pose matches the robot's actual pose (slight
 * errors are not out of the ordinary, especially with sudden drive motions). The goal of this
 * exercise is to ascertain whether the localizer has been configured properly (note: the pure
 * encoder localizer heading may be significantly off if the track width has not been tuned).
 */
@TeleOp(group = "drive")
public class LocalizationTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        FakeMotorSubsystem fake = new FakeMotorSubsystem(hardwareMap);
        FakeServoSubsystem fakeServo = new FakeServoSubsystem(hardwareMap);
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);


        drive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (!isStopRequested()) {
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            // Raise arm with gamepad A button
            if (gamepad1.a) {
                fake.raiseArm(0.5); // Raise arm with 0.5 power
            } else if (gamepad1.b) {
                fake.lowerArm(0.5); // Lower arm with 0.5 power
            } else {
                fake.stopArm(); // Stop arm if neither button is pressed
            }

            // Open gripper with gamepad Y button
            if (gamepad1.y) {
                fakeServo.openGripper(); // Open gripper
            } else if (gamepad1.x) {
                fakeServo.closeGripper(); // Close gripper
            }

            drive.update();

            Pose2d poseEstimate = drive.getPoseEstimate();
            telemetry.addData("x", poseEstimate.getX());
            telemetry.addData("y", poseEstimate.getY());
            telemetry.addData("heading", poseEstimate.getHeading());
            telemetry.update();
        }
    }
}
