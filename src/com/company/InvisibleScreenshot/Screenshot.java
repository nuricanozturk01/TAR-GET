package com.company.InvisibleScreenshot;


import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class Screenshot
{
    public static final String SCREENSHOT_EXTENSION = ".png";
    public static void takeScreenShot(int second,String path)
    {
        String  title = LocalDate.now().getDayOfMonth()+"_"+LocalDate.now().getMonthValue()+"_"+LocalDate.now().getYear()+"_"+LocalTime.now().getHour();
        new File(path + title).mkdir();
        while (true)
        {
               try
               {
                   BufferedImage image = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
                   String fileName = LocalTime.now().getHour()+"_"+LocalTime.now().getMinute()+"_"+LocalTime.now().getSecond()+"_"+LocalTime.now().getNano();
                   ImageIO.write(image, "png", new File(path +title+"/"+fileName+SCREENSHOT_EXTENSION));
                   Thread.sleep(second * 1000);
               }
               catch (IOException | InterruptedException | AWTException e)
               {
                   e.printStackTrace();
               }
        }
    }

    public static void main(String[] args)
    {
        // You should change the screenshot path
        Screenshot.takeScreenShot(10,"C:\\Users\\public\\");
    }

}
