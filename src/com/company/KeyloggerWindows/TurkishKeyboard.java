package com.company.KeyloggerWindows;

import lc.kra.system.keyboard.event.GlobalKeyEvent;

import java.util.HashMap;

public class TurkishKeyboard
{

    public static final int TR_ENTER = 13;
    public static final int TR_SLASH = 111;
    public static final int TR_STAR = 106;
    public static final int TR_MINUS = 109;
    public static final int TR_PLUS_NUMPAD = 107;
    public static final int TR_COMMA = 110;

    public static final int TR_Ğ = 219;
    public static final int TR_Ü = 221;
    public static final int TR_Ş = 186;
    public static final int TR_İ = 222;
    public static final int TR_Ö = 191;
    public static final int TR_Ç = 220;
    public static final int TR_I = 73;
    public static final int TR_COLON = 190;
    public static final int TR_SEMICOLON = 188;
    public static final int TR_SIGN = 81;
    public static final int TR_TILDE = 221;


    public static final char[] SPECIAL_COLLECTION_1 = "!'^+%&/()=?_é".toCharArray();
    public  static final char[] SPECIAL_COLLECTION_2 = ">£#$½{[]}\\|<".toCharArray();

    private static final HashMap<Integer, String> TURKISH_CAPITALS = new HashMap<>(){
        {
            put(TR_Ğ,"Ğ");
            put(TR_Ü,"Ü");
            put(TR_Ş,"Ş");
            put(TR_İ,"İ");
            put(TR_Ö,"Ö");
            put(TR_Ç,"Ç");
            put(TR_I,"I");
            put(TR_COLON,":");
            put(TR_SEMICOLON,";");
        }
    };

    private static final HashMap<Integer, String> F_SERIES = new HashMap<>(){
        {
            put(GlobalKeyEvent.VK_F1," [F1] ");
            put(GlobalKeyEvent.VK_F2," [F2] ");
            put(GlobalKeyEvent.VK_F3," [F3] ");
            put(GlobalKeyEvent.VK_F4," [F4] ");
            put(GlobalKeyEvent.VK_F5," [F5] ");
            put(GlobalKeyEvent.VK_F6," [F6] ");
            put(GlobalKeyEvent.VK_F7," [F7] ");
            put(GlobalKeyEvent.VK_F8," [F8] ");
            put(GlobalKeyEvent.VK_F9," [F9] ");
            put(GlobalKeyEvent.VK_F10," [F10] ");
            put(GlobalKeyEvent.VK_F11," [F11] ");
            put(GlobalKeyEvent.VK_F12," [F12] ");
        }
    };

    public static String getTurkishLetter(int key)
    {
        return TURKISH_CAPITALS.get(key);
    }
    public static String getFSeries(int key)
    {
        return F_SERIES.get(key);
    }

}
