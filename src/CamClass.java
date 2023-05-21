/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author matgo
 */
import org.opencv.core.*;
import org.opencv.highgui.HighGui;
import org.opencv.videoio.VideoCapture;
import org.opencv.imgcodecs.Imgcodecs;

public class CamClass{
    public static void main (String args[]){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        VideoCapture camera = new VideoCapture(0);
        
        if(!camera.isOpened()){
            System.out.println("Error");
        }else{
                Mat frame = new Mat();
            while(true){
                if(camera.read(frame)){
                    System.out.println("Frame Obtained"); //status da ação
                    System.out.println("Captured Frame Width" +
                    frame.width() + " Heigh " + frame.height()); //printa o tamanho em pixels da imagem (desnecessário)
                    Imgcodecs.imwrite("camera.jpg", frame); //coloca o nome no arquivo
                    System.out.println("OK!");
                    break;
                }   
            }
        }
        camera.release();
    }
}
