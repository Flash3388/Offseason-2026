package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.PitcherSystem;

public class SetPitcherToAngleCommand extends Command {
    private final PitcherSystem pitcherSystem;
    private final double targetPosition;

    public SetPitcherToAngleCommand(PitcherSystem pitcherSubsystem, double angle) {
        this.pitcherSystem = pitcherSubsystem;
        this.targetPosition = angle / 360;

        addRequirements(pitcherSubsystem);
    }

    @Override
    public void initialize() {
        //pass target position to the system
        pitcherSystem.setPitcherToPosition(targetPosition);
    }

    @Override
    public boolean isFinished() {
        //stops command when the system reached its target state
        return pitcherSystem.didReachPosition(targetPosition);
    }

    @Override
    public void end(boolean interrupted) {
        //stops the system at the end
        pitcherSystem.stop();
    }
}
