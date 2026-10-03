package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.IntakeArmSystem;

public class SetIntakeToPositionCommand extends Command {
    private final IntakeArmSystem intakeArmSystem;
    private final double targetAngle;

    public SetIntakeToPositionCommand(IntakeArmSystem intakeArmSystem, double targetAngle) {
        this.intakeArmSystem = intakeArmSystem;
        this.targetAngle = targetAngle;

        addRequirements(intakeArmSystem);
    }

    @Override
    public void initialize() {
        intakeArmSystem.setIntakeArmToPosition(targetAngle);
    }

    @Override
    public void execute() {
    }

    @Override
    public boolean isFinished() {
        return intakeArmSystem.hasReachedPosition(targetAngle);
    }

    @Override
    public void end(boolean interrupted) {
        intakeArmSystem.stop();
    }
}
