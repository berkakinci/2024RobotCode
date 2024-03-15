package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber.climberSubsystem;

public class climberLeftDownCommand extends Command {

    private final climberSubsystem m_ClimberSubsystem;
    public climberLeftDownCommand(climberSubsystem subsystem) {
        m_ClimberSubsystem = subsystem;
        addRequirements(m_ClimberSubsystem);
    }

    @Override
    public void initialize() {
    
    }

    @Override
    public void execute() {
            m_ClimberSubsystem.setLeftClimberDown();
    }

    @Override
    public void end (boolean interrupted) {
        m_ClimberSubsystem.setLeftClimberNeutral();
    }

    @Override
    public boolean isFinished(){
    return m_ClimberSubsystem.getLeftDown();
}
}