package frc.robot;

import edu.wpi.first.math.MathShared;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.wpilibj.DriverStation;

public class GameField {

    public Pose2d getHubPosition() {
        DriverStation.Alliance alliance = DriverStation.getAlliance().orElse(DriverStation.Alliance.Red);
        if (alliance == DriverStation.Alliance.Red) {
            return new Pose2d(11.912854 , 4.021328, Rotation2d.kZero);
        } else {
            return new Pose2d(4.600194, 4.021328, Rotation2d.kZero);
        }
    }

    public double calculateShooterDistanceToHub(Pose2d robotPose) {
        Pose2d hub = getHubPosition();
        Pose2d shooter = robotPose.plus(new Transform2d(RobotMap.SHOOTER_ON_ROBOT.getTranslation().toTranslation2d(), Rotation2d.kZero));
        return Math.sqrt(Math.pow(hub.getX() - shooter.getX(), 2) + Math.pow(hub.getY() - shooter.getY(), 2));
    }

    public double Quadratic(double a , double b, double c){
        return (-b + Math.sqrt(Math.pow(b , 2) - 4 * a * c))/(2*a);
    }

    public double getFiringAngleOnDistance(double distance, double velocity) {
        double g = 9.81;
        double V0 = velocity;
        double H = RobotMap.HUB_HEIGHT - RobotMap.SHOOTER_ON_ROBOT_HEIGHT_METERS;

        return Math.atan(Quadratic(-g / Math.pow(V0, 2), distance,-g / Math.pow(V0, 2) - H ));
    }
}
