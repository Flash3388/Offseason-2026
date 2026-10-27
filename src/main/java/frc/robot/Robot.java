package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.SetPitcherToAngleCommand;
import frc.robot.subsystems.FeederSystem;
import frc.robot.commands.SetIntakeToPositionCommand;
import frc.robot.subsystems.*;
import frc.robot.commands.RotateShooterAtSpeed;

public class Robot extends TimedRobot {
    private IntakeArmSystem intakeArmSystem;

    private IntakeCollectorSystem intakeCollectorSystem;

    public PitcherSystem pitcherSystem;

    private ShooterSystem shooterSystem;

    private CommandXboxController xbox;

    private StorageSystem storageSystem;

    private FeederSystem feederSystem;

    @Override
    public void robotInit() {
        this.shooterSystem = new ShooterSystem();
        intakeArmSystem = new IntakeArmSystem();
        intakeCollectorSystem = new IntakeCollectorSystem();
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
