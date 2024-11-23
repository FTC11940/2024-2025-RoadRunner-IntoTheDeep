package org.firstinspires.ftc.teamcode.auton;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.drive.RoadrunnerOneThreeDeads;

@Autonomous(name = "Drive Forward 10in")

public class TenForwardAuton extends LinearOpMode {
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

        // Build trajectory - move forward 10 inches
        Trajectory forwardTrajectory = drive.trajectoryBuilder(startPose)
                .forward(10)
                .build();

        // Wait for the driver to press start
        waitForStart();

        if (isStopRequested()) return;

        // Execute the trajectory
        drive.followTrajectory(forwardTrajectory);

        // Add telemetry for final position
        Pose2d finalPose = drive.getPoseEstimate();
        telemetry.addData("Final X", finalPose.getX());
        telemetry.addData("Final Y", finalPose.getY());
        telemetry.addData("Final Heading", Math.toDegrees(finalPose.getHeading()));
        telemetry.update();
    }
}