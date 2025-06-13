// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems.hood;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.shooter.Shooter;

public class HoodState extends Command {

  public final Shooter shooterSub;


  /** Creates a new HoodState. */
  public HoodState(Shooter hood) {
    this.shooterSub = hood;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    shooterSub.setHood(!shooterSub.getHood());
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return true;
  }
}