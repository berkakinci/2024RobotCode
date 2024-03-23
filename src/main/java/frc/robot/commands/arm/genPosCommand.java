package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.arm.armSubsystem;

public class genPosCommand extends Command {

    private final armSubsystem m_ArmSubsystem;
    private double goal;
    public genPosCommand(armSubsystem subsystem, double desiredPos) {
        m_ArmSubsystem = subsystem;
        goal = desiredPos;
        addRequirements(m_ArmSubsystem);
    }

    @Override
    public void initialize() {
        //m_ArmSubsystem.setGoal(Arm.kAmpShootPos);
        m_ArmSubsystem.enable();
    }

    @Override 
    public void execute() {
        m_ArmSubsystem.setGoal(goal);
        //m_ArmSubsystem.setGoal(Arm.kAmpShootPos);
    }

    
}
