package frc.robot.utils;

import java.util.function.DoubleSupplier;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Rotation3d;
import edu.wpi.first.math.geometry.Transform3d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.util.sendable.SendableBuilder;
import frc.robot.Constants;

public class DriverHelper implements Sendable {
  public DoubleSupplier headingX = () -> this.getHeadingX();
  public DoubleSupplier headingY = () -> this.getHeadingY();
  public DoubleSupplier armAngle = () -> this.getArmAngle();

  private DoubleSupplier driverHeadingX;
  private DoubleSupplier driverHeadingY;

  private boolean passthrough = true;
  private Pose3d targetPose = new Pose3d();
  private Pose3d lastBotPose = new Pose3d();
  private Pose3d shooterPose = new Pose3d();
  private Pose3d launchDirection = new Pose3d();
  private Pose2d headingDirection = new Pose2d();

  public double driverDeadband = Constants.OperatorConstants.ROTATION_DEADBAND;

  public DriverHelper(DoubleSupplier driverHeadingXSupplier,
                       DoubleSupplier driverHeadingYSupplier) {
    driverHeadingX = driverHeadingXSupplier;
    driverHeadingY = driverHeadingYSupplier;
  }

  public void setPassThrough() {
    passthrough = true;
  }

  public void setTargetPose(Pose3d target) {
    passthrough = false;
    targetPose = target;
  }

  public void updateAim(Pose2d botPose2d) {
    var botPose = new Pose3d(botPose2d);
    lastBotPose = botPose;
    var armRotation = new Transform3d(0.0, 0.0, 0.0,
                                      new Rotation3d(0.0, -getArmAngle(), 0.0)); // Note: using last iteration result; This could become a stability problem.
    shooterPose = botPose.plus(Constants.Arm.botToPivot)
                         .plus(armRotation)
                         .plus(Constants.Arm.pivotToShooter); // Note: Order is important!
    var shooterPoseUpright = new Pose3d(shooterPose.getTranslation(), new Rotation3d());
    launchDirection = targetPose.relativeTo(shooterPoseUpright);
    headingDirection = launchDirection.toPose2d()
                                      .rotateBy(new Rotation2d(Constants.TAU/2)); // Robot shoots in the -X direction.
  }

  public double getHeadingX() {
    var driverInput = -driverHeadingX.getAsDouble();
    if(passthrough) { 
      return driverInput; };
    if(Math.abs(driverInput) > driverDeadband) {
      return driverInput; }

    var heading = headingDirection.getTranslation();
    var helperInput = heading.getX()/heading.getNorm();

    if(!Double.isFinite(helperInput)) {
      helperInput = 0.0; }
    return helperInput;
  }

  public double getHeadingY() {
    var driverInput = -driverHeadingY.getAsDouble();
    if(passthrough) { 
      return driverInput; };
    if(Math.abs(driverInput) > driverDeadband) {
      return driverInput; }

    var heading = headingDirection.getTranslation();
    var helperInput = heading.getY()/heading.getNorm();

    if(!Double.isFinite(helperInput)) {
      helperInput = 0.0; }
    return helperInput;
  }

  public double getArmAngle() {
    if(passthrough) {
      return Constants.Arm.kStartingPos; } // We really shouldn't be called now!
    double armShooterAngleOffset = Constants.Arm.armToShooterAngle;
    double armAngle = (Constants.TAU/2 - ballisticTrajectoryLaunchAngle()) + armShooterAngleOffset;

    if(!Double.isFinite(armAngle)) {
      armAngle = Constants.Arm.kStartingPos; }
    return armAngle;
  }

  private double ballisticTrajectoryLaunchAngle() {
    // FIXME: Solution (no air resistance) is already well known.  See https://en.wikipedia.org/wiki/Projectile_motion#Angle_θ_required_to_hit_coordinate_(x,_y)
    // FIXME: For air resistance: Since we only need a single point solution, with similar launch speed, similar environment, we can probably get away with adding a "drag" coefficient to distance we aim.
    // FIXME: Using straight line trajectory for now.
    var launchDirectionXYProjection = launchDirection.toPose2d();
    var launchDirectionXYProjLength = launchDirectionXYProjection.getTranslation().getNorm();
    var launchDirectionAngleToXYPlane = Math.atan2(launchDirection.getZ(), launchDirectionXYProjLength);
    /* To demonstrate "visually," but more roundabout approach:
     *   var launchDirectionXYArgument = launchDirectionXYProjection.getTranslation().getAngle();
     *   var launchDirectionRotatedToXZ = launchDirection.rotateBy(new Rotation3d(0.0,0.0, -launchDirectionXYArgument.getRadians()));
     *   var launchDirectionRotatedToXY = launchDirectionRotatedToXZ.rotateBy(new Rotation3d(-(TAU/4), 0.0, 0.0))
                                                                    .toPose2d();
     *   var launchDirectionAngle = launchDirectionRotatedToXY.getTranslation().getAngle();
     */
    /* FIXME: For future parabolic solution use:
     *   var launchDirection2d = new Translation2d(launchDirectionXYProjLength, launchDirection.getZ());
     */
    return launchDirectionAngleToXYPlane;
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("Utility");

    builder.addBooleanProperty("isPassThrough", () -> this.passthrough, null);
    builder.addDoubleProperty("headingX", headingX, null);
    builder.addDoubleProperty("headingY", headingY, null);
    builder.addDoubleProperty("armAngle", armAngle, null);
    builder.addDoubleProperty("driverHeadingX", driverHeadingX, null);
    builder.addDoubleProperty("driverHeadingY", driverHeadingY, null);
    builder.addStringProperty("lastBotPose", () -> this.lastBotPose.getTranslation().toString(), null);
    builder.addStringProperty("shooterPose", () -> this.shooterPose.getTranslation().toString(), null);
    builder.addStringProperty("targetPose", () -> this.targetPose.getTranslation().toString(), null);
    builder.addStringProperty("launchDirection", () -> this.launchDirection.getTranslation().toString(), null);
    builder.addStringProperty("headingDirection", () -> this.headingDirection.getTranslation().toString(), null);
  }
}
