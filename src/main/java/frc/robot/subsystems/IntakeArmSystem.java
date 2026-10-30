package frc.robot.subsystems;

import com.revrobotics.AbsoluteEncoder;
import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.*;
import com.revrobotics.spark.config.LimitSwitchConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.sim.IntakeArmSim;

public class IntakeArmSystem extends SubsystemBase {

    private final SparkMax intakeArmMotor;
    private final AbsoluteEncoder absoluteEncoder;
    private final SparkClosedLoopController motorPid;
    private final SparkMaxConfig maxConfig;

    private final IntakeArmSim sim;

    public IntakeArmSystem(){
        intakeArmMotor = new SparkMax(RobotMap.INTAKE_ARM_SYSTEM_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);

        SparkMaxConfig config = new SparkMaxConfig();
        maxConfig = config;

        config.idleMode(SparkBaseConfig.IdleMode.kBrake);


        config.limitSwitch
                .forwardLimitSwitchType(LimitSwitchConfig.Type.kNormallyOpen)
                .forwardLimitSwitchTriggerBehavior(LimitSwitchConfig.Behavior.kStopMovingMotor)
                .reverseLimitSwitchType(LimitSwitchConfig.Type.kNormallyOpen)
                .reverseLimitSwitchTriggerBehavior(LimitSwitchConfig.Behavior.kStopMovingMotor);

        config.closedLoop.pid(RobotMap.INTAKE_ARM_PID_KP, RobotMap.INTAKE_ARM_PID_KI, RobotMap.INTAKE_ARM_PID_KD)
                .feedbackSensor(FeedbackSensor.kAbsoluteEncoder);

        intakeArmMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);

        absoluteEncoder = intakeArmMotor.getAbsoluteEncoder();

        motorPid = intakeArmMotor.getClosedLoopController();

        if (RobotBase.isSimulation()) {
            sim = new IntakeArmSim(intakeArmMotor);
            SmartDashboard.putData("IntakeArmSim", sim);
        } else {
            sim = null;
        }
    }

    public void set(double speed) {
        intakeArmMotor.set(speed);
    }


    public double getIntakeArmPositionDegrees() {
        return absoluteEncoder.getPosition() * 360;
    }

    public double getIntakeArmVelocityRPM() {
        return absoluteEncoder.getVelocity();
    }

    public double getFeedForwardValue(){
        return RobotMap.INTAKE_ARM_FEEDFORWARD_OUTPUT_KG * Math.cos(Math.toRadians(getIntakeArmPositionDegrees()));
    }


    public void setIntakeArmToPosition(double angle) {
        motorPid.setSetpoint(angle / 360, SparkMax.ControlType.kPosition, ClosedLoopSlot.kSlot0,
                getFeedForwardValue(), SparkClosedLoopController.ArbFFUnits.kPercentOut);
    }


    @Override
    public void periodic() {
        SmartDashboard.putNumber("Intake Arm Position:", getIntakeArmPositionDegrees());
        SmartDashboard.putBoolean("Intake Arm Forward Limit Switch:", intakeArmMotor.getForwardLimitSwitch().isPressed());
        SmartDashboard.putBoolean("Intake Arm Reverse Limit Switch:", intakeArmMotor.getReverseLimitSwitch().isPressed());
    }

    public boolean hasReachedPosition(double targetAngle) {
        return MathUtil.isNear(targetAngle, getIntakeArmPositionDegrees(), 0.05)
                        && Math.abs(getIntakeArmVelocityRPM()) < 1;
    }

    public void stop(){
        intakeArmMotor.set(0);
    }

    @Override
    public void simulationPeriodic() {
        sim.update();
    }

    public void setPID(double kp, double ki, double kd) {
        maxConfig.closedLoop.pid(kp, ki, kd);
        intakeArmMotor.configure(maxConfig, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }
}
