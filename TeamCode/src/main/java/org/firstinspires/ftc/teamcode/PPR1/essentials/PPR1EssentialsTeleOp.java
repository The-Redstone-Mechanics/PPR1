package org.firstinspires.ftc.teamcode.PPR1.essentials;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.Servo;

/*
* This is meant to be a library!
* Do not copy and edit the code
* Import it in a new java class!
*
* DO NOT MODIFY
*/

public class PPR1EssentialsTeleOp extends LinearOpMode {
    public DcMotor LF; // LeftFront Motor
    public DcMotor LB; // LeftBack Motor
    public DcMotor RF; // RightFront Motor
    public DcMotor RB; // RightBack Motor

    public DcMotor RS; // RightSlide Motor
    public DcMotor LS; // LeftSlide Motor

    public Servo CS; //Claw Servo
    public Servo FBS; // FourBar Servo

    public final double PRECISION_POWER = 0.1;

    double vertical;
    double horizontal;
    float pivot;

    double clawGrab = 0.5; //Lower grabs more
    double clawOpenFront = 1; //
    double clawOpenBack = 1;

    int drivetrainVelocityRate; // Converts power to ticks


    public PPR1EssentialsTeleOp(int drivetrainVelocityRate) {
        this.drivetrainVelocityRate = drivetrainVelocityRate;
    }

    public void initialize() {
        LF = hardwareMap.get(DcMotor.class, "LeftFront");
        LF.setDirection(DcMotorSimple.Direction.FORWARD);
        LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        LB = hardwareMap.get(DcMotor.class, "LeftBack");
        LB.setDirection(DcMotorSimple.Direction.FORWARD);
        LB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RF = hardwareMap.get(DcMotor.class, "RightFront");
        RF.setDirection(DcMotorSimple.Direction.REVERSE);
        RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        RB = hardwareMap.get(DcMotor.class, "RightBack");
        RB.setDirection(DcMotorSimple.Direction.REVERSE);
        RB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        LS = hardwareMap.get(DcMotor.class, "LeftSlide");
        LS.setDirection(DcMotorSimple.Direction.REVERSE);
        LS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        RS = hardwareMap.get(DcMotor.class, "RightSlide");
        RS.setDirection(DcMotorSimple.Direction.REVERSE);
        RS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        RS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        CS = hardwareMap.get(Servo.class, "ClawServo");
        FBS = hardwareMap.get(Servo.class, "FourBarServo");
    }

    public void gamepad1Controls() {
        // Initiating Holonomic Drive
        vertical = gamepad1.right_stick_y * drivetrainVelocityRate;
        horizontal = -gamepad1.right_stick_x * drivetrainVelocityRate;
        pivot = -gamepad1.left_stick_x * drivetrainVelocityRate;

        ((DcMotorEx) LF).setVelocity(pivot + vertical + horizontal);
        ((DcMotorEx) LB).setVelocity(vertical - horizontal + pivot);
        ((DcMotorEx) RF).setVelocity(vertical - horizontal - pivot);
        ((DcMotorEx) RB).setVelocity(vertical + horizontal - pivot);

    }
    public void clawFBScontrol(){

        if (gamepad2.b && RS.getCurrentPosition()<400) {
            CS.setPosition(clawOpenFront);
        }
        if (gamepad2.b && RS.getCurrentPosition()>=400){
            CS.setPosition(clawOpenBack);
        }
        if (gamepad2.x) {
            CS.setPosition(clawGrab);
        }
        if (gamepad2.y) {
            FBS.setPosition(0);
        }
        if (gamepad2.a) {
            FBS.setPosition(1);
        }

    }
    public void gamepad2Controls() {
        clawFBScontrol();
        if (gamepad2.dpad_left){
            CS.setPosition(0.05);
            FBS.setPosition(0.05);
            RS.setTargetPosition(1400);
            RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RS.setPower(1);
            while (RS.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",RS.getCurrentPosition()); telemetry.update(); clawFBScontrol();}
        }
        else if (gamepad2.dpad_up){
            CS.setPosition(0.05);
            FBS.setPosition(0.4);
            RS.setTargetPosition(3250);
            RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RS.setPower(1);

            while (RS.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",RS.getCurrentPosition()); telemetry.update(); clawFBScontrol();}
        }
        else if(gamepad2.dpad_right){
            CS.setPosition(0.05);
            FBS.setPosition(0.05);
            RS.setTargetPosition(2650);
            RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RS.setPower(1);

            while (RS.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",RS.getCurrentPosition()); telemetry.update(); clawFBScontrol();}
        }
        else if(gamepad2.dpad_down){
            CS.setPosition(0.15);
            FBS.setPosition(1);
            RS.setTargetPosition(0);
            RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RS.setPower(1);

            while (RS.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",RS.getCurrentPosition()); telemetry.update(); clawFBScontrol();}
        }
        else{
            RS.setPower(0.05);
        }

        if (gamepad2.left_bumper && RS.getCurrentPosition() > 100){

            RS.setTargetPosition(RS.getCurrentPosition()-100);
            RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RS.setPower(1);


            while (RS.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",RS.getCurrentPosition()); telemetry.update(); clawFBScontrol();}

        }
        if (gamepad2.right_bumper && RS.getCurrentPosition() < 3000){
            RS.setTargetPosition(RS.getCurrentPosition()+100);
            RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            RS.setPower(1);

            while (RS.isBusy()){gamepad1Controls(); telemetry.addData("RS Height",RS.getCurrentPosition()); telemetry.update(); clawFBScontrol();}
        }

    }
    @Override
    public void runOpMode(){}
    }
