package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.intakeSubsystem;
import frc.robot.subsystems.arm.shooterSubsystem;

public class shooterInitCommand extends Command {

    private final shooterSubsystem m_ShooterSubsystem;
    public shooterInitCommand(shooterSubsystem subsystem) {
        m_ShooterSubsystem = subsystem;
        addRequirements(m_ShooterSubsystem);
    }

    @Override
    public void initialize() {
        m_ShooterSubsystem.initFlywheel();
    }

    @Override
    public boolean isFinished() {
        return true;
    }

   
    
}
