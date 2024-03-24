package frc.robot.subsystems.climber;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkLowLevel.MotorType;

import frc.robot.Constants;
import frc.robot.Constants.Climber;

public class climberRightSubsystem extends SubsystemBase{

    private final CANSparkMax climberMotorRight = new CANSparkMax(21, MotorType.kBrushless);
    {
        climberMotorRight.setSmartCurrentLimit(
            Constants.MotorLimit.Neo550.stall,
            Constants.MotorLimit.Neo550.free,
            Constants.MotorLimit.Neo550.stallRPM);
    }

    private final RelativeEncoder climberMotorRightRelativeEncoder = climberMotorRight.getEncoder();

    private final DigitalInput climberRightLimitSwitch = new DigitalInput(2);

    public climberRightSubsystem() {

    }

    public void setRightClimberDown() {
        if (!climberRightLimitSwitch.get()) {
            climberMotorRight.set(-1);
        }
    }

    public void setRightClimberUp() {
        if (climberMotorRightRelativeEncoder.getPosition() < Climber.MAX_HEIGHT_RIGHT_ENCODER_VALUE) {
            climberMotorRight.set(1);
        }
    }

    public void setRightClimberNeutral() {
        climberMotorRight.set(0);
    }

    public void zeroRightEncoder() {
        climberMotorRightRelativeEncoder.setPosition(0);

    }

    public boolean getRightDown() {
        if (climberRightLimitSwitch.get()){
            return true;
        }
        else {
            return false;
        }
    }

    public boolean getRightUp() {
        if (climberMotorRightRelativeEncoder.getPosition() > Climber.MAX_HEIGHT_RIGHT_ENCODER_VALUE) {
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
