package frc.robot.commands.arm;

import edu.wpi.first.wpilibj2.command.Command;
//import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.arm.shooterSubsystem;

public class ampShootCommand extends Command {

    private final shooterSubsystem m_ShooterSubsystem;
    //private final CommandXboxController operatorXbox;
    public ampShootCommand(shooterSubsystem subsystem/*, CommandXboxController operator*/) {
        m_ShooterSubsystem = subsystem;
        //operatorXbox = operator;
        addRequirements(m_ShooterSubsystem);
    }

    @Override
    public void initialize() {
        m_ShooterSubsystem.guidedShoot(1250);
    }

    @Override
    public void execute() {
        //if (m_ShooterSubsystem.isSpunUp()) {
            //operatorXbox.();
       // }
    }

    @Override
    public void end (boolean interrupted) {
        m_ShooterSubsystem.guidedShoot(0);
    }

    
}
