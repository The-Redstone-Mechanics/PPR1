package org.firstinspires.ftc.teamcode.PPR1.TeleAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.PPR1.essentials.PPR1EssentialsAuto;

@Disabled
@Autonomous
public class PPR1MediumRightV1 extends PPR1EssentialsAuto{
    public PPR1MediumRightV1(){
        super(560,10);
    }
    @Override
    public void runOpMode() {
        initialize();
        reset();
        runCamera();
        initIMU();

        CS.setPosition(0.475);
        waitForStart();
        int zoneNum = detector.getZoneNum();
        stopCamera();

        sleep(100);
        // move forward
        goRotations(0.5,1);
        // strafe right
        strafeRight(0.5,1);
        // move forward
        goRotations(1.7,1);
        // turn 45 degrees right
        turnAngle(-37,0.25);
        // move forward
        goRotations(0.75,0.5);
        // raise slide
        moveSlides(5700);
        // move FBS to ~0.4
        FBS.setPosition(0.4);
        sleep(1000);
        // open claw
        CS.setPosition(0);
        sleep(1500);
        // move backward
        goRotations(-0.65,0.5);
        // lower slide
        moveSlides(0);
        // turn 45 degrees left
        turnAngle(0,0.25);
        // back up a little more
        goRotations(-0.2,0.5);
        // depending on zoneNum strafe right or left (1 = right 3 = left)
        if (zoneNum == 1){
            strafeRight(2.3,1);
        }
        else if (zoneNum == 3){
            strafeLeft(2.5,1);
        }
    }
}
