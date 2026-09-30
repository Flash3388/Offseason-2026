package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class FeederSystem extends SubsystemBase {
    private final SparkMax feederMotor;

    public FeederSystem(){
        feederMotor = new SparkMax(RobotMap.FEEDER_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);

        //configuration
        SparkMaxConfig config = new SparkMaxConfig();
        config.idleMode(SparkBaseConfig.IdleMode.kBrake);
        this.feederMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }
    //move the motor at a constant speed
    public void feedToShooter(){
        feederMotor.set(0.5);
    }
    //stop the motor
    public void stop(){
        feederMotor.set(0);
    }
}
