package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.*;
import com.revrobotics.spark.config.SparkFlexConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class ShooterSystem extends SubsystemBase {

    private final SparkFlex motor;
    private final SparkClosedLoopController PIDMotor;

    public ShooterSystem() {
        this.motor = new SparkFlex(RobotMap.SHOOTER_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);

        SparkFlexConfig config = new SparkFlexConfig();

        config.closedLoop.pid(RobotMap.SHOOTER_P_GAIN, RobotMap.SHOOTER_I_GAIN, RobotMap.SHOOTER_D_GAIN).feedbackSensor(FeedbackSensor.kPrimaryEncoder);

        motor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

        PIDMotor = motor.getClosedLoopController();
    }

    public void setToVelocity(double velocityRpm) {
        double ff = velocityRpm / RobotMap.MAX_SHOOTER_VELOCITY_RPM; // this adds feedforward to help the shooter reach the target velocity faster.
        PIDMotor.setSetpoint(velocityRpm, SparkMax.ControlType.kVelocity, ClosedLoopSlot.kSlot0, ff, SparkClosedLoopController.ArbFFUnits.kPercentOut);
    }

    public void stop() {
        motor.set(0);
    }
}
