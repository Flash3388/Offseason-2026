package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.*;
import com.revrobotics.spark.config.LimitSwitchConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class IntakeArmSystem extends SubsystemBase {

    private final SparkMax intakeArmMotor;
    private final AbsoluteEncoder absoluteEncoder;
    private final SparkClosedLoopController motorPid;

    public IntakeArmSystem(){
        intakeArmMotor = new SparkMax(RobotMap.INTAKE_ARM_SYSTEM_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);

        SparkMaxConfig config = new SparkMaxConfig();

        config.idleMode(SparkBaseConfig.IdleMode.kBrake);


        config.limitSwitch
                .forwardLimitSwitchType(LimitSwitchConfig.Type.kNormallyOpen)
                .forwardLimitSwitchTriggerBehavior(LimitSwitchConfig.Behavior.kStopMovingMotor)
                .reverseLimitSwitchType(LimitSwitchConfig.Type.kNormallyOpen)
                .reverseLimitSwitchTriggerBehavior(LimitSwitchConfig.Behavior.kStopMovingMotor);

        config.closedLoop.pid(RobotMap.PITCHER_PID_KP, RobotMap.PITCHER_PID_KI, RobotMap.PITCHER_PID_KD)
                .feedbackSensor(FeedbackSensor.kAbsoluteEncoder);

        intakeArmMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

        absoluteEncoder = intakeArmMotor.getAbsoluteEncoder();

        motorPid = intakeArmMotor.getClosedLoopController();
    }

    public void setIntakeArmToPosition(double angle) {
        motorPid.setSetpoint(angle /360, SparkMax.ControlType.kPosition);
    }


    public double getIntakeArmPositionDegrees() {
        return absoluteEncoder.getPosition() * 360;
    }

    public double getIntakeArmVelocityRPM() {
        return absoluteEncoder.getVelocity();
    }

    @Override
    public void periodic() {
        SmartDashboard.putNumber("Intake Arm Position:", absoluteEncoder.getPosition());
        SmartDashboard.putBoolean("Intake Arm Forward Limit Switch:", intakeArmMotor.getForwardLimitSwitch().isPressed());
        SmartDashboard.putBoolean("Intake Arm Reverse Limit Switch:", intakeArmMotor.getReverseLimitSwitch().isPressed());
    }

    public boolean hasReachedPosition(double targetAngle) {
        return MathUtil.isNear(targetAngle, getIntakeArmPositionDegrees(), 0.3)
                        && Math.abs(getIntakeArmVelocityRPM()) < 1;
    }

    public void stop(){
        intakeArmMotor.set(0);
    }
}
