package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants.Climber;

public class climberSubsystem extends SubsystemBase{

    private final CANSparkMax climberMotorLeft = new CANSparkMax(22, MotorType.kBrushless);
    private final CANSparkMax climberMotorRight = new CANSparkMax(21, MotorType.kBrushless);

    private final RelativeEncoder climberMotorLeftRelativeEncoder = climberMotorLeft.getEncoder();
    private final RelativeEncoder climberMotorRightRelativeEncoder = climberMotorRight.getEncoder();

    private final DigitalInput climberLeftLimitSwitch = new DigitalInput(2);
    private final DigitalInput climberRightLimitSwitch = new DigitalInput(1);

    public climberSubsystem() {

    }

    public void setClimberDown() {
         if (!climberLeftLimitSwitch.get() && !climberRightLimitSwitch.get()) {
            climberMotorLeft.set(0.7);
            climberMotorRight.set(-0.7);
        }
    }

    public void setClimberUp() {
        if (climberMotorLeftRelativeEncoder.getPosition() > Climber.MAX_HEIGHT_LEFT_ENCODER_VALUE && climberMotorRightRelativeEncoder.getPosition() < Climber.MAX_HEIGHT_RIGHT_ENCODER_VALUE) {
            climberMotorLeft.set(-0.7);
            climberMotorRight.set(0.7);
        }
    }

    public void setClimberNeutral() {
        climberMotorLeft.set(0);
        climberMotorRight.set(0);
    }

    public void zeroEncoders() {
        climberMotorLeftRelativeEncoder.setPosition(0);
        climberMotorRightRelativeEncoder.setPosition(0);

    }

    public boolean getDown() {
        if (climberLeftLimitSwitch.get() && climberRightLimitSwitch.get()){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void periodic() {

    }

    
}
