package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.TimestampedData;

@Autonomous(name="Move Auto PID", group="Simple Autos")
public class BioBuzzAutomodePID extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotorEx frontLeftDrive = null;
    private DcMotorEx backLeftDrive = null;
    private DcMotorEx frontRightDrive = null;
    private DcMotorEx backRightDrive = null;
    private DcMotor xEncoder = null;
    private DcMotor yEncoder = null;
    private DcMotor yEncoder2 = null;
    double currentX = 0.0;
    double currentY = 0.0;
    double currentYaw = 0.0;

    //TODO make cleaner

    private PIDController drive_yPID = new PIDController(Constants.DRIVE_PID.kP, Constants.DRIVE_PID.kI, Constants.DRIVE_PID.kD);
    private PIDController drive_xPID = new PIDController(Constants.DRIVE_PID.kP, Constants.DRIVE_PID.kI, Constants.DRIVE_PID.kD);
    private PIDController drive_yawPID = new PIDController(Constants.DRIVE_PID.kP, Constants.DRIVE_PID.kI, Constants.DRIVE_PID.kD);

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        frontLeftDrive = hardwareMap.get(DcMotorEx.class, "FL");
        backLeftDrive = hardwareMap.get(DcMotorEx.class, "BL");
        frontRightDrive = hardwareMap.get(DcMotorEx.class, "FR");
        backRightDrive = hardwareMap.get(DcMotorEx.class, "BR");
        xEncoder = hardwareMap.get(DcMotor.class, "XE");
        yEncoder = hardwareMap.get(DcMotor.class, "YE");
        yEncoder2 = hardwareMap.get(DcMotor.class, "F");

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
        yEncoder.setDirection(DcMotor.Direction.REVERSE);



        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();





        while(opModeIsActive()) {



            setSetpoints(0.0, -50);

            currentX = xEncoder.getCurrentPosition()*Constants.CM_PER_TICK;
            currentY = ((double) (yEncoder.getCurrentPosition() + yEncoder2.getCurrentPosition()) / 2)*Constants.CM_PER_TICK;
            currentYaw = 0.0;
            double axial   = drive_yPID.performPID(currentY);
            double lateral = drive_xPID.performPID(currentY);
            double yaw     = drive_yawPID.performPID(currentYaw);

            telemetry.addData("axial", axial);
            telemetry.addData("lateral", lateral);
            telemetry.addData("yEncoder",yEncoder.getCurrentPosition());
            telemetry.addData("xEncoder",xEncoder.getCurrentPosition());
            telemetry.addData("yEncoder2",yEncoder2.getCurrentPosition());
            telemetry.update();
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
        yEncoder2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        yEncoder.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        double x_dist = dist * Math.cos(angle);
        double y_dist = dist * Math.sin(angle);

        drive_xPID.setSetpoint(x_dist);
        drive_yPID.setSetpoint(y_dist);
        drive_yawPID.setSetpoint(0.0);
    }

}