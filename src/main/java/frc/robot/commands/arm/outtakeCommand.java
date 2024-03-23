package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.intakeSubsystem;

public class outtakeCommand extends Command {

    private final intakeSubsystem m_IntakeSubsystem;
    public outtakeCommand(intakeSubsystem subsystem) {
        m_IntakeSubsystem = subsystem;
        addRequirements(m_IntakeSubsystem);
    }

    @Override
    public void initialize() {
    
    }

    @Override
    public void execute() {
        m_IntakeSubsystem.outtake();
    }

    @Override
    public void end (boolean interrupted) {
        m_IntakeSubsystem.stop();
    }

    /*@Override
    public boolean isFinished() {
        if (m_IntakeSubsystem.isHolding()) {
            return true;
        }
        else {
            return false;
        }
    }*/
    
}
