package frc.robot.subsystems;
import java.util.List;

import org.photonvision.PhotonCamera;
import org.photonvision.proto.Photon;
import org.photonvision.targeting.PhotonTrackedTarget;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PhotonvisionSubsystem extends SubsystemBase{

    private result = null;

    private final PhotonCamera camera =
        new PhotonCamera("photonvision");

    public PhotonvisionSubsystem(){
    }

    @Override
    public void periodic(){
        var result = camera.getLatestResult();

        boolean hasTarget = result.hasTargets();

        List<PhotonTrackedTarget> targets = result.getTargets();

        if (hasTarget) {
            var imageCaptureTime = result.getTimestampSeconds();
            var camToTargetTrans = result.getBestTarget().getBestCameraToTarget();
            var camPose = Constants.kFarTargetPose.transformBy(camToTargetTrans.inverse());
            m_poseEstimator.addVisionMeasurement
        }


    }




    
}
