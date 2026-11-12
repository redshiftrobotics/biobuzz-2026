package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;

public class Constants {
    public static final double FLYWHEEL_POWER = -0.75;
    public static final double INTAKE_POWER = 1.0;
    public static final int STOPPER_DELAY = 750;

    public static final PIDConstant DRIVE_PID = new PIDConstant(1,0,0.1);
    public static final double TICKS_PER_REV = 537.6;
    public static final double WHEEL_DIA = 32;
    public static final double WHEEL_CIRCUMFERENCE = WHEEL_DIA*Math.PI;
    public static final double CM_PER_TICK = WHEEL_CIRCUMFERENCE * TICKS_PER_REV / 10;
    public static final double PID_MAX_SPEED = 0.25;
    public static final double FLOWER_UP = 0.0;
    public static final double FLOWER_DOWN = 0.0;
    public static final double X_OFFSET = 0.0;
    public static final double Y_OFFSET = 0.0;
    public static final GoBildaPinpointDriver.EncoderDirection XE_DIR = GoBildaPinpointDriver.EncoderDirection.FORWARD;
    public static final GoBildaPinpointDriver.EncoderDirection YE_DIR = GoBildaPinpointDriver.EncoderDirection.FORWARD;
}
