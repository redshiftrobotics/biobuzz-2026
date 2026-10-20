package org.firstinspires.ftc.teamcode;



import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Basic: Omni Linear OpMode", group="Linear OpMode")
public class BioBuzzAutomode1 extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotor frontLeftDrive = null;
    private DcMotor backLeftDrive = null;
    private DcMotor frontRightDrive = null;
    private DcMotor backRightDrive = null;

    static final double     COUNTS_PER_MOTOR_REV    = 1440 ;    // eg: TETRIX Motor Encoder
    static final double     DRIVE_GEAR_REDUCTION    = 1.0 ;     // No External Gearing.
    static final double     WHEEL_DIAMETER_INCHES   = 4.0 ;     // For figuring circumference
    static final double     COUNTS_PER_INCH         = (COUNTS_PER_MOTOR_REV * DRIVE_GEAR_REDUCTION) /
            (WHEEL_DIAMETER_INCHES * 3.1415);

    static final double     DRIVE_SPEED             = 0.6;
    static final double     TURN_SPEED              = 0.5;

    @Override
    public void runOpMode() {

        // Initialize the hardware variables. Note that the strings used here must correspond
        // to the names assigned during the robot configuration step on the DS or RC devices.
        frontLeftDrive = hardwareMap.get(DcMotor.class, "FL");
        backLeftDrive = hardwareMap.get(DcMotor.class, "BL");
        frontRightDrive = hardwareMap.get(DcMotor.class, "FR");
        backRightDrive = hardwareMap.get(DcMotor.class, "BR");

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

        // Wait for the game to start (driver presses START)
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        waitForStart();
        runtime.reset();
        XDriveForward(DRIVE_SPEED,  48,  48, 5.0);  // S1: Forward 47 Inches with 5 Sec timeout

        sleep(250);
    }

        public void XDriveForward(double speed,
                                  double leftInches, double rightInches,
                                  double timeoutS) {
            int newLeftTarget;
            int newRightTarget;

            // Ensure that the OpMode is still active
            if (opModeIsActive()) {

                // Determine new target position, and pass to motor controller
                newLeftTarget = frontLeftDrive.getCurrentPosition() + (int)(leftInches * COUNTS_PER_INCH);
                newRightTarget = backRightDrive.getCurrentPosition() + (int)(rightInches * COUNTS_PER_INCH);
                frontLeftDrive.setTargetPosition(newLeftTarget);
                backRightDrive.setTargetPosition(newRightTarget);

                // Turn On RUN_TO_POSITION
                frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

                // reset the timeout time and start motion.
                runtime.reset();
                frontLeftDrive.setPower(Math.abs(speed));
                backRightDrive.setPower(Math.abs(speed));

                // keep looping while we are still active, and there is time left, and both motors are running.
                // Note: We use (isBusy() && isBusy()) in the loop test, which means that when EITHER motor hits
                // its target position, the motion will stop.  This is "safer" in the event that the robot will
                // always end the motion as soon as possible.
                // However, if you require that BOTH motors have finished their moves before the robot continues
                // onto the next step, use (isBusy() || isBusy()) in the loop test.
                while (opModeIsActive() &&
                        (runtime.seconds() < timeoutS) &&
                        (frontLeftDrive.isBusy() && backRightDrive.isBusy())) {

                    // Display it for the driver.
                    telemetry.addData("Running to",  " %7d :%7d", newLeftTarget,  newRightTarget);
                    telemetry.addData("Currently at",  " at %7d :%7d",
                            frontLeftDrive.getCurrentPosition(), backRightDrive.getCurrentPosition());
                    telemetry.update();
                }

                // Stop all motion;
                frontLeftDrive.setPower(0);
                backRightDrive.setPower(0);

                // Turn off RUN_TO_POSITION
                frontLeftDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
                backRightDrive.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

                sleep(250);   // optional pause after each move.

        }}}



