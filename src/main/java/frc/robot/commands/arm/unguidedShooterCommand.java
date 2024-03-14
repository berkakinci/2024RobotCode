package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.shooterSubsystem;

public class unguidedShooterCommand extends Command {

    private final shooterSubsystem m_ShooterSubsystem;
    public unguidedShooterCommand(shooterSubsystem subsystem) {
        m_ShooterSubsystem = subsystem;
        addRequirements(m_ShooterSubsystem);
    }

    @Override
    public void initialize() {
        m_ShooterSubsystem.guidedShoot(5676);
    }

    @Override
    public void execute() {
        
    }

    @Override
    public void end (boolean interrupted) {
        m_ShooterSubsystem.guidedShoot(0);
    }

    
}
