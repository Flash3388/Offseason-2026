package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.SetPitcherToAngleCommand;
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

    private CommandXboxController xbox;

    private StorageSystem storageSystem;
    private FeederSystem feederSystem;

    @Override
    public void robotInit() {
        this.shooterSystem = new ShooterSystem();
        intakeArmSystem = new IntakeArmSystem();
        pitcherSystem = new PitcherSystem();
        storageSystem = new StorageSystem();
        feederSystem = new FeederSystem();
        xbox = new CommandXboxController(0);
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
        xbox.x().whileTrue(new SetPitcherToAngleCommand(pitcherSystem, 10));
        xbox.y().whileTrue(new SetPitcherToAngleCommand(pitcherSystem, 20));
        xbox.b().whileTrue(new SetPitcherToAngleCommand(pitcherSystem, 30));
        xbox.a().whileTrue(new SetPitcherToAngleCommand(pitcherSystem, 15));
//        CommandScheduler.getInstance().schedule(new RotateShooterAtSpeed(shooterSystem , 4000));

    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void teleopExit() {

    }

    @Override
    public void autonomousInit() {
        pitcherSystem.stop();
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
