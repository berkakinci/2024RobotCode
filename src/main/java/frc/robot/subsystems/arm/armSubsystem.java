// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.controller.ArmFeedforward;
import edu.wpi.first.math.controller.ProfiledPIDController;
import edu.wpi.first.math.trajectory.TrapezoidProfile;
import edu.wpi.first.wpilibj.DutyCycleEncoder;
import frc.robot.Constants.Arm;
import edu.wpi.first.wpilibj2.command.ProfiledPIDSubsystem;

// A robot arm subsystem that moves with a motion profile.
public class armSubsystem extends ProfiledPIDSubsystem {
  private final CANSparkMax leftMotor = new CANSparkMax(Arm.kLeftMotorPort, MotorType.kBrushless);
  private final CANSparkMax rightMotor = new CANSparkMax(Arm.kRightMotorPort, MotorType.kBrushless);
  private final DutyCycleEncoder m_encoder = new DutyCycleEncoder(4);
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
                Arm.kMaxAccelerationRadPerSecSquared)),
        0);
    m_encoder.setDistancePerRotation(Arm.kEncoderDistancePerPulse);
    // Start arm at rest in neutral position
    setGoal(Arm.kArmOffsetRads);
  }

  @Override
  public void useOutput(double output, TrapezoidProfile.State setpoint) {
    // Calculate the feedforward from the setpoint
    double feedforward = m_feedforward.calculate(setpoint.position, setpoint.velocity);
    // Add the feedforward to the PID output to get the motor output
    m_motor.setVoltage(output + feedforward);
  }

  @Override
  public double getMeasurement() {
    return m_encoder.getDistance() + ArmConstants.kArmOffsetRads;
  }
}