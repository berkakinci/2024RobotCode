/*package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.armSubsystem;
import frc.robot.subsystems.arm.intakeSubsystem;

public class speakerPosCommand extends Command {

    private final armSubsystem m_ArmSubsystem;
    public speakerPosCommand(armSubsystem subsystem) {
        m_ArmSubsystem = subsystem;
        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize() {
        m_ArmSubsystem.setGoal();
    }

    public boolean isFinished() {
        return true;
    }
    
}
*/