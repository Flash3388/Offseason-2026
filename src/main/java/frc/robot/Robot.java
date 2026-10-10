package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.subsystems.FeederSystem;
import frc.robot.commands.RotateShooterAtSpeed;
import frc.robot.subsystems.ShooterSystem;
import frc.robot.subsystems.IntakeArmSystem;
import frc.robot.subsystems.PitcherSystem;
import frc.robot.subsystems.StorageSystem;

public class Robot extends TimedRobot {
    private IntakeArmSystem intakeArmSystem;

    public PitcherSystem pitcherSystem;

    private ShooterSystem shooterSystem;

    private StorageSystem storageSystem;
    private FeederSystem feederSystem;

    @Override
    public void robotInit() {
        this.shooterSystem = new ShooterSystem();
        intakeArmSystem = new IntakeArmSystem();
        pitcherSystem = new PitcherSystem();
        storageSystem = new StorageSystem();
        feederSystem = new FeederSystem();
    }

    @Override
    public void robotPeriodic() {
        CommandScheduler.getInstance().run();
    }

    @Override
    public void simulationInit() {

    }

    @Override
    public void simulationPeriodic() {

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
}
