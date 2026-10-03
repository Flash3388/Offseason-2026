package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.*;
import com.revrobotics.spark.config.SparkFlexConfig;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import frc.robot.sim.ShooterSim;

public class ShooterSystem extends SubsystemBase {

    private final SparkFlex motor;
    private final SparkClosedLoopController PIDMotor;

    private final ShooterSim sim;

    public ShooterSystem() {
        this.motor = new SparkFlex(RobotMap.SHOOTER_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);

        SparkFlexConfig config = new SparkFlexConfig();

        config.closedLoop.pid(RobotMap.SHOOTER_P_GAIN, RobotMap.SHOOTER_I_GAIN, RobotMap.SHOOTER_D_GAIN).feedbackSensor(FeedbackSensor.kPrimaryEncoder);

        motor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

        PIDMotor = motor.getClosedLoopController();

        if (RobotBase.isSimulation()) {
            sim = new ShooterSim(motor);
            SmartDashboard.putData("ShooterSim", sim);
        } else {
            sim = null;
        }
    }

    public void setToVelocity(double velocityRpm) {
        double ff = velocityRpm / RobotMap.MAX_SHOOTER_VELOCITY_RPM; // this adds feedforward to help the shooter reach the target velocity faster.
        PIDMotor.setSetpoint(velocityRpm, SparkMax.ControlType.kVelocity, ClosedLoopSlot.kSlot0, ff, SparkClosedLoopController.ArbFFUnits.kPercentOut);
    }

    public void stop() {
        motor.set(0);
    }

    @Override
    public void simulationPeriodic() {
        sim.update();
    }
}
