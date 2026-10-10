package frc.robot.subsystems;

import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.RobotMap;
import swervelib.SwerveDrive;
import swervelib.parser.SwerveParser;

import java.io.File;
import java.io.IOException;

public class SwerveSystem extends SubsystemBase {
    private final SwerveDrive swerveDrive;

    public SwerveSystem() {
        File file = new File(Filesystem.getDeployDirectory(), "swerve");
        try {
            SwerveParser swerveParser = new SwerveParser(file);
            swerveDrive = swerveParser.createSwerveDrive(RobotMap.SWERVE_MAX_SPEED_METERS_PER_SECOND);
        }
        catch (IOException e){
            throw new Error(e);
        }
    }

    public void drive(double velocityX,double velocityY, double velocityRotation ) {
        swerveDrive.drive(new ChassisSpeeds(velocityX, velocityY, velocityRotation));
    }

    public void stop() {
        swerveDrive.drive(new ChassisSpeeds(0, 0, 0));
    }
}
