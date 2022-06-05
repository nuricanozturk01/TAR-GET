package com.company.KeyloggerWindows;

import java.util.ArrayList;

public interface IFileOperation
{
    void writeFile(String str, String fileName, boolean isAppend);
    ArrayList<String> readFile(String fileName);
}
