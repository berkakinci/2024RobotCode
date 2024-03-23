// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
 
package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.math.trajectory.TrapezoidProfile.State;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.Constants.Arm;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;

// A robot arm subsystem that moves with a motion profile.
public class armSubsystem extends ProfiledPIDSubsystem {
  private CANSparkMax leftMotor = new CANSparkMax(Arm.kLeftMotorPort, MotorType.kBrushless);
  private CANSparkMax rightMotor = new CANSparkMax(Arm.kRightMotorPort, MotorType.kBrushless);
  
  public DutyCycleEncoder m_encoder = new DutyCycleEncoder(4);
  
  private final ArmFeedforward m_feedforward =
      new ArmFeedforward(
          Arm.kSVolts, Arm.kGVolts,
          Arm.kVVoltSecondPerRad, Arm.kAVoltSecondSquaredPerRad);

  // Create a new ArmSubsystem. 
  public armSubsystem() {
    super(
        new ProfiledPIDController(
            Arm.kP,
            0,
            0,
            new TrapezoidProfile.Constraints(
                Arm.kMaxVelocityRadPerSecond,
                Arm.kMaxAccelerationRadPerSecSquared))//, Arm.kStartingPos
                );//add 0 
    m_encoder.setPositionOffset(0.386);
    m_encoder.setDistancePerRotation(Arm.kEncoderDistancePerRotation);
    leftMotor.setInverted(false);
    rightMotor.setInverted(true);
    // Start arm at rest in neutral position
    //setGoal(Arm.kArmOffsetRads);
    setGoal(Arm.kStartingPos);

    //enable();
  }

  @Override
  public void useOutput(double output, TrapezoidProfile.State setpoint) {
    // Calculate the feedforward from the setpoint
    double feedforward = m_feedforward.calculate(setpoint.position, setpoint.velocity);
    // Add the feedforward to the PID output to get the motor output
    leftMotor.setVoltage(-output + feedforward);// + feedforward);
    rightMotor.setVoltage(-output + feedforward);// + feedforward);
    //System.out.println("Absolute position encoder measurement: " + getMeasurement());
    //System.out.println("Voltage: " + output);
    
  }

  @Override
  public double getMeasurement() {
    return Math.PI*2*(m_encoder.getAbsolutePosition() - m_encoder.getPositionOffset()); //+ Arm.kArmOffsetRads;
    
  }

  public double showPositionError() {
    return m_controller.getPositionError();
  }

}