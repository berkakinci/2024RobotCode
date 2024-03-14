package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.armSubsystem;
import frc.robot.Constants.Arm;

public class startingPosCommand extends Command {

    private final armSubsystem m_ArmSubsystem;
    public startingPosCommand(armSubsystem subsystem) {
        m_ArmSubsystem = subsystem;
        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize() {
        m_ArmSubsystem.setGoal(Arm.kStartingPos);
        if (!m_ArmSubsystem.isEnabled()) {
            m_ArmSubsystem.enable();
        }
    }

}
