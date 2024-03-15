package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class intakeSubsystem extends SubsystemBase {
    
    private final CANSparkMax intakeMotor = new CANSparkMax(29, MotorType.kBrushless);
    private final AnalogInput noteSensor = new AnalogInput(1);
    double voltage_scale_factor = 5/RobotController.getVoltage5V();
    double currentDistanceInches;

    
    public intakeSubsystem () {

    }

    public void intake() {
        //if (!isHolding()){
            intakeMotor.set(0.5);
       // }

    }

    public void outtake() {
        intakeMotor.set(-0.2);
    }

    public void stop() {
        intakeMotor.set(0);
    }

    public void index () {
        intakeMotor.set(0.3);
    }

    //public boolean isHolding () {
        //if (currentDistanceInches < 13) {
           // return true;
        //}
        //else {
        //    return false;
        //}
   // }

    @Override
    public void periodic() {
        //currentDistanceInches = noteSensor.getValue() * voltage_scale_factor * 0.0492;

        //System.out.println("ultrasonic distance" + currentDistanceInches);
    }

}
