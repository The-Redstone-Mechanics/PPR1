package org.firstinspires.ftc.teamcode.PPR1.TeleAuto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;

import org.firstinspires.ftc.teamcode.PPR1.essentials.PPR1EssentialsAuto;

@Disabled
@Autonomous
public class PPR1Parking extends PPR1EssentialsAuto{
    public PPR1Parking(){
        super(560,10);
    }
    @Override
    public void runOpMode(){
        initialize();
        reset();
        runCamera();

        waitForStart();
        int zoneNum = detector.getZoneNum();
        stopCamera();
        telemetry.addData("zone number",zoneNum);
        telemetry.update();
        goRotations(0.5,1);
        if (zoneNum == 1){strafeLeft(1.7,0.5);}
        if (zoneNum==2){strafeRight(0.5,0.5);}
        if (zoneNum==3){strafeRight(2.7,0.5);}
        goRotations(2,1);
    }
}
