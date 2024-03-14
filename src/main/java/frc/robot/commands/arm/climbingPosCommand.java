package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.armSubsystem;
import frc.robot.subsystems.arm.intakeSubsystem;
import frc.robot.Constants.Arm;

public class climbingPosCommand extends Command {

    private final armSubsystem m_ArmSubsystem;
    public climbingPosCommand(armSubsystem subsystem) {
        m_ArmSubsystem = subsystem;
        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize() {
        m_ArmSubsystem.setGoal(Arm.kClimbingPos);
        m_ArmSubsystem.enable();
    }

    @Override
    public void execute() {
        //m_ArmSubsystem.setGoal(Arm.kClimbingPos);
    }
    
}
