package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Move Auto PID", group="Simple Autos")
public class BioBuzzAutomodePID extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor flywheel = null;
    private DcMotor intake = null;
    private boolean flywheelEnabled = false;
    private boolean intakeEnabled = false;

    //TODO make cleaner

    private PIDController PID_FR = new PIDController(Constants.DRIVE_PID.kP, Constants.DRIVE_PID.kI, Constants.DRIVE_PID.kD);
    private PIDController PID_FL = new PIDController(Constants.DRIVE_PID.kP, Constants.DRIVE_PID.kI, Constants.DRIVE_PID.kD);
    private PIDController PID_BR = new PIDController(Constants.DRIVE_PID.kP, Constants.DRIVE_PID.kI, Constants.DRIVE_PID.kD);
    private PIDController PID_BL = new PIDController(Constants.DRIVE_PID.kP, Constants.DRIVE_PID.kI, Constants.DRIVE_PID.kD);

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        frontLeftDrive = hardwareMap.get(DcMotor.class, "FL");
        backLeftDrive = hardwareMap.get(DcMotor.class, "BL");
        frontRightDrive = hardwareMap.get(DcMotor.class, "FR");
        backRightDrive = hardwareMap.get(DcMotor.class, "BR");
        flywheel = hardwareMap.get(DcMotor.class, "F");
        intake = hardwareMap.get(DcMotor.class, "I");

        // ########################################################################################
        // !!!            IMPORTANT Drive Information. Test your motor directions.            !!!!!
        // ########################################################################################
        // Most robots need the motors on one side to be reversed to drive forward.
        // The motor reversals shown here are for a "direct drive" robot (the wheels turn the same direction as the motor shaft)
        // If your robot has additional gear reductions or uses a right-angled drive, it's important to ensure
        // that your motors are turning in the correct direction.  So, start out with the reversals here, BUT
        // when you first test your robot, push the left joystick forward and observe the direction the wheels turn.
        // Reverse the direction (flip FORWARD <-> REVERSE ) of any wheel that runs backward
        // Keep testing until ALL the wheels move the robot forward when you push the left joystick forward.
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        backRightDrive.setDirection(DcMotor.Direction.FORWARD);


        double flywheelPower = 0;
        double intakePower = 0;


        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();



        if (intakeEnabled) {
            intakePower = Constants.INTAKE_POWER;
        } else {
            intakePower = 0.0;
        }
        if (flywheelEnabled) {
            flywheelPower = Constants.FLYWHEEL_POWER;
        } else {
            flywheelPower = 0.0;
        }
        intake.setPower(intakePower);
        flywheel.setPower(flywheelPower);


        while(opModeIsActive()) {
            double frontLeftPower  = PID_FL.performPID();
            double frontRightPower = PID_FR.performPID();
            double backLeftPower   = PID_BL.performPID();
            double backRightPower  = PID_BR.performPID();




            frontLeftDrive.setPower(frontLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backLeftDrive.setPower(backLeftPower);
            backRightDrive.setPower(backRightPower);
        }


    }
    public void drivePID(double angle, double dist) {

        PID_FL.setSetpoint();
        PID_FR.setSetpoint();
        PID_BL.setSetpoint();
        PID_BR.setSetpoint();
    }

}