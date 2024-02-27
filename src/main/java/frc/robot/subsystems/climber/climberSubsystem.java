package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants.Climber;

public class climberSubsystem extends SubsystemBase{

    private final static CANSparkMax climberMotorLeft = new CANSparkMax(22, MotorType.kBrushless);
    private final static CANSparkMax climberMotorRight = new CANSparkMax(21, MotorType.kBrushless);

    private final static RelativeEncoder climberMotorLeftRelativeEncoder = climberMotorLeft.getEncoder();
    private final static RelativeEncoder climberMotorRightRelativeEncoder = climberMotorRight.getEncoder();

    private final static DigitalInput climberLeftLimitSwitch = new DigitalInput(2);
    private final static DigitalInput climberRightLimitSwitch = new DigitalInput(1);

    public static boolean position = false;

    public climberSubsystem() {

    }

    public static void setClimberPosition(boolean Position) {

        position = Position;
        
    }

    public void periodic() { //false is down, true is up for position

        if (position && climberMotorLeftRelativeEncoder.getPosition() < Climber.MAX_HEIGHT_LEFT_ENCODER_VALUE && climberMotorRightRelativeEncoder.getPosition() < Climber.MAX_HEIGHT_RIGHT_ENCODER_VALUE) {
            climberMotorLeft.set(-0.7);
            climberMotorRight.set(0.7);
        }
        
        if (!position && !climberLeftLimitSwitch.get() && !climberRightLimitSwitch.get()) {
            climberMotorLeft.set(0.7);
            climberMotorRight.set(-0.7);
        }
        
    }

    public void zeroEncoders() {
        climberMotorLeftRelativeEncoder.setPosition(0);

    }

    
}
