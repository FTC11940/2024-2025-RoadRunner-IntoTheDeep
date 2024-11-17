// First, create a SubsystemManager to handle initialization and cross-subsystem communication
public class SubsystemManager {
private static SubsystemManager instance;

    // All subsystems are managed here
    private IntakeSubsystem intakeSub;
    private BucketSubsystem bucketSub;
    private SlidesSubsystem slidesSub;
    private DriveSubsystem driveSub;
    private ClimbSubsystem climbSub;
    
    // Private constructor to prevent multiple instances
    private SubsystemManager() {}
    
    // Get the single instance of the manager
    public static SubsystemManager getInstance() {
        if (instance == null) {
            instance = new SubsystemManager();
        }
        return instance;
    }
    
    // Initialize all subsystems
    public void initializeSubsystems(HardwareMap hardwareMap, Telemetry telemetry) {
        // Create sensors first since other subsystems depend on it
        Sensors sensors = new Sensors(hardwareMap);
        
        // Initialize all subsystems
        intakeSub = new IntakeSubsystem(hardwareMap, sensors);
        bucketSub = new BucketSubsystem(hardwareMap, telemetry);
        slidesSub = new SlidesSubsystem(hardwareMap, sensors);
        driveSub = new DriveSubsystem(hardwareMap);
        climbSub = new ClimbSubsystem(hardwareMap);
        
        // Set up cross-subsystem references
        bucketSub.setIntakeSubsystem(intakeSub);
        
        // Verify initialization
        if (!verifyInitialization()) {
            throw new RuntimeException("Subsystem initialization failed!");
        }
    }
    
    // Verify all subsystems are properly initialized
    private boolean verifyInitialization() {
        return intakeSub != null && 
               bucketSub != null && 
               slidesSub != null && 
               driveSub != null && 
               climbSub != null;
    }
    
    // Getters for subsystems with null checks
    public IntakeSubsystem getIntakeSubsystem() {
        if (intakeSub == null) {
            throw new RuntimeException("IntakeSubsystem not initialized!");
        }
        return intakeSub;
    }
    
    public BucketSubsystem getBucketSubsystem() {
        if (bucketSub == null) {
            throw new RuntimeException("BucketSubsystem not initialized!");
        }
        return bucketSub;
    }
    
    // Add getters for other subsystems...
}

// Modified RobotContainer class
@TeleOp(group = "drive", name = "TeleOp")
public class RobotContainer extends LinearOpMode {
private SubsystemManager subsystemManager;

    @Override
    public void runOpMode() throws InterruptedException {
        try {
            // Get the subsystem manager instance
            subsystemManager = SubsystemManager.getInstance();
            
            // Initialize all subsystems
            subsystemManager.initializeSubsystems(hardwareMap, telemetry);
            
            // Get references to subsystems (now guaranteed to be initialized)
            IntakeSubsystem intakeSub = subsystemManager.getIntakeSubsystem();
            BucketSubsystem bucketSub = subsystemManager.getBucketSubsystem();
            
            // Wait for start
            waitForStart();
            
            while (opModeIsActive()) {
                try {
                    // Your existing gamepad control code here
                    // Now using subsystems through the manager
                    
                    // Example of safe cross-subsystem operation
                    if (gamepad1.b) {
                        if (bucketSub.getLiftStatus() == BucketSubsystem.LiftStatus.DOWN) {
                            intakeSub.groupIntakeArmUp();
                        } else {
                            telemetry.addData("Warning", "Cannot move intake arm up - lift not down");
                        }
                    }
                    
                } catch (Exception e) {
                    // Log the error but don't crash the robot
                    telemetry.addData("Error in loop", e.getMessage());
                    telemetry.update();
                }
            }
            
        } catch (Exception e) {
            // Handle initialization errors
            telemetry.addData("Fatal Error", "Failed to initialize: " + e.getMessage());
            telemetry.update();
        }
    }
}

// Modified IntakeSubsystem - example of improved status checking
public class IntakeSubsystem {
// Existing code...

    public void groupIntakeArmUp() {
        try {
            // Check subsystem health
            if (!isSubsystemHealthy()) {
                telemetry.addData("Warning", "Intake subsystem not healthy");
                return;
            }
            
            // Check if movement is safe
            if (intakeWheel.getPower() > 0.05) {
                telemetry.addData("Warning", "Cannot move arm while wheel is running");
                return;
            }
            
            // Move the arm
            intakeArm.setPosition(ARM_POSE_UP);
            
        } catch (Exception e) {
            telemetry.addData("Intake Error", e.getMessage());
        }
    }
    
    private boolean isSubsystemHealthy() {
        return intakeArm != null && 
               intakeWheel != null && 
               Math.abs(intakeArm.getPosition()) <= 1.0;
    }
}