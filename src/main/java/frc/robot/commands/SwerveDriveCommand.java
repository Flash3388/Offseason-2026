package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
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
        double Lx = -xboxController.getLeftX();
        double Ly = -xboxController.getLeftY();
        double Rx = -xboxController.getRightX();

        if (MathUtil.isNear(0, Lx, RobotMap.DRIVER_CONTROLLER_DEADZONE_L_STICK)) {Lx = 0;}
        if (MathUtil.isNear(0, Ly, RobotMap.DRIVER_CONTROLLER_DEADZONE_L_STICK)) {Ly = 0;}
        if (MathUtil.isNear(0, Rx, RobotMap.DRIVER_CONTROLLER_DEADZONE_R_STICK )) {Rx = 0;}

        swerveSystem.drive(
                Ly * Math.abs(Ly) * RobotMap.SWERVE_MAX_SPEED_METERS_PER_SECOND,
                Lx * Math.abs(Lx) * RobotMap.SWERVE_MAX_SPEED_METERS_PER_SECOND ,
                Rx * Math.abs(Rx) * RobotMap.SWERVE_MAX_SPEED_RAD_PER_SECOND);
    }

    @Override
    public void end(boolean interrupted) {
        swerveSystem.stop();
    }

    @Override
    public boolean isFinished() {
        return false;
    }


    //BR 3.7deg clockwise
}
