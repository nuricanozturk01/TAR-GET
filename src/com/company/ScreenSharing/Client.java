package com.company.ScreenSharing;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.*;

/**
 * Client is a master computer. You should check the ip address.
 */
public class Client extends JFrame implements Runnable
{


    private int port;
    private JPanel panel;
    private String ip;

    public Client(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
        initComponents();
        setLocationRelativeTo(this);

    }

    private void initComponents()
    {
        setBounds(new Rectangle((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()+0.25),
                (int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight() + 0.25)));
        setResizable(true);
        panel = new JPanel();
        panel.setBounds(new Rectangle((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        panel.setMaximumSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        panel.setMinimumSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        panel.setPreferredSize(new Dimension((int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(),
                (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight()));
        add(panel);
        setVisible(true);

    }

    @Override
    public void run()
    {
        try
        {
            while(true)
            {
                Socket client = new Socket(ip, port);
                BufferedImage image = ImageIO.read(client.getInputStream());
                panel.getGraphics().drawImage(image,0,0,panel.getWidth(),panel.getHeight(),null);
            }
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(null,"server error!");
        }
    }

    public static void main(String[] args)
    {
        Client c = new Client("192.168.8.129", 1010);
        c.run();
    }

}
