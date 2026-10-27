package frc.robot;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.wpilibj.util.Color;
import edu.wpi.first.wpilibj.util.Color8Bit;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.FeedBallsToShooter;
import frc.robot.commands.SetPitcherToAngleCommand;
import frc.robot.sim.BallSim;
import frc.robot.sim.FiringDisplay;
import frc.robot.subsystems.FeederSystem;
import frc.robot.commands.RotateShooterAtSpeed;
import frc.robot.subsystems.ShooterSystem;
import frc.robot.subsystems.IntakeArmSystem;
import frc.robot.subsystems.PitcherSystem;
import frc.robot.subsystems.StorageSystem;

import java.util.Set;

public class Robot extends TimedRobot {
    private IntakeArmSystem intakeArmSystem;

    public PitcherSystem pitcherSystem;

    private ShooterSystem shooterSystem;

    private StorageSystem storageSystem;
    private FeederSystem feederSystem;
    private GameField gameField;
    private Field2d field;

    private BallSim ballSim;

    @Override
    public void robotInit() {
        this.shooterSystem = new ShooterSystem();
        intakeArmSystem = new IntakeArmSystem();
        pitcherSystem = new PitcherSystem();
        storageSystem = new StorageSystem();
        feederSystem = new FeederSystem();
        gameField = new GameField();

        field = new Field2d();

        ballSim = new BallSim(field, gameField);

        SmartDashboard.putData("Field", field);
        field.setRobotPose(new Pose2d(1, 1, Rotation2d.kZero));
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();

        Pose2d pose = gameField.getHubPosition();
        field.getObject("Hub").setPose(pose);

        double distance = gameField.calculateShooterDistanceToHub(field.getRobotPose());
        SmartDashboard.putNumber("Distance", distance);
    }

    @Override
    public void simulationInit() {

    }

    @Override
    public void simulationPeriodic() {
        ballSim.update();
    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void disabledExit() {

    }

    @Override
    public void teleopInit() {
        launchBall();
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void teleopExit() {

    }

    @Override
    public void autonomousInit() {

    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void autonomousExit() {

    }

    @Override
    public void testInit() {

    }

    @Override
    public void testPeriodic() {

    }

    @Override
    public void testExit() {

    }

    private Command shootToHub() {
        return Commands.defer(()-> {
            double distance = gameField.calculateShooterDistanceToHub(field.getRobotPose());
            double velocity = ShooterSystem.calculateFiringVelocity(6000);
            double angle = gameField.getFiringAngleOnDistance(distance, velocity);
            return new ParallelCommandGroup(
                    new SetPitcherToAngleCommand(pitcherSystem, angle),
                    new RotateShooterAtSpeed(shooterSystem, 6000),
                    new FeedBallsToShooter(feederSystem)
            );
        }, Set.of(pitcherSystem, shooterSystem, feederSystem));
    }

    private void launchBall() {
        Pose3d robotPose = new Pose3d(field.getRobotPose());
        Pose3d shootPose = robotPose.plus(RobotMap.SHOOTER_ON_ROBOT);
        double firingAngleDegrees = 15;//pitcherSystem.translateSystemAngleToFiringAngle(pitcherSystem.getPitcherPositionDegrees());
        double firingDirectionDegrees = robotPose.getRotation().toRotation2d().getDegrees();
        double firingVelocityRpm = 2000;//shooterSystem.getVelocityRpm();

        ballSim.launchBall(shootPose.getTranslation(), firingDirectionDegrees, firingVelocityRpm, firingAngleDegrees);
    }
}
