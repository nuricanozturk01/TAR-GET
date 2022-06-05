package com.company.ScreenSharing;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;


/**
 *
 * Server is a target machine.
 */
public class Server implements Runnable
{
    private ServerSocket socket;
    private Socket client;
    private Robot robot;
    private int width;
    private int height;


    public Server(int port)
    {
        try
        {
            socket = new ServerSocket(port);
            robot = new Robot();
            width = (int) Toolkit.getDefaultToolkit().getScreenSize().getWidth();
            height = (int) Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        }
        catch (IOException | AWTException e)
        {
            e.printStackTrace();
        }
    }


    @Override
    public void run()
    {
        try
        {
            while (true)
            {
                client = socket.accept();
                BufferedImage img = robot.createScreenCapture(new Rectangle(0, 0, width, height));
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(img, "png", outputStream);
                client.getOutputStream().write(outputStream.toByteArray());

            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }



    public static void main(String[] args)
    {
        Server server = new Server(1010);
        server.run();


    }
}
