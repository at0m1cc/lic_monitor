package ru.at0m1cc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    public static void main(String[] args) throws IOException{
        Settings.load("settings.ini");
        String queryNanoCAD = "cmd /k cd \"" + Settings.getPathNanoCAD() + "\" & lmutil lmstat -a -c 27001@localhost > NanoCAD.txt";
        String queryCSoft = "cmd /k cd \"" + Settings.getPathCSoft() + "\" & lmutil lmstat -a -c 27000@localhost > CSoft.txt";
        try {
            ServerSocket serverSocket = new ServerSocket(Settings.getPort()); // Создаём сервер сокет
            while (true) {
                Socket socket = serverSocket.accept(); // Ждём подключения к нашему сокету
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Получение информации с клиента
                String request = reader.readLine();//Передаём всю информацию с клиента
                //Проверка команд
                switch (request) {
                    case "showLic" -> {
                        Runtime.getRuntime().exec(queryNanoCAD);
                        Runtime.getRuntime().exec(queryCSoft);
                        LicFile nanoCAD = new LicFile(Settings.getPathNanoCAD() ,"NanoCAD.txt");
                        LicFile cSoft = new LicFile(Settings.getPathCSoft(),"CSoft.txt");
                        nanoCAD.parse();
                        cSoft.parse();
                        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(new File("parsed_NanoCAD.txt")));
                        BufferedOutputStream bos = new BufferedOutputStream(socket.getOutputStream());
                        byte[] byteArr = new byte[13240];
                        int byteR;
                        for(;(byteR = bis.read(byteArr)) != -1;){
                            bos.write(byteArr, 0, byteR);
                            bos.flush();
                        }
                        bis.close();
                        bos.close();
                    }
                }
                reader.close();
            }
            
        } catch (IOException e) {
            throw new RuntimeException(e); //Если что-либо пошло не по плану
        }
    }
}
