package org.firstinspires.ftc.teamcode.PPR1.essentials;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

/*
* This is meant to be a library!
* Do not copy and edit the code
* Import it in a new java class!
*
* DO NOT MODIFY
*/

public class PPR1EssentialsAuto extends LinearOpMode {
    public DcMotor LF; // LeftFront Motor
    public DcMotor LB; // LeftBack Motor
    public DcMotor RF; // RightFront Motor
    public DcMotor RB; // RightBack Motor

    public DcMotor RS; // RightSlide Motor
    public DcMotor LS; // LeftSlide Motor

    public Servo FBS; //FourBarLinkage Servo
    public Servo CS; //Claw Servo

    public BNO055IMU IMU;

    public int tickRotation;
    public int tickAcceptance;

    public OpenCvWebcam webcam;
    public PPR1SleeveDetector detector;

    public PPR1EssentialsAuto(int tickRotation, int tickAcceptance){
        this.tickRotation = tickRotation;
        this.tickAcceptance = tickAcceptance;
    }

    public void initialize(){
        LF = hardwareMap.get(DcMotor.class, "LeftFront");
        LF.setDirection(DcMotorSimple.Direction.REVERSE);
        LF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ((DcMotorEx) LF).setTargetPositionTolerance(tickAcceptance);

        LB = hardwareMap.get(DcMotor.class, "LeftBack");
        LB.setDirection(DcMotorSimple.Direction.REVERSE);
        LB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ((DcMotorEx) LB).setTargetPositionTolerance(tickAcceptance);

        RF = hardwareMap.get(DcMotor.class, "RightFront");
        RF.setDirection(DcMotorSimple.Direction.FORWARD);
        RF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ((DcMotorEx) RF).setTargetPositionTolerance(tickAcceptance);

        RB = hardwareMap.get(DcMotor.class, "RightBack");
        RB.setDirection(DcMotorSimple.Direction.FORWARD);
        RB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        ((DcMotorEx) RB).setTargetPositionTolerance(tickAcceptance);


        LS = hardwareMap.get(DcMotor.class, "LeftSlide");
        LS.setDirection(DcMotorSimple.Direction.REVERSE);
        LS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        RS = hardwareMap.get(DcMotor.class, "RightSlide");
        RS.setDirection(DcMotorSimple.Direction.REVERSE);
        RS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        FBS = hardwareMap.get(Servo.class, "FourBarServo");
        CS = hardwareMap.get(Servo.class, "ClawServo");

        IMU = hardwareMap.get(BNO055IMU.class,"IMU");

    }

    public void runCamera(){
        final String webcamName = "Webcam 1";
        final int length = 320;
        final int width = 240;
        final int squareSize = 30;

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        webcam = OpenCvCameraFactory.getInstance().createWebcam(hardwareMap.get(WebcamName.class, webcamName), cameraMonitorViewId);

        detector = new PPR1SleeveDetector(telemetry, length, width, squareSize, 30, -56);
        webcam.setPipeline(detector);

        webcam.setMillisecondsPermissionTimeout(2500); // Timeout for obtaining permission is configurable. Set before opening.
        webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
        {
            @Override
            public void onOpened()
            {
                webcam.startStreaming(length, width, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode)
            {

            }
        });

    }
    public void stopCamera(){webcam.stopStreaming();}


    public void reset(){
        stopDrivetrain();

        LF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        LB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RF.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        RB.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void stopDrivetrain(){
        LF.setPower(0);
        LB.setPower(0);
        RF.setPower(0);
        RB.setPower(0);
    }

    public void initIMU() {
        BNO055IMU.Parameters imuParameters;

        imuParameters = new BNO055IMU.Parameters();
        imuParameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        imuParameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        // Express acceleration as m/s^2.
        imuParameters.loggingEnabled = false;
        // Disable logging.
        IMU.initialize(imuParameters);
        // Initialize IMU.
    }


    public void goRotations(double rotations, double power){
        reset();
        int rotationsInTicks = (int) (rotations*tickRotation);

        LF.setTargetPosition(rotationsInTicks);
        LB.setTargetPosition(rotationsInTicks);
        RF.setTargetPosition(rotationsInTicks);
        RB.setTargetPosition(rotationsInTicks);

        LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RB.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        LF.setPower(power);
        LB.setPower(power);
        RF.setPower(power);
        RB.setPower(power);

        while(LF.isBusy() ||
              LB.isBusy() ||
              RF.isBusy() ||
              RB.isBusy()
        ){
            telemetry.addData("LF Current Position", LF.getCurrentPosition());
            telemetry.addData("LB Current Position", LB.getCurrentPosition());
            telemetry.addData("RF Current Position", RF.getCurrentPosition());
            telemetry.addData("RB Current Position", RB.getCurrentPosition());
            telemetry.update();
        }

        stopDrivetrain();
    }

    public void strafeLeft(double distance, double power){
        goDiagonal(-distance, distance, distance, -distance, power, power, power, power);
    }

    public void strafeRight(double distance, double power){
        goDiagonal(distance, -distance, -distance, distance, power, power, power, power);
    }

    public void goDiagonal(double dLF, double dLB, double dRF, double dRB, double sLF, double sLB, double sRF, double sRB) {
        reset();
        LF.setTargetPosition((int) (560 * dLF));
        LB.setTargetPosition((int) (560 * dLB));
        RB.setTargetPosition((int) (560 * dRB));
        RF.setTargetPosition((int) (560 * dRF));
        LF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RB.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RF.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        LF.setPower(sLF);
        LB.setPower(sLB);
        RB.setPower(sRB);
        RF.setPower(sRF);
        while (!(!LF.isBusy() && !LB.isBusy() && !RB.isBusy() && !RF.isBusy())) {
            // Put loop blocks here.
        }
        stopDrivetrain();
    }

    public void turnAngle(int inputangle, double speed) {
        initialize();
        LF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        LB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        RF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        if (IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle < inputangle) {
            while (!(IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle >= inputangle)) {
                LF.setPower(-speed);
                LB.setPower(-speed);
                RB.setPower(speed);
                RF.setPower(speed);
            }
        } else if (IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle > inputangle) {
            while (!(IMU.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle <= inputangle)) {
                LF.setPower(speed);
                LB.setPower(speed);
                RB.setPower(-speed);
                RF.setPower(-speed);
            }
        }
        LF.setPower(0);
        LB.setPower(0);
        RB.setPower(0);
        RF.setPower(0);
    }

    public void moveSlides(int posInTicks){
        RS.setTargetPosition(posInTicks);
        RS.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        RS.setPower(1);
        while (RS.isBusy()){}
    }



    @Override
    public void runOpMode(){
    }
}
