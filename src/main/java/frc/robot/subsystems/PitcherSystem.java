package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.*;
import com.revrobotics.spark.config.LimitSwitchConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class PitcherSystem extends SubsystemBase {
    private final SparkMax pitcherMotor;
    private final AbsoluteEncoder encoder;
    private final SparkClosedLoopController motorPid;

    public PitcherSystem() {
        this.pitcherMotor = new SparkMax(RobotMap.PITCHER_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);

        //creating a new config to use on motor
        SparkMaxConfig config = new SparkMaxConfig();

        //configure the motor to use kBrake idleMode
        config.idleMode(SparkBaseConfig.IdleMode.kBrake);

        //configure hard limit switches to stop the pitcher out of bounds
        config.limitSwitch
                .forwardLimitSwitchType(LimitSwitchConfig.Type.kNormallyOpen)
                .forwardLimitSwitchTriggerBehavior(LimitSwitchConfig.Behavior.kStopMovingMotor)
                .reverseLimitSwitchType(LimitSwitchConfig.Type.kNormallyOpen)
                .reverseLimitSwitchTriggerBehavior(LimitSwitchConfig.Behavior.kStopMovingMotor);

        //pid config to run pid loop on the controller
        config.closedLoop.pid(RobotMap.PITCHER_PID_KP, RobotMap.PITCHER_PID_KI, RobotMap.PITCHER_PID_KD)
                .feedbackSensor(FeedbackSensor.kAbsoluteEncoder);

        //configuring the motor
        this.pitcherMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

        this.encoder = pitcherMotor.getAbsoluteEncoder();
        this.motorPid = pitcherMotor.getClosedLoopController();
    }


    public void stop() {
        //stop the motor
        pitcherMotor.set(0);
    }

    public void setPitcherToPosition(double position) {
        //pass new setpoint to the pid
        motorPid.setSetpoint(position, SparkMax.ControlType.kPosition);
    }

    public double getPitcherVelocityRPM() {
        //get encoder velocity
        return encoder.getVelocity();
    }

    public double getPitcherPositionDegrees() {
        //get encoder position and convert from rotations to degrees
        return encoder.getPosition() * 360;
    }

    public boolean didReachPosition(double targetPosition) {
        //checking to see if the encoder position and velocity is close enough to the target
        return
                MathUtil.isNear(targetPosition, getPitcherPositionDegrees(), 1)
                && Math.abs(getPitcherVelocityRPM()) < 0.05;
    }
}
