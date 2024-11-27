package org.firstinspires.ftc.teamcode.drive;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.util.Encoder;

import java.util.Arrays;
import java.util.List;

@Config
public class RoadrunnerOneThreeDeads extends ThreeTrackingWheelLocalizer {
    // GoBilda 8192 CPR Encoder Specifications
    public static double TICKS_PER_REV = 8192;
    
    // GoBilda Odometry Wheel Diameter
    public static double WHEEL_RADIUS = 1.46 / 2; // 1.46 inch diameter, radius is half
    
    // Lateral distance between parallel wheels
    public static double LATERAL_DISTANCE = 10; // Adjust to your specific robot width
    
    // Distance of perpendicular wheel from robot center
    public static double PERPENDICULAR_WHEEL_OFFSET = 5.5; // Adjust based on your pod placement
    
    private Encoder leftEncoder, rightEncoder, perpEncoder;

   private Telemetry telemetry; // Add Telemetry member

    public RoadrunnerOneThreeDeads(HardwareMap hardwareMap, Telemetry telemetry) {
        super(Arrays.asList(
            new Pose2d(0, LATERAL_DISTANCE / 2, 0), // Left parallel encoder
            new Pose2d(0, -LATERAL_DISTANCE / 2, 0), // Right parallel encoder
            new Pose2d(PERPENDICULAR_WHEEL_OFFSET, 0, Math.PI / 2) // Perpendicular encoder
        ));

        this.telemetry = telemetry; // Initialize Telemetry

        // Initialize encoders (update names to match your actual configuration)
//        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftOdo"));
//        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightOdo"));
//        perpEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "perpOdo"));

        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftFront"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightFront"));
        perpEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftBack"));


        // Set encoder directions - may need to adjust based on your specific setup
        leftEncoder.setDirection(Encoder.Direction.REVERSE);
        // rightEncoder direction depends on your specific mounting
    }
    
    @NonNull
    @Override
    public List<Double> getWheelPositions() {
        return Arrays.asList(
            encoderTicksToInches(leftEncoder.getCurrentPosition()),
            encoderTicksToInches(rightEncoder.getCurrentPosition()),
            encoderTicksToInches(perpEncoder.getCurrentPosition())
        );
    }
    
    @NonNull
    @Override
    public List<Double> getWheelVelocities() {
        return Arrays.asList(
            encoderTicksToInches(leftEncoder.getRawVelocity()),
            encoderTicksToInches(rightEncoder.getRawVelocity()),
            encoderTicksToInches(perpEncoder.getRawVelocity())
        );
    }

    @Override
    public void update() {
        super.update();

        // Add telemetry for encoder positions
//        telemetry.addData("Left Encoder", leftEncoder.getCurrentPosition());
//        telemetry.addData("Right Encoder", rightEncoder.getCurrentPosition());
//        telemetry.addData("Perp Encoder", perpEncoder.getCurrentPosition());

//        telemetry.update(); // Send telemetry data to Driver Station
    }

    public double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * ticks / TICKS_PER_REV;
    }
}