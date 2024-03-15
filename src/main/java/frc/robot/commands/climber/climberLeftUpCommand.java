package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber.climberSubsystem;

public class climberLeftUpCommand extends Command {

    private final climberSubsystem m_ClimberSubsystem;
    public climberLeftUpCommand(climberSubsystem subsystem) {
        m_ClimberSubsystem = subsystem;
        addRequirements(m_ClimberSubsystem);
    }

    @Override
    public void initialize() {
    
    }

    @Override
    public void execute() {
        m_ClimberSubsystem.setLeftClimberUp();
    }

    @Override
    public void end (boolean interrupted) {
        m_ClimberSubsystem.setLeftClimberNeutral();
    }

    @Override
    public boolean isFinished(){
    if (m_ClimberSubsystem.getLeftUp()) {
        return true;
    }
    else {
        return false;
    }
}
    

}