package com.company.KeyloggerWindows;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class FileOperation implements IFileOperation
{
    @Override
    public void writeFile(String str, String fileName, boolean isAppend)
    {
        try(FileOutputStream fos = new FileOutputStream(new File(fileName), isAppend);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, StandardCharsets.UTF_8)))
        {
            if (str.equals("[ENTER] "))
                writer.newLine();
            writer.write(str);

        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> readFile(String fileName)
    {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName)))
        {
            String line ;
            while ((line = reader.readLine()) != null)
                lines.add(line);

        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

}
