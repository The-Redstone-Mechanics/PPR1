package org.firstinspires.ftc.teamcode.PPR1.TeleAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.PPR1.essentials.PPR1EssentialsAuto;

@Disabled
@Autonomous
public class PPR1MediumLeftV2 extends PPR1EssentialsAuto{
    public PPR1MediumLeftV2(){
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
        goRotations(0.5,0.5);
        // strafe right
        strafeRight(0.3,0.5);
        // move forward
        goRotations(1.6,0.45);
        //strafe left
        strafeLeft(1.15,0.1);
        // raise slide
        moveSlides(2880);
        //move forward
        goRotations(0.22,0.5);
        FBS.setPosition(1);
        sleep(2000);
        // open claw
        CS.setPosition(0.3);
        sleep(2000);
        //reset FBS
        FBS.setPosition(0.4);
        CS.setPosition(0.15);
        sleep(2000);
        // lower slide
        moveSlides(0);
        turnAngle(0,0.25);
        // back up a little more
        goRotations(-0.3,0.5);
        // depending on zoneNum strafe right or left (1 = right 3 = left)
        if (zoneNum == 1){
            strafeRight(3.5,1);
        }
        else if (zoneNum == 3){
            strafeLeft(1.3,1);
        }
        else {
            strafeRight(1,1);
        }

    }
}
