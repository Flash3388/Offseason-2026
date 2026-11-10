package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class IntakeCollectorSystem extends SubsystemBase {
    private final SparkMax intakeMotor;

    public IntakeCollectorSystem(){
        intakeMotor = new SparkMax(RobotMap.INTAKE_COLLECTOR_SYSTEM_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);

        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(SparkBaseConfig.IdleMode.kCoast);
        intakeMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public void takeInFuel(){
        intakeMotor.set(0.5);
    }

    public void stop(){
        intakeMotor.set(0);
    }


}
