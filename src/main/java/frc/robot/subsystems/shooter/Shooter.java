package frc.robot.subsystems.shooter;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj.PneumaticHub;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import com.ctre.phoenix6.controls.Follower;

import static frc.robot.Constants.ShooterConstants.*;
public class Shooter {

    TalonFX rightLaunch = new TalonFX(rightLauncherID);
    TalonFX leftLaunch = new TalonFX(leftLauncherID);
    TalonSRX feedMe = new TalonSRX(feedRollerID);
    Solenoid hood;

    public Shooter(PneumaticHub hub) {
        hood = hub.makeSolenoid(solenoidID);
        leftLaunch.getConfigurator().apply(new TalonFXConfiguration());
        rightLaunch.getConfigurator().apply(new TalonFXConfiguration());
        feedMe.configFactoryDefault();
        hood.set(false);

        // Making the right motor follow the left one.
        rightLaunch.setControl(new Follower(leftLaunch.getDeviceID(), true));
    }

    public void setLaunch(double speed) {
        leftLaunch.set(speed * maxLaunchSpeed);
    }

    public void setFeederSpeed(double speed) {
        feedMe.set(ControlMode.PercentOutput, -speed * maxFeedSpeed);
    }

    public double getLaunchingSpeed() {
        return (leftLaunch.get() + rightLaunch.get());
    }

    public double getFeedSpeed(double speed) {
        return feedMe.getMotorOutputPercent();
    }

    public void setHood(boolean state) {
        SmartDashboard.putString("Hood", state ? "Closed" : "Open");
        hood.set(state);
    }

    public boolean getHood() {
        return hood.get();
    }
}
