package frc.robot.subsystems.vision;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;
import frc.robot.utils.vision.TimestampedVisionPose;
import frc.robot.utils.vision.VisionPoseAcceptor;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static frc.robot.Constants.Vision.FRONT_CAMERA_POSE;


public class AprilTagVision extends SubsystemBase {
    private final AprilTagVisionIO frontCameraIO;
    private final AprilTagVisionIOInputsAutoLogged frontCameraInputs = new AprilTagVisionIOInputsAutoLogged();

    private final Consumer<TimestampedVisionPose> visionPoseConsumer;
    private final VisionPoseAcceptor poseAcceptor;

    private TimestampedVisionPose frontPose =
            new TimestampedVisionPose(-1, new Pose2d(), new int[0], new Pose2d[0], true);

    public AprilTagVision(
            AprilTagVisionIO frontCameraIO,
            Consumer<TimestampedVisionPose> visionPoseConsumer,
            VisionPoseAcceptor poseAcceptor) {
        this.frontCameraIO = frontCameraIO;
        this.visionPoseConsumer = visionPoseConsumer;
        this.poseAcceptor = poseAcceptor;
    }

    @Override
    public void periodic() {
        // TODO: need to change if one camera is stationary
        frontCameraIO.setPoseOffset(
                new Pose3d(
                        FRONT_CAMERA_POSE.getX(),
                        FRONT_CAMERA_POSE.getY(),
                        FRONT_CAMERA_POSE.getZ(),
                        FRONT_CAMERA_POSE.getRotation())
        );

        frontCameraIO.updateInputs(frontCameraInputs);
        Logger.processInputs("Vision/FrontCamera", frontCameraInputs);
        if (frontCameraInputs.lastMeasurementTimestampSecs > frontPose.timestampSecs()) {
            frontPose = new TimestampedVisionPose(
                    frontCameraInputs.lastMeasurementTimestampSecs,
                    frontCameraInputs.estimatedPoseMeters,
                    frontCameraInputs.seenTagIDs,
                    frontCameraInputs.tagPosesMeters,
                    true);
        }

        Optional<TimestampedVisionPose> latestPose = getEstimatedPose();
        latestPose.ifPresent(visionPose -> Logger.recordOutput("Vision/EstimatedPose", visionPose.poseMeters()));
        latestPose.ifPresent(visionPoseConsumer);
    }

    /**
     * Gets the estimated pose by fusing individual computed poses from each camera.
     * Returns null if no tags seen, in simulation, or if the elevator is moving
     * too fast
     */
    public Optional<TimestampedVisionPose> getEstimatedPose() {
        boolean useFrontPose = poseAcceptor.shouldAcceptVision(frontPose);

        if (useFrontPose) {
            int[] allTagIDs = Arrays.copyOf(frontPose.seenTagIDs(), frontPose.getNumTagsSeen());
            Pose2d[] allTagPoses = Arrays.copyOf(frontPose.tagPosesMeters(), frontPose.getNumTagsSeen());

            return Optional.of(new TimestampedVisionPose(
                    frontPose.timestampSecs() / 2,
                    frontPose.poseMeters(),
                    allTagIDs,
                    allTagPoses,
                    true));
        } else if (useFrontPose) return Optional.of(frontPose);
        else return Optional.empty();
    }
}
