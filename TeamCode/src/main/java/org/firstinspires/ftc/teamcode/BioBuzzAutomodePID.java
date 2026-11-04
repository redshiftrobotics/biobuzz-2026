package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Move Auto PID", group="Simple Autos")
public class BioBuzzAutomodePID extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotorEx frontLeftDrive = null;
    private DcMotorEx backLeftDrive = null;
    private DcMotorEx frontRightDrive = null;
    private DcMotorEx backRightDrive = null;
    private DcMotor xEncoder = null;
    private DcMotor yEncoder = null;
    private DcMotorEx flywheel = null;
    private DcMotorEx intake = null;
    private boolean flywheelEnabled = false;
    private boolean intakeEnabled = false;
    double currentX = 0.0;
    double currentY = 0.0;

    //TODO make cleaner

    private PIDController drive_yPID = new PIDController(Constants.DRIVE_PID.kP, Constants.DRIVE_PID.kI, Constants.DRIVE_PID.kD);
    private PIDController drive_xPID = new PIDController(Constants.DRIVE_PID.kP, Constants.DRIVE_PID.kI, Constants.DRIVE_PID.kD);

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        frontLeftDrive = hardwareMap.get(DcMotorEx.class, "FL");
        backLeftDrive = hardwareMap.get(DcMotorEx.class, "BL");
        frontRightDrive = hardwareMap.get(DcMotorEx.class, "FR");
        backRightDrive = hardwareMap.get(DcMotorEx.class, "BR");
        flywheel = hardwareMap.get(DcMotorEx.class, "F");
        intake = hardwareMap.get(DcMotorEx.class, "I");
        xEncoder = hardwareMap.get(DcMotor.class, "XE");
        yEncoder = hardwareMap.get(DcMotor.class, "YE");

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





        while(opModeIsActive()) {


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


            setSetpoints(0.0, 15);


            double axial   = drive_yPID.performPID(yEncoder.getCurrentPosition()*Constants.CM_PER_TICK);
            double lateral = drive_xPID.performPID(xEncoder.getCurrentPosition()*Constants.CM_PER_TICK);
            double yaw     = 0;

            // Set up a variable for each drive wheel to save the power level for telemetry.
            double frontLeftPower  = axial + lateral + yaw;
            double frontRightPower = axial - lateral - yaw;
            double backLeftPower   = axial - lateral + yaw;
            double backRightPower  = axial + lateral - yaw;




            frontLeftDrive.setPower(frontLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backLeftDrive.setPower(backLeftPower);
            backRightDrive.setPower(backRightPower);
        }


    }
    public void setSetpoints(double angle, double dist) {
        xEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        yEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double x_dist = dist * Math.cos(angle);
        double y_dist = dist * Math.sin(angle);
        drive_xPID.setSetpoint(x_dist);
        drive_yPID.setSetpoint(y_dist);
    }

}