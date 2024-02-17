package frc.robot.subsystems.vision;

import java.lang.Cloneable;
import java.lang.Override;
import org.littletonrobotics.junction.LogTable;
import org.littletonrobotics.junction.inputs.LoggableInputs;

public class AprilTagVisionIOInputsAutoLogged extends AprilTagVisionIO.AprilTagVisionIOInputs implements LoggableInputs, Cloneable {
  @Override
  public void toLog(LogTable table) {
    table.put("EstimatedPoseMeters", estimatedPoseMeters);
    table.put("SeenTagIDs", seenTagIDs);
    table.put("TagPosesMeters", tagPosesMeters);
    table.put("LastMeasurementTimestampSecs", lastMeasurementTimestampSecs);
  }

  @Override
  public void fromLog(LogTable table) {
    estimatedPoseMeters = table.get("EstimatedPoseMeters", estimatedPoseMeters);
    seenTagIDs = table.get("SeenTagIDs", seenTagIDs);
    tagPosesMeters = table.get("TagPosesMeters", tagPosesMeters);
    lastMeasurementTimestampSecs = table.get("LastMeasurementTimestampSecs", lastMeasurementTimestampSecs);
  }

  public AprilTagVisionIOInputsAutoLogged clone() {
    AprilTagVisionIOInputsAutoLogged copy = new AprilTagVisionIOInputsAutoLogged();
    copy.estimatedPoseMeters = this.estimatedPoseMeters;
    copy.seenTagIDs = this.seenTagIDs.clone();
    copy.tagPosesMeters = this.tagPosesMeters.clone();
    copy.lastMeasurementTimestampSecs = this.lastMeasurementTimestampSecs;
    return copy;
  }
}
