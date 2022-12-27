package org.firstinspires.ftc.teamcode.PPR1.TeleAutoWorking;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.PPR1.essentials.PPR1EssentialsAuto;

@Autonomous
public class PPR1MediumRightV3 extends PPR1EssentialsAuto{
    public PPR1MediumRightV3(){
        super(560,10);
    }
    @Override
    public void runOpMode() {
        initialize();
        reset();
        runCamera();
        initIMU();

        CS.setPosition(0.15);
        waitForStart();
        int zoneNum = detector.getZoneNum();
        stopCamera();

        CS.setPosition(0);
        sleep(100);
        FBS.setPosition(0.8);
        sleep(1000);
        CS.setPosition(0.3);
        sleep(100);
        FBS.setPosition(1);
        sleep(100);
        CS.setPosition(0);
        sleep(100);
        FBS.setPosition(0.5);
        sleep(100);
        // move forward
        goRotations(0.2,0.5);
        // strafe right
        strafeRight(0.3,0.5);
        // move forward
        goRotations(2,0.45);
        //strafe left
        strafeRight(1.1111,0.1);
        // raise slide
        moveSlides(2880);
        //move forward
        goRotations(0.18,0.5);
        FBS.setPosition(1);
        sleep(2000);
        moveSlides(2600);
        // open claw
        CS.setPosition(0.3);
        sleep(2000);
        //reset FBS
        moveSlides(2880);
        FBS.setPosition(0.4);
        CS.setPosition(0.15);
        sleep(2000);
        // lower slide
        moveSlides(0);
        turnAngle(0,0.25);
        // back up a little more
        goRotations(-0.3,0.5);
        // depending on zoneNum strafe right or left (1 = right 3 = left)
        if (zoneNum == 3){
            strafeRight(1.2,1);
        }
        else if (zoneNum == 1){
            strafeLeft(3.5,1);
        }
        else {
            strafeLeft(1,1);
        }

    }
}
