package frc.robot.sim;

import com.revrobotics.sim.SparkFlexSim;
import com.revrobotics.spark.SparkFlex;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;
import frc.robot.RobotMap;

public class ShooterSim implements NTSendable {

    private final SparkFlexSim motorSim;
    private final FlywheelSim sim;

    private boolean canSendEntries;
    private DoublePublisher velocityEntryPub;

    public ShooterSim(SparkFlex shooterMotor) {
        motorSim = new SparkFlexSim(shooterMotor, RobotMap.SHOOTER_MOTOR);
        sim = new FlywheelSim(
                LinearSystemId.createFlywheelSystem(RobotMap.SHOOTER_MOTOR, RobotMap.SHOOTER_MOI, RobotMap.SHOOTER_GEAR_RATIO),
                RobotMap.SHOOTER_MOTOR);
    }

    public void update() {
        double batteryVoltage = RobotController.getBatteryVoltage();
        double shooterVolts = motorSim.getAppliedOutput() * batteryVoltage;
        sim.setInputVoltage(shooterVolts);
        sim.update(0.020);
        motorSim.iterate(
                Units.radiansPerSecondToRotationsPerMinute(sim.getAngularVelocityRadPerSec()),
                batteryVoltage,
                0.020);

        if (canSendEntries) {
            velocityEntryPub.set(sim.getAngularVelocityRPM());
        }
    }

    @Override
    public void initSendable(NTSendableBuilder builder) {
        NetworkTable table = builder.getTable().getSubTable("Values");

        velocityEntryPub = table.getDoubleTopic("VelocityMps").publish();
        canSendEntries = true;
    }
}
