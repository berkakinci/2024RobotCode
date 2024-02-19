package frc.robot.utils.vision;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Timer;

import java.util.function.Supplier;

import static frc.robot.Constants.Vision.*;

public class VisionPoseAcceptor {
    private final Supplier<ChassisSpeeds> robotVelocitySupplier;

    public VisionPoseAcceptor(
            Supplier<ChassisSpeeds> robotVelocitySupplier) {
        this.robotVelocitySupplier = robotVelocitySupplier;
    }

    public boolean shouldAcceptVision(TimestampedVisionPose visionPose) {
        ChassisSpeeds robotVelocity = robotVelocitySupplier.get();
        // Do not accept poses that have too much delay
        if (Timer.getFPGATimestamp() - visionPose.timestampSecs() >= MAX_VISION_DELAY_SECS) return false;

        // Do not accept poses that see too little tags
        if (visionPose.getNumTagsSeen() < MIN_ACCEPTED_NUM_TAGS) return false;

        // Do not accept poses that have an average tag distance that is too far away
        if (visionPose.getAverageTagDistance() > MAX_ACCEPTED_AVG_TAG_DIST_METERS) return false;

        // Do not accept poses taken when the robot has too much rotational or translational velocity
        boolean rotatingTooFast = Math.abs(robotVelocity.omegaRadiansPerSecond) > MAX_ACCEPTED_ROT_SPEED_RAD_PER_SEC;
        boolean translatingTooFast =
                Math.hypot(robotVelocity.vxMetersPerSecond, robotVelocity.vyMetersPerSecond)
                        > MAX_ACCEPTED_LINEAR_SPEED_MPS;
        return !rotatingTooFast && !translatingTooFast;
    }
}
