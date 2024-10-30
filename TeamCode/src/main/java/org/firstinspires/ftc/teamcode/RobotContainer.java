package org.firstinspires.ftc.teamcode;


import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.subsystems.PracticeMotorSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.PracticeServoSubsystem;


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
        ClimbSubystem climbSub = new ClimbSubystem(hardwareMap);


        // Required to initialize the subsystems when starting the OpMode
        waitForStart();


        // While loop to keep the robot running
        while (opModeIsActive()) {

            // FIXME RC2.Replace these with button presses with current Methods (actions in Subsystems).
            /*
            * Map methods from the subsystems to gamepad inputs
            * See `ControllerMapping.md` for gamepad field names
            */


            //
            drive.setWeightedDrivePower(
                    new Pose2d(
                            -gamepad1.left_stick_y,
                            -gamepad1.left_stick_x,
                            -gamepad1.right_stick_x
                    )
            );

            //
            if (gamepad1.a) {
            }

            //
            if (gamepad1.b) {
            }

            //
            if (gamepad1.x) {
            }

            //
            if (gamepad1.y) {
            }



            telemetry.update();

        } // end of while loop

    } // end of runOpMode method

} // end of the class