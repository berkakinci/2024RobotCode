package frc.robot.subsystems.arm;

import com.revrobotics.CANSparkLowLevel.MotorType;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;

import edu.wpi.first.math.Nat;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.controller.LinearQuadraticRegulator;
import edu.wpi.first.math.estimator.KalmanFilter;
import edu.wpi.first.math.numbers.N1;
import edu.wpi.first.math.system.LinearSystem;
import edu.wpi.first.math.system.LinearSystemLoop;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class shooterSubsystem extends SubsystemBase {
    
    private final CANSparkMax shooterMotor = new CANSparkMax(28, MotorType.kBrushless);
    private final RelativeEncoder shooterEncoder = shooterMotor.getEncoder();

    private static double kSpinupRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(0);
    private static final double kFlywheelGearing = 1.0;
    private static final double kFlywheelMomentOfInertia = 0.005; //kg * m^2

  // The plant holds a state-space model of our flywheel. This system has the following properties:
  //
  // States: [velocity], in radians per second.
  // Inputs (what we can "put in"): [voltage], in volts.
  // Outputs (what we can measure): [velocity], in radians per second.
  private final LinearSystem<N1, N1, N1> m_flywheelPlant =
      LinearSystemId.createFlywheelSystem(
          DCMotor.getNEO(1), kFlywheelMomentOfInertia, kFlywheelGearing);

  // The observer fuses our encoder data and voltage inputs to reject noise.
  private final KalmanFilter<N1, N1, N1> m_observer =
      new KalmanFilter<>(
          Nat.N1(),
          Nat.N1(),
          m_flywheelPlant,
          VecBuilder.fill(3.0), // How accurate we think our model is
          VecBuilder.fill(0.01), // How accurate we think our encoder
          // data is
          0.020);

  // A LQR uses feedback to create voltage commands.
  private final LinearQuadraticRegulator<N1, N1, N1> m_controller =
      new LinearQuadraticRegulator<>(
          m_flywheelPlant,
          VecBuilder.fill(8.0), // qelms. Velocity error tolerance, in radians per second. Decrease
          // this to more heavily penalize state excursion, or make the controller behave more
          // aggressively.
          VecBuilder.fill(12.0), // relms. Control effort (voltage) tolerance. Decrease this to more
          // heavily penalize control effort, or make the controller less aggressive. 12 is a good
          // starting point because that is the (approximate) maximum voltage of a battery.
          0.020); // Nominal time between loops. 0.020 for TimedRobot, but can be
  // lower if using notifiers.

  // The state-space loop combines a controller, observer, feedforward and plant for easy control.
  private final LinearSystemLoop<N1, N1, N1> m_loop =
      new LinearSystemLoop<>(m_flywheelPlant, m_controller, m_observer, 12.0, 0.020);


    public void initFlywheel() {
        m_loop.reset(VecBuilder.fill(Units.rotationsPerMinuteToRadiansPerSecond(shooterEncoder.getVelocity())));
        shooterMotor.setInverted(true);
    }

    public shooterSubsystem () {

    }

    public void unguidedShoot() {
        shooterMotor.set(-1);
    }

    public void guidedShoot(double desiredSpeed) { //pass in desired rpm and convert to radians
        kSpinupRadPerSec = Units.rotationsPerMinuteToRadiansPerSecond(desiredSpeed);
    }

    public void stop() {
        kSpinupRadPerSec = 0;
    }


    @Override
    public void periodic() {
        //System.out.println("shooter speed: " + ((shooterEncoder.getVelocity()*(Math.PI*0.1016))/60 )+ " m/s");
        
        m_loop.setNextR(VecBuilder.fill(kSpinupRadPerSec));

        m_loop.correct(VecBuilder.fill(Units.rotationsPerMinuteToRadiansPerSecond(shooterEncoder.getVelocity())));

        m_loop.predict(0.020);

        double nextVoltage = m_loop.getU(0);
        shooterMotor.setVoltage(-nextVoltage);

    
    }

}
