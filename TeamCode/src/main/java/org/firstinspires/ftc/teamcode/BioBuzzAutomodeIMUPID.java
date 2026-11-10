package org.firstinspires.ftc.teamcode;


import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorGoBildaPinpoint;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;

@Autonomous(name="Move Auto PID", group="Simple Autos")
public class BioBuzzAutomodeIMUPID extends LinearOpMode {
    private ElapsedTime runtime = new ElapsedTime();
    private DcMotorEx frontLeftDrive = null;
    private DcMotorEx backLeftDrive = null;
    private DcMotorEx frontRightDrive = null;
    private DcMotorEx backRightDrive = null;
    private GoBildaPinpointDriver posSensor = null;

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
        posSensor = hardwareMap.get(GoBildaPinpointDriver.class, "POS");

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





        while(opModeIsActive()) {



            setSetpoints(new Pose2D(DistanceUnit.CM, 10,10, AngleUnit.DEGREES, 0));

            Pose2D pos = posSensor.getPosition();
            double axial   = drive_yPID.performPID(pos.getY(DistanceUnit.CM));
            double lateral = drive_xPID.performPID(pos.getX(DistanceUnit.CM));
            double yaw     = drive_yawPID.performPID(pos.getHeading(AngleUnit.DEGREES));

            telemetry.addData("axial", axial);
            telemetry.addData("lateral", lateral);
            telemetry.update();
            // Set up a variable for each drive wheel to save the power level for telemetry.
            double frontLeftPower  = axial + lateral + yaw;
            double frontRightPower = axial - lateral - yaw;
            double backLeftPower   = axial - lateral + yaw;
            double backRightPower  = axial + lateral - yaw;

//            // Normalize the values so no wheel power exceeds 100%
//            // This ensures that the robot maintains the desired motion.
//            max = Math.max(Math.abs(frontLeftPower), Math.abs(frontRightPower));
//            max = Math.max(max, Math.abs(backLeftPower));
//            max = Math.max(max, Math.abs(backRightPower));
//
//            if (max > 1.0) {
//                frontLeftPower  /= max;
//                frontRightPower /= max;
//                backLeftPower   /= max;
//                backRightPower  /= max;
//            }



            frontLeftDrive.setPower(frontLeftPower);
            frontRightDrive.setPower(frontRightPower);
            backLeftDrive.setPower(backLeftPower);
            backRightDrive.setPower(backRightPower);
        }


    }
    public void setSetpoints(Pose2D destination) {

        drive_xPID.setSetpoint(destination.getX(DistanceUnit.CM));
        drive_yPID.setSetpoint(destination.getY(DistanceUnit.CM));
        drive_yawPID.setSetpoint(destination.getHeading(AngleUnit.DEGREES));
    }

}
