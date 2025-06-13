package frc.robot;

import static frc.robot.Constants.DriveConstants.backLeftID;
import static frc.robot.Constants.DriveConstants.backRightID;
import static frc.robot.Constants.DriveConstants.frontLeftID;
import static frc.robot.Constants.DriveConstants.frontRightID;

import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.subsystems.drive.Drive;
import frc.robot.subsystems.drive.DrivetrainIOReal;
import frc.robot.subsystems.drive.DrivetrainIOSim;
import frc.robot.subsystems.hood.HoodState;
import frc.robot.subsystems.intake.Intake;
import frc.robot.subsystems.intake.Commands.RunIntake;
import frc.robot.subsystems.intake.Commands.SetIntakeState;
import frc.robot.subsystems.shooter.Shooter;
import frc.robot.subsystems.shooter.Commands.RunFeed;
import frc.robot.subsystems.shooter.Commands.RunLaunch;

public class RobotContainer {

  private Drive drive;
  private Intake intake;
  private Shooter shooter;
  private CommandXboxController controller = new CommandXboxController(0);

  private PneumaticHub hub = new PneumaticHub(31);

  public RobotContainer(boolean isReal) {
    if (isReal) {
      drive = new Drive(new DrivetrainIOReal(frontLeftID, frontRightID, backLeftID, backRightID));
      intake = new Intake(hub);
      shooter = new Shooter(hub);
    } else {
      drive = new Drive(new DrivetrainIOSim());
      intake = new Intake(hub);
      shooter = new Shooter(hub);
    }
    configureBindings();
  }

  private void configureBindings() {

    drive.setDefaultCommand(
        drive.arcadeDrive(
            () -> -controller.getRightX(),
            () -> controller.getLeftY()));

    controller.x().whileTrue(new RunIntake(intake, 1)); // Run intake motors
    controller.b().toggleOnTrue(new SetIntakeState(intake, true)); // Move the intake down
    controller.rightTrigger(0.5).whileTrue(new RunFeed(shooter, 1)); // shoot the ball after
    controller.y().toggleOnTrue(new RunLaunch(shooter, 1)); // toggles the flyweels
    controller.povUp().toggleOnTrue(new HoodState(shooter)); // Hood state

    // Shoots the ball backwards if it is stuck
    controller.leftBumper().whileTrue(new RunIntake(intake, -1));
    controller.leftBumper().whileTrue(new RunFeed(shooter, -1));
  }

  public Command getAutonomousCommand() {
    return Commands.none();
  }
}
