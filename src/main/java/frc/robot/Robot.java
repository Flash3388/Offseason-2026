package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.SetIntakeToPositionCommand;
import frc.robot.subsystems.*;
import frc.robot.commands.RotateShooterAtSpeed;

public class Robot extends TimedRobot {
    private IntakeArmSystem intakeArmSystem;

    private IntakeCollectorSystem intakeCollectorSystem;

    public PitcherSystem pitcherSystem;

    private ShooterSystem shooterSystem;

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
        intakeArmSystem.setIntakeArmToPosition(60);
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

    double kp = 0;
    double ki = 0;
    double kd = 0;
    @Override
    public void testInit() {
        intakeArmSystem.setIntakeArmToPosition(45);
        SmartDashboard.putNumber("KP", 0);
        kp = 0;
        SmartDashboard.putNumber("KI", 0);
        ki = 0;
        SmartDashboard.putNumber("KD", 0);
        kd = 0;

    }

    @Override
    public void testPeriodic() {
        boolean config = false;
        double kp = SmartDashboard.getNumber("KP", 0);
        if (this.kp != kp) {
            this.kp = kp;
            config = true;
        }

        double ki = SmartDashboard.getNumber("KI", 0);
        if (this.ki != ki) {
            this.ki = ki;
            config = true;
        }

        double kd = SmartDashboard.getNumber("KD", 0);
        if (this.kd != kd) {
            this.kd = kd;
            config = true;
        }

        if (config) {
            intakeArmSystem.setPID(kp, ki, kd);
        }
    }

    @Override
    public void testExit() {

    }
}
