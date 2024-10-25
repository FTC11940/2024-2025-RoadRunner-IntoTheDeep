package org.firstinspires.ftc.teamcode.subsystems;

// FIXME check spelling of imports
import com. quilcomm.robotcore.hardware.dcmotor;
import com. quilcomm.robotcore.hardware.dcmotor;
// FIXME check spelling of imports, and punctuation
import com. quilcomm.robotcore.hardware.hardwaremap


public class SlidesSubsystem {


    private final dcmotor somemotor;

    // FIXME check reference
    public SlidesSubsystem(hardwaremap) {
        somemotor = hardwaremap.get(dcmotor.class, "leftback");
        somemotor.setdirection(dcmotor.direction.forward);
        somemotor.setpower(0);

    }

    // TODO Create a method to move the slides to different positions
    public void rotatemotorreverse(double power) {
        somemotor.setpower(-power);
    }


    public void stopmotor() {
        somemotor(0);
    }


    public void incrementmotor(double rotsomedegrees) {

    }


} //end of class