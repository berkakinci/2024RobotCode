package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import edu.wpi.first.math.geometry.Translation2d;


public class DebugCommand extends Command {
    SwerveSubsystem swerve;
    myNumberInator genNumInator = new myNumberInator();
    myCircleInator genCircleInator = new myCircleInator();

    public DebugCommand(SwerveSubsystem swerve) {
        this.swerve = swerve;
        addRequirements(swerve);
    };

    @Override
    public void execute() {
        // Get directions
        Translation2d translation = new Translation2d(genCircleInator.getX(),
                                                      genCircleInator.getY());
        genCircleInator.rotate();

        // Make the robot move
        swerve.drive(translation, 0.0, false);
    };
}
