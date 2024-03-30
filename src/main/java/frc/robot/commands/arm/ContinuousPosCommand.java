package frc.robot.commands.arm;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.armSubsystem;

public class ContinuousPosCommand extends Command {

  private final armSubsystem m_ArmSubsystem;
  private final DoubleSupplier desiredAngleSupplier;

  public ContinuousPosCommand(armSubsystem subsystem,
                              DoubleSupplier desiredAngleSupplier) {
    m_ArmSubsystem = subsystem;
    this.desiredAngleSupplier = desiredAngleSupplier;
    addRequirements(m_ArmSubsystem);
  }

  @Override
  public void initialize() {
    m_ArmSubsystem.setSafeGoal(m_ArmSubsystem.getMeasurement());
    if (!m_ArmSubsystem.isEnabled()) {
      m_ArmSubsystem.enable();
    }
  }

  @Override
  public void execute() {
    double desiredAngle = desiredAngleSupplier.getAsDouble();
    m_ArmSubsystem.setSafeGoal(desiredAngle);
    // System.out.println("position error: " + m_ArmSubsystem.showPositionError());
  }

}
