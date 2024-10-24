package org.firstinspires.ftc.teamcode.subsystems;
import com. quilcomm.robotcore.hardware.dcmotor;
import com. quilcomm.robotcore.hardware.dcmotor;
import com. quilcomm.robotcore.hardware.hardwaremap


public class SlidesSubsystem {


    private final dcmotor somemotor;

    public SlidesSubsystem(hardwaremap) {
        somemotor = hardwaremap.get(dcmotor.class, "leftback");
        somemotor.setdirection(dcmotor.direction.forward);
        somemotor.setpower(0);

    }



    public void rotatemotorreverse(double power) {
        somemotor.setpower(-power);
    }


    public void stopmotor() {
        somemotor(0);
    }


    public void incrementmotor(double rotsomedegrees) {

    }

} //end of class