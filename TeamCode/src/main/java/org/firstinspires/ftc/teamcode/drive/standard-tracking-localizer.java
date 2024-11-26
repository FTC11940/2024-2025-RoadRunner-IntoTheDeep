package org.firstinspires.ftc.teamcode.drive;

import androidx.annotation.NonNull;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.localization.ThreeTrackingWheelLocalizer;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.util.Encoder;

import java.util.Arrays;
import java.util.List;

@Config
public class StandardTrackingWheelLocalizer extends ThreeTrackingWheelLocalizer {
    // Precise Encoder Specifications
    public static double TICKS_PER_REV = 8192; // GoBilda 8192 CPR Encoder
    public static double WHEEL_RADIUS = 1.46 / 2; // inches
    public static double GEAR_RATIO = 1; // direct drive

    // Precise Robot Measurements
    public static double LATERAL_DISTANCE = 10.25; // inches between parallel wheels
    public static double FORWARD_OFFSET = 5.5; // inches, perpendicular wheel offset

    private Encoder leftEncoder, rightEncoder, frontEncoder;

    private List<Integer> lastEncPositions, lastEncV