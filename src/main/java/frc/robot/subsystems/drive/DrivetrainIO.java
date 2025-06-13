// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.drive;

import org.littletonrobotics.junction.AutoLog;

import edu.wpi.first.math.geometry.Pose2d;

public interface DrivetrainIO {
    @AutoLog
    public static class DrivetrainIOInputs {
        public Pose2d robotPose = new Pose2d();
        public double leftOutputVolts = 0.0;
        public double rightOutputVolts = 0.0;

        public double leftVelocityMetersPerSecond = 0.0;
        public double rightVelocityMetersPerSecond = 0.0;

        public double leftPositionMeters = 0.0;
        public double rightPositionMeters = 0.0;

        public double[] leftCurrentAmps = new double[0];
        public double[] rightCurrentAmps = new double[0];
    }

    
    public void updateInputs(DrivetrainIOInputs inputs);
    
    public void arcadeDrive(double left, double right);
    
} 
