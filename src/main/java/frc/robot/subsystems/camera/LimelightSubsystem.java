package frc.robot.subsystems.camera;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class LimelightSubsystem extends SubsystemBase{

    public LimelightSubsystem(){}

    private LimelightHelpers.LimelightResults llresults = LimelightHelpers.getLatestResults("");

    @Override
    public void periodic() {
        llresults = LimelightHelpers.getLatestResults("");

        double[] botpose = llresults.results.botpose;
    }
    
}
