// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

import edu.wpi.first.networktables.DoubleArraySubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;


/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();
  private TalonFX leftm1;
  private TalonFX leftm2;
  private TalonFX rightm1;
  private TalonFX rightm2;
  private XboxController joy1;
private limelight lime;

    NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");
    NetworkTableEntry tx = table.getEntry("tx");
    NetworkTableEntry ty = table.getEntry("ty");
  
    NetworkTableEntry ta = table.getEntry("ta");
    NetworkTableEntry tv = table.getEntry("tv");
 
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
    leftm1 = new TalonFX(0);
    leftm2 = new TalonFX(1);
    rightm1 = new TalonFX(2);
    rightm2 = new TalonFX(3);
    joy1 = new XboxController(0);

  
  




  }

  /**
   * This function is called every 20 ms, no matter the mode. Use this for items like diagnostics
   * that you want ran during disabled, autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before LiveWindow and
   * SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {}

  /**
   * This autonomous (along with the chooser code above) shows how to select between different
   * autonomous modes using the dashboard. The sendable chooser code works with the Java
   * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
   * uncomment the getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to the switch structure
   * below with additional strings. If using the SendableChooser make sure to add them to the
   * chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /** This function is called periodically during autonomous. */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /** This function is called once when teleop is enabled. */
  @Override
  public void teleopInit() {
    

 
    


        




  }

  /** This function is called periodically during operator control. */
  @Override
  public void teleopPeriodic() {
    double speed = -joy1.getLeftY();
        double turn = joy1.getRightX();
        double strafe = joy1.getLeftX();

        double leftfront = speed+turn+strafe;
        double rightfront = speed-turn-strafe;
        double leftrear = speed+turn-strafe;
        double rightrear = speed-turn+strafe;
        leftm1.set(ControlMode.PercentOutput,leftfront*0.7);
        leftm2.set(ControlMode.PercentOutput,rightfront*0.7);
        rightm1.set(ControlMode.PercentOutput,leftrear*0.7);
        rightm2.set(ControlMode.PercentOutput,rightrear*0.7);
        double x = tx.getDouble(0.0);
        double y = ty.getDouble(0.0);
        double a = ta.getDouble(0.0);
        double v = tv.getDouble(0.0);
        
      if(v >= 1){
        if(x > 1.0){
          leftm1.set(ControlMode.PercentOutput, 0.02*x);
          leftm1.set(ControlMode.PercentOutput, 0.02*x);
       





        }
        else if(x < -1.0){
          rightm1.set(ControlMode.PercentOutput,0.02*x);
          rightm2.set(ControlMode.PercentOutput, 0.02*x);

        }
        else if(x > -1.0 && x < 1.0){
        leftm1.set(ControlMode.PercentOutput,0);
        leftm2.set(ControlMode.PercentOutput,0);
        rightm1.set(ControlMode.PercentOutput,0);
        rightm2.set(ControlMode.PercentOutput,0);
        }
        else if(v ==0){
          leftm1.set(ControlMode.PercentOutput,0);
        leftm2.set(ControlMode.PercentOutput,0);
        rightm1.set(ControlMode.PercentOutput,0);
        rightm2.set(ControlMode.PercentOutput,0);


        }

      }
    
  }

  /** This function is called once when the robot is disabled. */
  @Override
  public void disabledInit() {}

  /** This function is called periodically when disabled. */
  @Override
  public void disabledPeriodic() {}

  /** This function is called once when test mode is enabled. */
  @Override
  public void testInit() {}

  /** This function is called periodically during test mode. */
  @Override
  public void testPeriodic() {}

  /** This function is called once when the robot is first started up. */
  @Override
  public void simulationInit() {}

  /** This function is called periodically whilst in simulation. */
  @Override
  public void simulationPeriodic() {}
}
