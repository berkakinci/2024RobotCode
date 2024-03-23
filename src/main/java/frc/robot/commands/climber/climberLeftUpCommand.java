package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber.climberLeftSubsystem;

public class climberLeftUpCommand extends Command {

    private final climberLeftSubsystem m_ClimberLeftSubsystem;
    public climberLeftUpCommand(climberLeftSubsystem subsystem) {
        m_ClimberLeftSubsystem = subsystem;
        addRequirements(m_ClimberLeftSubsystem);
    }

    @Override
    public void initialize() {
    
    }

    @Override
    public void execute() {
        m_ClimberLeftSubsystem.setLeftClimberUp();
    }

    @Override
    public void end (boolean interrupted) {
        m_ClimberLeftSubsystem.setLeftClimberNeutral();
    }

    @Override
    public boolean isFinished(){
    if (m_ClimberLeftSubsystem.getLeftUp()) {
        return true;
    }
    else {
        return false;
    }
}
    

}