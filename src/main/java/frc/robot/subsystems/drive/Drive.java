package frc.robot.subsystems.drive;

import java.util.function.DoubleSupplier;
import org.littletonrobotics.junction.Logger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveConstants;

public class Drive extends SubsystemBase {

  private final DrivetrainIOInputsAutoLogged inputs;
  private final DrivetrainIO io;
  private Field2d field = new Field2d();
  private DifferentialDriveOdometry odometry = new DifferentialDriveOdometry(new Rotation2d(), 0, 0,
      new Pose2d(0, 0, new Rotation2d()));

  
  public Drive(DrivetrainIO io) {
    this.io = io;
    inputs = new DrivetrainIOInputsAutoLogged();
  }

  public void arcadeDrive(double speed, double rotation) { // Real Implemntation
    speed = MathUtil.applyDeadband(speed, 0.2);
    rotation = MathUtil.applyDeadband(rotation, 0.2);
    double left = speed + rotation;
    double right = speed - rotation;

    left *= DriveConstants.maxSpeed;
    right *= DriveConstants.maxSpeed;

    io.arcadeDrive(left, right);
  }

  @Override
  public void periodic() {
    inputs.robotPose = odometry.update(
        odometry.getPoseMeters().getRotation()
            // Use differential drive kinematics to find the rotation rate based on the
            .plus(Rotation2d
                .fromRadians((inputs.leftVelocityMetersPerSecond - inputs.rightVelocityMetersPerSecond)
                    * 0.020 / Units.inchesToMeters(26))), // 26
        inputs.leftPositionMeters, inputs.rightPositionMeters);
    io.updateInputs(inputs);
    Logger.processInputs("Drivetrain", inputs);
    Logger.recordOutput("Drivetrain Pose", odometry.getPoseMeters());
    field.setRobotPose(odometry.getPoseMeters());
  }

  public Command arcadeDrive(DoubleSupplier xSpeedDoubleSupplier, DoubleSupplier zRotationSupplier) {
    return new RunCommand(() -> {
        this.arcadeDrive(xSpeedDoubleSupplier.getAsDouble(), zRotationSupplier.getAsDouble());
    }, this);
  }
}
