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
    // Precise Odometry Specifications
    public static double TICKS_PER_REV = 8192; // GoBilda 8192 CPR Encoder
    public static double WHEEL_RADIUS = 1.46 / 2; // 1.46 inch diameter

    // Precise Robot Measurements
    public static double LATERAL_DISTANCE = 10.25; // Distance between parallel wheels
    public static double PERPENDICULAR_WHEEL_OFFSET = 5.5; // Perpendicular wheel offset

    private Encoder leftEncoder, rightEncoder, perpEncoder;

    public RoadrunnerOneThreeDeads(HardwareMap hardwareMap) {
        super(Arrays.asList(
                new Pose2d(0, LATERAL_DISTANCE / 2, 0), // Left parallel encoder
                new Pose2d(0, -LATERAL_DISTANCE / 2, 0), // Right parallel encoder
                new Pose2d(PERPENDICULAR_WHEEL_OFFSET, 0, Math.PI / 2) // Perpendicular encoder
        ));

        // Initialize dedicated odometry encoders
        leftEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "leftOdoEncoder"));
        rightEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "rightOdoEncoder"));
        perpEncoder = new Encoder(hardwareMap.get(DcMotorEx.class, "perpOdoEncoder"));

        // Set encoder directions carefully
        leftEncoder.setDirection(Encoder.Direction.REVERSE);
        rightEncoder.setDirection(Encoder.Direction.FORWARD);
        perpEncoder.setDirection(Encoder.Direction.REVERSE);
    }

    public RoadrunnerOneThreeDeads(HardwareMap hardwareMap, Telemetry telemetry) {
        super();
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

    public double encoderTicksToInches(double ticks) {
        return WHEEL_RADIUS * 2 * Math.PI * ticks / TICKS_PER_REV;
    }
}
