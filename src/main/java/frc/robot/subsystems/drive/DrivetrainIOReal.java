package frc.robot.subsystems.drive;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

public class DrivetrainIOReal implements DrivetrainIO{

    public SparkMax frontLeft;
    public SparkMax frontRight;
    public SparkMax backLeft;
    public SparkMax backRight;

    public DrivetrainIOReal(int fl, int fr, int bl, int br) {

        frontLeft = new SparkMax(fl, MotorType.kBrushless);
        frontRight = new SparkMax(fr, MotorType.kBrushless);
        backLeft = new SparkMax(bl, MotorType.kBrushless);
        backRight = new SparkMax(br, MotorType.kBrushless);

        SparkMaxConfig config = new SparkMaxConfig();
        SparkMaxConfig leftConfig = new SparkMaxConfig();

        config.idleMode(IdleMode.kCoast);
        config.smartCurrentLimit(50);
        config.inverted(false);

        leftConfig.inverted(true);
        leftConfig.idleMode(IdleMode.kCoast);
        leftConfig.smartCurrentLimit(50);

        frontLeft.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        backLeft.configure(leftConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        frontRight.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        backRight.configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

        leftConfig.follow(frontLeft);
        config.follow(frontRight);

    }

    public void arcadeDrive(double left, double right) {
        frontLeft.set(left);
        frontRight.set(right);
    }

    @Override
    public void updateInputs(DrivetrainIOInputs inputs) {
        inputs.leftOutputVolts = frontLeft.getBusVoltage() * frontLeft.getAppliedOutput();
        inputs.rightOutputVolts = frontRight.getBusVoltage() * frontRight.getAppliedOutput();
    }

}
