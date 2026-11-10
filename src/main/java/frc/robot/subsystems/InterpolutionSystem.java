package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.sim.BallSim;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.interpolation.LinearInterpolator;

public class InterpolutionSystem extends SubsystemBase {
    private double[] distances;
    private double[] angles;
    private double[] velocites;

    private BallSim ballSim;
    private LinearInterpolator interpolator;
    private UnivariateFunction functionAngle;
    private UnivariateFunction functionDistance;

    public InterpolutionSystem() {
        interpolator = new LinearInterpolator();
        angles = new double[]{85 , 82 , 79 , 75 , 75 , 76 , 77};
        distances = new double[]{1 , 1.5 , 2 , 2.5 , 3 , 3.5 , 4};
        velocites = new double[]{2000 , 2000 , 2000 , 2000 , 2150 , 2350 , 2550};
        functionAngle = interpolator.interpolate(distances, angles);
        functionDistance = interpolator.interpolate(distances , velocites);
    }

    public double GetVelocityRPMFromDistance(double distance) {
        return functionDistance.value(distance);
    }

    public double GetAngleFromDistance(double distance) {
        return functionAngle.value(distance);
    }
}
