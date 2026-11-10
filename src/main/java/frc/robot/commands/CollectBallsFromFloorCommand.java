package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeCollectorSystem;

public class CollectBallsFromFloorCommand extends Command {
    private final IntakeCollectorSystem intakeCollectorSystem;
    public CollectBallsFromFloorCommand(IntakeCollectorSystem intakeCollectorSystem) {
        this.intakeCollectorSystem = intakeCollectorSystem;
        addRequirements(intakeCollectorSystem);
    }

    @Override
    public void initialize() {
        intakeCollectorSystem.takeInFuel();
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public void end(boolean interrupted) {
        intakeCollectorSystem.stop();
    }
}
