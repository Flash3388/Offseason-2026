package frc.robot.sim;

import com.revrobotics.sim.SparkMaxSim;
import com.revrobotics.spark.SparkMax;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NTSendable;
import edu.wpi.first.networktables.NTSendableBuilder;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.SingleJointedArmSim;
import edu.wpi.first.wpilibj.smartdashboard.Mechanism2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismLigament2d;
import edu.wpi.first.wpilibj.smartdashboard.MechanismRoot2d;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import frc.robot.RobotMap;

public class IntakeArmSim implements NTSendable {

    private static final double BASE_MECHANISM_ROOT_X = 0.3;
    private static final double BASE_MECHANISM_ROOT_HEIGHT = 0.2;
    private static final Color8Bit MECHANISM_COLOR_SWITCH_OFF = new Color8Bit(Color.kRed);
    private static final Color8Bit MECHANISM_COLOR_SWITCH_ON = new Color8Bit(Color.kGreen);

    private final SparkMaxSim motorSim;
    private final SingleJointedArmSim sim;

    private final Mechanism2d mechanism;
    private final MechanismLigament2d mechanismArm;
    private final MechanismLigament2d mechanismSwitchBottom;
    private final MechanismLigament2d mechanismSwitchTop;

    private boolean canSendEntries;
    private DoublePublisher positionEntryPub;
    private DoublePublisher velocityEntryPub;
    private BooleanPublisher hitBottomEntryPub;
    private BooleanPublisher hitTopEntryPub;

    public IntakeArmSim(SparkMax motor) {
        motorSim = new SparkMaxSim(motor, RobotMap.INTAKE_ARM_MOTOR);
        motorSim.setPosition(0);
        sim = new SingleJointedArmSim(
                RobotMap.INTAKE_ARM_MOTOR,
                RobotMap.INTAKE_ARM_GEAR_RATIO,
                RobotMap.INTAKE_ARM_MOI,
                RobotMap.INTAKE_ARM_LENGTH_METERS,
                Math.toRadians(RobotMap.INTAKE_ARM_MIN_ANGLE_DEGREES),
                Math.toRadians(RobotMap.INTAKE_ARM_MAX_ANGLE_DEGREES),
                true,
                0);

        mechanism = new Mechanism2d(2, 2);
        MechanismRoot2d mechanismRoot = mechanism.getRoot("base", BASE_MECHANISM_ROOT_X, BASE_MECHANISM_ROOT_HEIGHT);
        mechanismRoot.append(new MechanismLigament2d("lineRight", 1.7, 0, 10, new Color8Bit(Color.kCyan)));
        mechanismRoot.append(new MechanismLigament2d("lineLeft", 0.7, 180, 10, new Color8Bit(Color.kCyan)));
        MechanismRoot2d mechanismArmRoot = mechanism.getRoot("armBase", BASE_MECHANISM_ROOT_X, BASE_MECHANISM_ROOT_HEIGHT);
        mechanismArm = mechanismArmRoot.append(new MechanismLigament2d("arm", 0.3, 0, 7, new Color8Bit(Color.kPink)));
        mechanismSwitchBottom = mechanism.getRoot("bottomRoot", BASE_MECHANISM_ROOT_X - 0.1, BASE_MECHANISM_ROOT_HEIGHT + 0.1)
                .append(new MechanismLigament2d("switchBottom", 0.15, 180, 6, MECHANISM_COLOR_SWITCH_OFF));
        mechanismSwitchTop = mechanism.getRoot("topRoot", BASE_MECHANISM_ROOT_X - 0.1, BASE_MECHANISM_ROOT_HEIGHT + config.maxAngleDegrees)
                .append(new MechanismLigament2d("switchTop", 0.15, 180, 6, MECHANISM_COLOR_SWITCH_OFF));
    }

    public void update() {
        double voltage = motorSim.getAppliedOutput() * RobotController.getBatteryVoltage();
        sim.setInputVoltage(voltage);
        sim.update(0.020);
        motorSim.iterate(Units.radiansPerSecondToRotationsPerMinute(
                sim.getVelocityRadPerSec()),
                RobotController.getBatteryVoltage(),
                0.020);

        double posDegrees = Math.toDegrees(sim.getAngleRads());
        double velDps = Math.toDegrees(sim.getVelocityRadPerSec());
        boolean atBottom = sim.hasHitLowerLimit();
        boolean atTop = sim.hasHitUpperLimit();

        motorSim.getAbsoluteEncoderSim().setPosition(posDegrees / 360);
        motorSim.getReverseLimitSwitchSim().setPressed(atBottom);
        motorSim.getForwardLimitSwitchSim().setPressed(atTop);

        if (canSendEntries) {
            positionEntryPub.set(posDegrees);
            velocityEntryPub.set(velDps);
            hitBottomEntryPub.set(atBottom);
            hitTopEntryPub.set(atTop);

            mechanismArm.setAngle(posDegrees);
            mechanismSwitchBottom.setColor(atBottom ? MECHANISM_COLOR_SWITCH_ON : MECHANISM_COLOR_SWITCH_OFF);
            mechanismSwitchTop.setColor(atTop ? MECHANISM_COLOR_SWITCH_ON : MECHANISM_COLOR_SWITCH_OFF);
        }
    }

    @Override
    public void initSendable(NTSendableBuilder builder) {
        NetworkTable table = builder.getTable().getSubTable("Values");

        positionEntryPub = table.getDoubleTopic("PositionMeters").publish();
        velocityEntryPub = table.getDoubleTopic("VelocityMps").publish();
        hitBottomEntryPub = table.getBooleanTopic("HitBottom").publish();
        hitTopEntryPub = table.getBooleanTopic("HitTop").publish();
        canSendEntries = true;

        mechanism.initSendable(builder);
    }
}
