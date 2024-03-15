package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber.climberLeftSubsystem;

public class climberLeftZeroCommand extends Command {

    private final climberLeftSubsystem m_ClimberLeftSubsystem;
    public climberLeftZeroCommand(climberLeftSubsystem subsystem) {
        m_ClimberLeftSubsystem = subsystem;
        addRequirements(m_ClimberLeftSubsystem);
    }

    @Override
    public void initialize() {
        m_ClimberLeftSubsystem.zeroLeftEncoder();
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