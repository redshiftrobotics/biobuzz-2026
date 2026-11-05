//package org.firstinspires.ftc.teamcode;
//
//
//
//import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
//import com.qualcomm.robotcore.eventloop.opmode.Disabled;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.hardware.CRServo;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.util.ElapsedTime;
//
//@Autonomous(name="Move Auto", group="Simple Autos")
//@Disabled
//public class BioBuzzTesting1 extends LinearOpMode {
//    private ElapsedTime runtime = new ElapsedTime();
//    private DcMotor frontLeftDrive = null;
//    private DcMotor backLeftDrive = null;
//    private DcMotor frontRightDrive = null;
//    private DcMotor backRightDrive = null;
//    private DcMotor flywheel = null;
//    private DcMotor intake = null;
//    private boolean flywheelEnabled = false;
//    private boolean intakeEnabled = false;
//    private boolean firing = false;
//
//    @Override
//    public void runOpMode() {
//
//        // Initialize the hardware variables. Note that the strings used here must correspond
//        // to the names assigned during the robot configuration step on the DS or RC devices.
//        frontLeftDrive = hardwareMap.get(DcMotor.class, "FL");
//        backLeftDrive = hardwareMap.get(DcMotor.class, "BL");
//        frontRightDrive = hardwareMap.get(DcMotor.class, "FR");
//        backRightDrive = hardwareMap.get(DcMotor.class, "BR");
//        flywheel = hardwareMap.get(DcMotor.class, "F");
//        intake = hardwareMap.get(DcMotor.class, "I");
//
//
//        // ########################################################################################
//        // !!!            IMPORTANT Drive Information. Test your motor directions.            !!!!!
//        // ########################################################################################
//        // Most robots need the motors on one side to be reversed to drive forward.
//        // The motor reversals shown here are for a "direct drive" robot (the wheels turn the same direction as the motor shaft)
//        // If your robot has additional gear reductions or uses a right-angled drive, it's important to ensure
//        // that your motors are turning in the correct direction.  So, start out with the reversals here, BUT
//        // when you first test your robot, push the left joystick forward and observe the direction the wheels turn.
//        // Reverse the direction (flip FORWARD <-> REVERSE ) of any wheel that runs backward
//        // Keep testing until ALL the wheels move the robot forward when you push the left joystick forward.
//        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
//        backLeftDrive.setDirection(DcMotor.Direction.REVERSE);
//        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
//        backRightDrive.setDirection(DcMotor.Direction.FORWARD);
//
//        frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//
//
//        // Wait for the game to start (driver presses START)
//        telemetry.addData("Status", "Initialized");
//        telemetry.update();
//
//        waitForStart();
//        runtime.reset();
//        double CPCM = (537.6 / (96 * Math.PI)) / 10;
//        double FL_SPIN = FL_DIST*Math.sin(45);
//        double FR_SPIN = FL_DIST*Math.sin(45);
//        double BL_SPIN = FL_DIST*Math.sin(45);
//        double BR_SPIN = FL_DIST*Math.sin(45);
//
//        int FL_SPINCM = (int)(FL_SPIN*CPCM);
//        int FR_SPINCM = (int)(FR_SPIN*CPCM);
//        int BL_SPINCM = (int)(BL_SPIN*CPCM);
//        int BR_SPINCM = (int)(BR_SPIN*CPCM);
//
//        frontLeftDrive.setTargetPosition(frontLeftDrive.getCurrentPosition() + FL_SPINCM);
//        frontRightDrive.setTargetPosition(frontRightDrive.getCurrentPosition() + FR_SPINCM);
//        backLeftDrive.setTargetPosition(backLeftDrive.getCurrentPosition() + BL_SPINCM);
//        backRightDrive.setTargetPosition(backRightDrive.getCurrentPosition() + BR_SPINCM);
//
//        frontLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        frontRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backLeftDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        backRightDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        frontLeftDrive.setPower(1);
//        frontRightDrive.setPower(1);
//        backRightDrive.setPower(1);
//        backRightDrive.setPower(1);
//        sleep(10000);
//    }
//
//
//}
