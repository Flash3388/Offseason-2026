package frc.robot;

import edu.wpi.first.math.system.plant.DCMotor;

public class RobotMap {

    private RobotMap() {}

    public static final int SHOOTER_MOTOR_ID = 1;
    public static final double SHOOTER_P_GAIN = 0;
    public static final double SHOOTER_I_GAIN = 0;
    public static final double SHOOTER_D_GAIN = 0;

    public static final int INTAKE_ARM_SYSTEM_MOTOR_ID = 0;

    public static final int PITCHER_MOTOR_ID = 11; //not real values
    public static final double PITCHER_PID_KP = 0;
    public static final double PITCHER_PID_KI = 0;
    public static final double PITCHER_PID_KD = 0;


    public static final DCMotor SHOOTER_MOTOR = DCMotor.getNeoVortex(1);
    public static final double SHOOTER_GEAR_RATIO = 1;
    public static final double SHOOTER_MASS_KG = 1;
    public static final double SHOOTER_WHEEL_RADIUS_METERS = 0.0381;
    public static final double SHOOTER_MOI = (1 / 2.0) * SHOOTER_MASS_KG * SHOOTER_WHEEL_RADIUS_METERS * SHOOTER_WHEEL_RADIUS_METERS;
    public static final int MAX_SHOOTER_VELOCITY_RPM = 6784;

    public static final DCMotor PITCHER_MOTOR = DCMotor.getNEO(1);
    public static final double PITCHER_MASS_KG = 0;
    public static final double PITCHER_LENGTH_M = 0.22;
    public static final double PITCHER_MOI = (1 / 3.0) * PITCHER_MASS_KG * PITCHER_LENGTH_M * PITCHER_LENGTH_M;
    public static final double PITCHER_GEAR_RATIO = 5;
    public static final double PITCHER_MIN_ANGLE_DEGREES = 0;
    public static final double PITCHER_MAX_ANGLE_DEGREES = 0;

    public static final DCMotor INTAKE_ARM_MOTOR = DCMotor.getNEO(1);
    public static final double INTAKE_ARM_GEAR_RATIO = 1.0 / 80.0;
    public static final double INTAKE_ARM_MASS_KG = 5;
    public static final double INTAKE_ARM_LENGTH_METERS = 0.5;
    public static final double INTAKE_ARM_MOI = 1 / 3.0 * INTAKE_ARM_MASS_KG * INTAKE_ARM_LENGTH_METERS * INTAKE_ARM_LENGTH_METERS;
    public static final double INTAKE_ARM_MIN_ANGLE_DEGREES = 0;
    public static final double INTAKE_ARM_MAX_ANGLE_DEGREES = 0;

    public static final int STORAGE_MOTOR_ID = 12;
    public static final int PROXIMITY_SENSOR_ID = 13;
    public static final double CONVEYOR_SPEED = 0.5;
}
