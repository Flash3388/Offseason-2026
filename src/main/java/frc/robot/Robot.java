package frc.robot;

import edu.wpi.first.apriltag.AprilTagPoseEstimator;
import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.CommandScheduler;
import frc.robot.commands.SwerveDriveCommand;
import frc.robot.subsystems.SwerveSystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.*;
import frc.robot.commands.*;
import frc.robot.subsystems.*;

public class Robot extends TimedRobot {
  
    private IntakeArmSystem intakeArmSystem;
    private IntakeCollectorSystem intakeCollectorSystem;
    private PitcherSystem pitcherSystem;
    private ShooterSystem shooterSystem;
    private StorageSystem storageSystem;
    private FeederSystem feederSystem;
    private SwerveSystem swerveSystem;
    private CommandXboxController xbox;


    @Override
    public void robotInit() {
        swerveSystem = new SwerveSystem();        
        shooterSystem = new ShooterSystem();
        intakeArmSystem = new IntakeArmSystem();
        intakeCollectorSystem = new IntakeCollectorSystem();
        pitcherSystem = new PitcherSystem();
        storageSystem = new StorageSystem();
        feederSystem = new FeederSystem();
        xbox = new CommandXboxController(0);
      
        swerveSystem.setDefaultCommand(new SwerveDriveCommand(swerveSystem, xbox.getHID()));
    }

    @Override
    public void robotPeriodic() {
        try {
            LimelightHelpers.PoseEstimate limelightMeasurement = LimelightHelpers.getBotPoseEstimate_wpiBlue("limelight");
            double distToTag = LimelightHelpers.getRawFiducials("limelight")[0].distToRobot  - 0.925;
            if(distToTag > RobotMap.LIMELIGHT_MIN_ACCURATE_DISTANCE && distToTag < RobotMap.LIMELIGHT_MAX_ACCURATE_DISTANCE) {
                swerveSystem.addVisionMeasurement(
                        limelightMeasurement.pose,
                        limelightMeasurement.timestampSeconds
                );
                SmartDashboard.putNumber("distance to AprilTag", distToTag);
            }
        } catch (RuntimeException e) {}

        CommandScheduler.getInstance().run();
    }

    @Override
    public void simulationInit() {

    }

    @Override
    public void simulationPeriodic() {

    }

    @Override
    public void disabledInit() {

    }

    @Override
    public void disabledPeriodic() {

    }

    @Override
    public void disabledExit() {

    }

    @Override
    public void teleopInit() {
      
    }

    @Override
    public void teleopPeriodic() {

    }

    @Override
    public void teleopExit() {

    }

    @Override
    public void autonomousInit() {
        

    }

    @Override
    public void autonomousPeriodic() {

    }

    @Override
    public void autonomousExit() {

    }

    @Override
    public void testInit() {
//            Command autoCollect = new SequentialCommandGroup(
//                    new SetIntakeToPositionCommand(intakeArmSystem, 0),
//                    new DriveBackUntilFullCommand(intakeCollectorSystem, swerveSystem, new Timer()));
    }

    @Override
    public void testPeriodic() {

    }

    @Override
    public void testExit() {

    }
}
