package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.RobotMap;
import frc.robot.subsystems.StorageSystem;

public class MoveStoredBallsToFeeder extends Command {

    private final StorageSystem storageSystem;

    public MoveStoredBallsToFeeder(StorageSystem storageSystem) {
        this.storageSystem = storageSystem;
        addRequirements(storageSystem);
    }

    @Override
    public void initialize() {
        storageSystem.move(RobotMap.CONVEYOR_SPEED);
    }

    @Override
    public void end(boolean interrupted) {
        storageSystem.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
