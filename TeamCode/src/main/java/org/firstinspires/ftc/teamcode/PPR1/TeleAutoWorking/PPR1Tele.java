package org.firstinspires.ftc.teamcode.PPR1.TeleAutoWorking;


import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.PPR1.essentials.PPR1EssentialsTeleOp;


@TeleOp
public class PPR1Tele extends PPR1EssentialsTeleOp {
    public PPR1Tele(){
        super(2000);
    }


    @Override
    public void runOpMode(){
        initialize();
        waitForStart();
        while (opModeIsActive()){
           gamepad1Controls();
           gamepad2Controls();

        }
    }
}
