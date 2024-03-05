package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber.climberSubsystem;

public class climberZeroCommand extends Command {

    private final climberSubsystem m_ClimberSubsystem;
    public climberZeroCommand(climberSubsystem subsystem) {
        m_ClimberSubsystem = subsystem;
        addRequirements(m_ClimberSubsystem);
    }

    @Override
    public void initialize() {
        m_ClimberSubsystem.zeroEncoders();
    }  

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return true;
    }

    @Override
    public void end (boolean interrupted) {
    }
}