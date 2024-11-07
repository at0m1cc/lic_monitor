package ru.at0m1cc;

import java.io.File;
import java.io.IOException;

import org.ini4j.Ini;


public class Settings {

    private static Ini ini;

    private static String pathNanoCAD;

    private static String pathCSoft;

    private static int port;

    public static void load(String iniName){
        try {
            ini = new Ini(new File(iniName));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pathCSoft = ini.get("lic","pathNanoCAD");
        pathCSoft = ini.get("lic","pathCSoft");
        port = Integer.parseInt(ini.get("server","4444"));
    }

    public static String getPathNanoCAD(){
        return pathNanoCAD;
    }
    public static String getPathCSoft(){
        return pathCSoft;
    }
    public static int getPort(){
        return port;
    }

}
