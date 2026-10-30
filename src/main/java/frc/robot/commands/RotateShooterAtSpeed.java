package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.ShooterSystem;

public class RotateShooterAtSpeed extends Command {

    private final ShooterSystem shooterSystem;
    private final double targetRPM;

    public RotateShooterAtSpeed(ShooterSystem shooterSystem, double targetRPM) {
        this.shooterSystem = shooterSystem;
        this.targetRPM = targetRPM;
        addRequirements(shooterSystem);
    }

    @Override
    public void initialize() {
        shooterSystem.setToVelocity(targetRPM);
    }

    @Override
    public void end(boolean interrupted) {
        shooterSystem.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
