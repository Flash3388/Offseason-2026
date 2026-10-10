package frc.robot.commands;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.SwerveSystem;

public class SwerveDriveCommand extends Command {
    private final SwerveSystem swerveSystem;
    private final XboxController xboxController;
    public SwerveDriveCommand(SwerveSystem swerveSystem, XboxController xboxController) {
        this.swerveSystem = swerveSystem;
        this.xboxController = xboxController;

        addRequirements(this.swerveSystem);
    }

    @Override
    public void initialize() {

    }

    @Override
    public void execute() {
        swerveSystem.drive(
                -xboxController.getLeftY() * RobotMap.SWERVE_MAX_SPEED_METERS_PER_SECOND,
                 xboxController.getLeftX() * RobotMap.SWERVE_MAX_SPEED_METERS_PER_SECOND,
                xboxController.getRightX() * RobotMap.SWERVE_MAX_SPEED_RAD_PER_SECOND);
    }

    @Override
    public void end(boolean interrupted) {
        swerveSystem.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
