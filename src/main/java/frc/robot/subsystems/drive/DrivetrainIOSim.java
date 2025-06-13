package frc.robot.subsystems.drive;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;

import static frc.robot.Constants.DriveConstants.*;
public class DrivetrainIOSim implements DrivetrainIO {

    DCMotorSim left_Motor = new DCMotorSim(
            LinearSystemId.createDCMotorSystem(DCMotor.getNEO(1), 1, gearing), DCMotor.getNEO(1));
    DCMotorSim right_Motor = new DCMotorSim(
            LinearSystemId.createDCMotorSystem(DCMotor.getNEO(1), 1, gearing), DCMotor.getNEO(1));

    @Override
    public void updateInputs(DrivetrainIOInputs inputs) {
        left_Motor.update(0.02);
        right_Motor.update(0.02);

        inputs.leftCurrentAmps = new double[] { left_Motor.getCurrentDrawAmps() };
        inputs.leftPositionMeters = left_Motor.getAngularPositionRotations();
        inputs.leftOutputVolts = left_Motor.getAngularVelocityRPM() / maxRPM
                * RobotController.getInputVoltage();
        inputs.leftVelocityMetersPerSecond = Units.radiansPerSecondToRotationsPerMinute(
                left_Motor.getAngularVelocityRPM() * gearing) * Units.inchesToMeters(2);

        inputs.rightCurrentAmps = new double[] { right_Motor.getCurrentDrawAmps() };
        inputs.rightPositionMeters = right_Motor.getAngularPositionRotations();
        inputs.rightOutputVolts = right_Motor.getAngularVelocityRPM() / maxRPM
                * RobotController.getInputVoltage();
        inputs.rightVelocityMetersPerSecond = Units.radiansPerSecondToRotationsPerMinute(
                right_Motor.getAngularVelocityRPM() * gearing) * Units.inchesToMeters(2);

    }

    @Override
    public void arcadeDrive(double left, double right) {
        left_Motor.setInputVoltage(MathUtil.clamp(left, -12, 12));
        right_Motor.setInputVoltage(MathUtil.clamp(right, -12, 12));
    }
}
