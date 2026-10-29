package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Move Auto", group="Simple Autos")
public class BioBuzzAutomode1 extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;
    private DcMotor flywheel = null;
    private DcMotor intake = null;
    private boolean flywheelEnabled = false;
    private boolean intakeEnabled = false;
    private boolean firing = false;
    private CRServo leftStopper = null;
    private CRServo rightStopper = null;

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
        rightStopper = hardwareMap.get(CRServo.class, "RS");
        leftStopper = hardwareMap.get(CRServo.class, "LS");

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

        XDriveForward(500, 0.25, 0, 0);

        XDriveForward(0, 0, 0, 90);

    }
    public void XDriveForward(int time, double speed_x, double speed_y, double angularvelocity) {
        double max;

        double axial   = speed_y;
        double lateral =  speed_x;
        double yaw     =  angularvelocity;


        double frontLeftPower  = axial + lateral + yaw;
        double frontRightPower = axial - lateral - yaw;
        double backLeftPower   = axial - lateral + yaw;
        double backRightPower  = axial + lateral - yaw;


        // Normalize the values so no wheel power exceeds 100%
        // This ensures that the robot maintains the desired motion.
        max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
        max = Math.max(max, Math.abs(backLeftPower));
        max = Math.max(max, Math.abs(backRightPower));

        if (max > 1.0) {
            frontLeftPower  /= max;
            frontRightPower /= max;
            backLeftPower   /= max;
            backRightPower  /= max;
        }

        frontLeftDrive.setPower(frontLeftPower);
        frontRightDrive.setPower(frontRightPower);
        backLeftDrive.setPower(backLeftPower);
        backRightDrive.setPower(backRightPower);
        sleep(time);
        frontLeftDrive.setPower(0.0);
        frontRightDrive.setPower(0.0);
        backLeftDrive.setPower(0.0);
        backRightDrive.setPower(0.0);

    }
    public void fire() {
        firing = true;
        rightStopper.setPower(-1);
        leftStopper.setPower(1);
        sleep(Constants.STOPPER_DELAY);
        leftStopper.setPower(-1);
        rightStopper.setPower(1);
        firing = false;
    }
}