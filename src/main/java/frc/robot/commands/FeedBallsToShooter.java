package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.FeederSystem;

public class FeedBallsToShooter extends Command {
    private final FeederSystem feederSystem;
    public FeedBallsToShooter(FeederSystem feederSystem) {
        this.feederSystem = feederSystem;
        addRequirements(feederSystem);
    }

    @Override
    public void initialize() {
        feederSystem.feedToShooter();
    }

    @Override
    public void end(boolean interrupted) {
        feederSystem.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
