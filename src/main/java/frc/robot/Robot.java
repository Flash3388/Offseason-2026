package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.SwerveDriveCommand;
import frc.robot.subsystems.SwerveSystem;

public class Robot extends TimedRobot {

    private SwerveSystem swerveSystem;
    private XboxController xboxController;

    @Override
    public void robotInit() {
        swerveSystem = new SwerveSystem();
        xboxController = new XboxController(0);
        swerveSystem.setDefaultCommand(new SwerveDriveCommand(swerveSystem, xboxController));
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
        swerveSystem.swerveDrive.getModules()[2].setAngle(0);
        //swerveSystem.drive(1, 0, 0);
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
