package org.firstinspires.ftc.teamcode.PPR1.essentials;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class PPR1SleeveDetector extends OpenCvPipeline {
    Telemetry telemetry;
    Mat yellowMat = new Mat();
    Mat cyanMat = new Mat();

    static double PERCENT_COLOR_THRESHOLD;

    final Rect YELLOW_ROI;
    final Rect CYAN_ROI;



    int zoneNum;

    public PPR1SleeveDetector(Telemetry telemetry, int length, int width, int squareXY, int xOffset, int yOffset){
        super();

        this.telemetry = telemetry;

        YELLOW_ROI = new Rect(
                new Point(length/2 - squareXY/2 - xOffset-50, width/2 - squareXY - yOffset),
                new Point(length/2 + squareXY/2 - xOffset, width/2 - yOffset)
        );

        CYAN_ROI = new Rect(
                new Point(length/2 - squareXY/2 - xOffset-50, width/2 - squareXY - yOffset),
                new Point(length/2 + squareXY/2 - xOffset, width/2 - yOffset)
        );



        PERCENT_COLOR_THRESHOLD = 0.1;
    }

    @Override
    public Mat processFrame(Mat input) {

        Imgproc.cvtColor(input, yellowMat, Imgproc.COLOR_RGB2HSV);
        Imgproc.cvtColor(input, cyanMat, Imgproc.COLOR_RGB2HSV);



        Scalar lowYellowHSV = new Scalar(23, 60, 60);
        Scalar highYellowHSV = new Scalar(33, 255, 255);
        Scalar lowCyanHSV = new Scalar(84,60,60);
        Scalar highCyanHSV = new Scalar(120,255,255.);


        Core.inRange(yellowMat, lowYellowHSV, highYellowHSV, yellowMat);
        Core.inRange(cyanMat, lowCyanHSV, highCyanHSV, cyanMat);

        Mat yellow = yellowMat.submat(YELLOW_ROI);
        Mat cyan = cyanMat.submat(CYAN_ROI);

        double yellowValue = Core.sumElems(yellow).val[0] / YELLOW_ROI.area() / 255;
        double cyanValue = Core.sumElems(cyan).val[0] / CYAN_ROI.area() / 255;
        yellow.release();
        cyan.release();

        boolean yellowSleeve = yellowValue > PERCENT_COLOR_THRESHOLD;
        boolean cyanSleeve = cyanValue > PERCENT_COLOR_THRESHOLD;

        if (yellowSleeve) { zoneNum = 1; }
        else if (cyanSleeve) { zoneNum = 3; }
        else {zoneNum = 2;}

        Imgproc.cvtColor(yellowMat, yellowMat, Imgproc.COLOR_GRAY2RGB);
        Imgproc.cvtColor(cyanMat, cyanMat, Imgproc.COLOR_GRAY2RGB);

        Scalar colorBlock = new Scalar(255, 0, 0);
        Scalar colorCube = new Scalar(0, 255, 0);


        Imgproc.rectangle(cyanMat, CYAN_ROI, zoneNum == 3? colorBlock:colorCube);
        Imgproc.rectangle(yellowMat, YELLOW_ROI, zoneNum == 1? colorBlock:colorCube);

        telemetry.addData("Yellow Percentage", (yellowValue * 100) + "%");
        telemetry.addData("Cyan Percentage", (cyanValue * 100) + "%");
        telemetry.update();
        if (zoneNum == 3)
            return cyanMat;
        else return yellowMat;
    }

    public int getZoneNum() {
        return zoneNum;
    }
}