package frc.robot.commands.climber;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.climber.climberRightSubsystem;

public class climberRightZeroCommand extends Command {

    private final climberRightSubsystem m_ClimberRightSubsystem;
    public climberRightZeroCommand(climberRightSubsystem subsystem) {
        m_ClimberRightSubsystem = subsystem;
        addRequirements(m_ClimberRightSubsystem);
    }

    @Override
    public void initialize() {
        m_ClimberRightSubsystem.zeroRightEncoder();
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