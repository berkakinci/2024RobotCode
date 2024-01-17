package frc.robot.subsystems;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.LimelightHelpers;

public class LimelightSubsystem extends SubsystemBase {
    public double tx = 0;
    public double ty = 0;
    public double ta = 0;
    public double apriltagID;
    public double pipelineID;

    public LimelightSubsystem(){
        
    }

    @Override
    public void periodic(){
        tx = LimelightHelpers.getTX("limelight");
        ty = LimelightHelpers.getTY("limelight");
        ta = LimelightHelpers.getTA("limelight");
        apriltagID = LimelightHelpers.getFiducialID("limelight");
        pipelineID = LimelightHelpers.getCurrentPipelineIndex("limelight");


    }

    public static double shootingAngle(double apriltagID, double tx, double ty, double ta) {
        double angle = 0;

        angle = ()

        return angle;
    }


}
