package frc.robot.subsystems.vision;

import java.lang.Cloneable;

public class AprilTagVisionIOInputsAutoLogged extends AprilTagVisionIO.AprilTagVisionIOInputs implements Cloneable {
  public AprilTagVisionIOInputsAutoLogged clone() {
    AprilTagVisionIOInputsAutoLogged copy = new AprilTagVisionIOInputsAutoLogged();
    copy.estimatedPoseMeters = this.estimatedPoseMeters;
    copy.seenTagIDs = this.seenTagIDs.clone();
    copy.tagPosesMeters = this.tagPosesMeters.clone();
    copy.lastMeasurementTimestampSecs = this.lastMeasurementTimestampSecs;
    return copy;
  }
}
