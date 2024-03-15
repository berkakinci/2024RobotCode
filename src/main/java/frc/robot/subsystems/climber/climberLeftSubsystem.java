package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;
import frc.robot.Constants.Climber;

public class climberLeftSubsystem extends SubsystemBase{

    private final CANSparkMax climberMotorLeft = new CANSparkMax(22, MotorType.kBrushless);

    private final RelativeEncoder climberMotorLeftRelativeEncoder = climberMotorLeft.getEncoder();

    private final DigitalInput climberLeftLimitSwitch = new DigitalInput(3);

    public climberLeftSubsystem() {

    }

    public void setLeftClimberDown() {
         if (!climberLeftLimitSwitch.get()) {
            climberMotorLeft.set(0.7);
        }
    }

    public void setLeftClimberUp() {
        if (climberMotorLeftRelativeEncoder.getPosition() > Climber.MAX_HEIGHT_LEFT_ENCODER_VALUE) {
            climberMotorLeft.set(-0.7);
        }
    }

    public void setLeftClimberNeutral() {
        climberMotorLeft.set(0);
    }

    public void zeroLeftEncoder() {
        climberMotorLeftRelativeEncoder.setPosition(0);
    }

    public boolean getLeftDown() {
        if (climberLeftLimitSwitch.get()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean getLeftUp() {
        if (climberMotorLeftRelativeEncoder.getPosition() < Climber.MAX_HEIGHT_LEFT_ENCODER_VALUE) {
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
