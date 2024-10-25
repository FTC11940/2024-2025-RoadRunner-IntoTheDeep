    package org.firstinspires.ftc.teamcode.sensors;

    import com.qualcomm.robotcore.hardware.ColorSensor;
    import com.qualcomm.robotcore.hardware.HardwareMap;

    public class colorSensor {
        ColorSensor colorOne;

        public colorSensor(HardwareMap hardwareMap) {
            colorOne = hardwareMap.get(ColorSensor.class, "colorOne");
    }

        public int getColor() {
            return colorOne.red();
    }

    public ColorSensorData getColorSensorData() {
        return new ColorSensorData(
                colorOne.red(),
                colorOne.green(),
                colorOne.blue(),
                colorOne.alpha()
        );
    }

    public static class ColorSensorData {
        public final int red;
        public final int green;
        public final int blue;
        public final int alpha;

        public ColorSensorData(int red, int green, int blue, int alpha) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpha = alpha;
        }
    } 

} //End of Class

