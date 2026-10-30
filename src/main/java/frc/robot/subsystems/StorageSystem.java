package frc.robot.subsystems;

import com.revrobotics.PersistMode;
import com.revrobotics.ResetMode;
import com.revrobotics.spark.SparkLowLevel;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;

public class StorageSystem extends SubsystemBase {

    private final SparkMax storageMotor;
    private final DigitalInput ProximitySensor;

    public StorageSystem() {
        storageMotor = new SparkMax(RobotMap.STORAGE_MOTOR_ID, SparkLowLevel.MotorType.kBrushless);
        ProximitySensor = new DigitalInput(RobotMap.PROXIMITY_SENSOR_ID);
        SparkMaxConfig config = new SparkMaxConfig();
        storageMotor.configure(config, ResetMode.kNoResetSafeParameters, PersistMode.kNoPersistParameters);
    }

    public boolean doesContainBalls() {
        return ProximitySensor.get();
    }

    public void move(double speed) {
        storageMotor.set(speed);
    }

    public void stop() {
        storageMotor.set(0);
    }
}
