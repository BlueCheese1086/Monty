// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.intake;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.IntakeConstants.*;
public class Intake extends SubsystemBase {
  // Initializing Motors
  TalonSRX frontRoller = new TalonSRX(frontRollerID);
  TalonSRX leftIndexer = new TalonSRX(leftIndexerID);
  TalonSRX rightIndexer = new TalonSRX(rightIndexerID);

  // Initializing Pneumatics
  Solenoid intake;

  /**
   * Creates a new Intake subsystem.
   * <p>
   * This subsystem is in charge of controlling the motors and solenoids that are
   * used to pick up game objects.
   * 
   * @param hub The Pneumatics Hub connected to the robot.
   */
  public Intake(PneumaticHub hub) {
    // Creating the solenoid.
    intake = hub.makeSolenoid(feederSolenoid);

    // Restoring the factory defaults of each motor.
    frontRoller.configFactoryDefault();
    leftIndexer.configFactoryDefault();
    rightIndexer.configFactoryDefault();

    // Making each motor follow the front roller.
    leftIndexer.follow(frontRoller);
    rightIndexer.follow(frontRoller);

    // Setting the initial state of the intake.
    intake.set(false);
  }

  /**
   * Sets the speed of the intake motors.
   */
  public void setSpeed(double speed) {
    SmartDashboard.putNumber("Intake Speed", speed * maxIntakeSpeed);
    frontRoller.set(ControlMode.PercentOutput, speed * maxIntakeSpeed);
  }

  /**
   * Returns the estimated speed of the intake motors.
   * 
   * @return A double representing the speed of the intake motors.
   */
  public double getSpeed() {
    return frontRoller.getMotorOutputPercent();
  }

  /**
   * Sets the state of the solenoids attached to the intake.
   * <p>
   * Values -> Physical state:
   * <p>
   * True -> Open
   * <p>
   * False -> Closed
   * <p>
   * 
   */
  public void setState(boolean state) {
    SmartDashboard.putString("Intake State", state ? "Open" : "Closed");

    intake.set(state);
  }

  /**
   * Returns the state of the intake.
   * 
   * @return The current state of the intake.
   */
  public boolean getState() {
    return intake.get();
  }
}
