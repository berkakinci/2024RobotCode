package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber.climberLeftSubsystem;

public class climberLeftDownCommand extends Command {

    private final climberLeftSubsystem m_ClimberLeftSubsystem;
    public climberLeftDownCommand(climberLeftSubsystem subsystem) {
        m_ClimberLeftSubsystem = subsystem;
        addRequirements(m_ClimberLeftSubsystem);
    }

    @Override
    public void initialize() {
    
    }

    @Override
    public void execute() {
            m_ClimberLeftSubsystem.setLeftClimberDown();
    }

    @Override
    public void end (boolean interrupted) {
        m_ClimberLeftSubsystem.setLeftClimberNeutral();
    }

    @Override
    public boolean isFinished(){
    return m_ClimberLeftSubsystem.getLeftDown();
}
}