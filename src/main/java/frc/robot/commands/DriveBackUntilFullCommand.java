package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.IntakeCollectorSystem;
import frc.robot.subsystems.SwerveSystem;

public class DriveBackUntilFullCommand extends Command {
    private final IntakeCollectorSystem intakeCollectorSystem;
    private final SwerveSystem swerveSystem;
    private final Timer timer;
    public DriveBackUntilFullCommand(IntakeCollectorSystem intakeCollectorSystem, SwerveSystem swerveSystem, Timer timer) {
        this.intakeCollectorSystem = intakeCollectorSystem;
        this.swerveSystem = swerveSystem;
        this.timer = timer;
        addRequirements(intakeCollectorSystem, swerveSystem);
    }

    @Override
    public void initialize() {
        intakeCollectorSystem.takeInFuel();
        timer.restart();
        swerveSystem.drive(0.2 * RobotMap.SWERVE_MAX_SPEED_METERS_PER_SECOND, 0, 0);
    }

    @Override
    public void execute() {

    }

    @Override
    public void end(boolean interrupted) {
        swerveSystem.stop();
        intakeCollectorSystem.stop();
    }

    @Override
    public boolean isFinished() {
        return (intakeCollectorSystem.isStorageFull() || timer.hasElapsed(3));
    }
}
