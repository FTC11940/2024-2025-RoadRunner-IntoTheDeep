package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.RoadrunnerOneThreeDeads;

@Autonomous(name = "Drive Forward and Strafe")
public class TenForwardStrafeLeft extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        // Initialize RoadRunner drive
        SampleMecanumDrive drive = new SampleMecanumDrive(hardwareMap);

        // Initialize our localizer
        RoadrunnerOneThreeDeads localizer = new RoadrunnerOneThreeDeads(hardwareMap, telemetry);
        drive.setLocalizer(localizer);

        // Start pose - assuming robot starts at the origin (0, 0) facing forward (0 degrees)
        Pose2d startPose = new Pose2d(0, 0, 0);
        drive.setPoseEstimate(startPose);

        // Build trajectory 1 - move forward 10 inches
        Trajectory forwardTrajectory = drive.trajectoryBuilder(startPose)
                .forward(10)
                .build();

        // Build trajectory 2 - strafe left 10 inches
        // Note: We use the end pose of the first trajectory as the start pose for the second
        Trajectory strafeTrajectory = drive.trajectoryBuilder(forwardTrajectory.end())
                .strafeLeft(10)
                .build();

        // Wait for the driver to press start
        waitForStart();

        if (isStopRequested()) return;

        // Execute the trajectories in sequence
        drive.followTrajectory(forwardTrajectory);
        drive.followTrajectory(strafeTrajectory);

        // Add telemetry for final position
        Pose2d finalPose = drive.getPoseEstimate();
        telemetry.addData("Final X", finalPose.getX());
        telemetry.addData("Final Y", finalPose.getY());
        telemetry.addData("Final Heading", Math.toDegrees(finalPose.getHeading()));
        telemetry.addData("Path Completed", "Success!");
        telemetry.update();
    }
}