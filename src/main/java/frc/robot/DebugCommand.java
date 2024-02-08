package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.swervedrive.SwerveSubsystem;
import edu.wpi.first.math.geometry.Translation2d;


public class DebugCommand extends Command {
    SwerveSubsystem swerve;
    NumberInator genNumInator = new NumberInator();
    //CircleInator genShapeInator = new CircleInator(0.5,0);
    DiamondInator genShapeInator = new DiamondInator(0.2, 0);

    public DebugCommand(SwerveSubsystem swerve) {
        this.swerve = swerve;
        addRequirements(swerve);
    };

    @Override
    public void execute() {
        // Get directions
        Translation2d translation = new Translation2d(genShapeInator.getX(),
                                                      genShapeInator.getY());
        genShapeInator.rotate();

        // Make the robot move
        swerve.drive(translation, 0.0, false);
    };
}
