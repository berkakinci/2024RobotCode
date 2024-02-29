package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber.climberSubsystem;

public class climberDownCommand extends Command {

    private final climberSubsystem m_ClimberSubsystem;
    public climberDownCommand(climberSubsystem subsystem) {
        m_ClimberSubsystem = subsystem;
        addRequirements(subsystem);
    }

    @Override
    public void initialize() {
    
    }

    @Override
    public void execute() {
        m_ClimberSubsystem.setClimberDown();
    }
}