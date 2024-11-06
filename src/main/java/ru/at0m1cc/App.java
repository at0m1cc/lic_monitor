package ru.at0m1cc;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException{
        String pathNanoCAD = "c:\\Program Files (x86)\\Nanosoft\\Nanosoft License Server\\";
        String pathCSoft = "c:\\Program Files (x86)\\CSoft\\CS License Server\\";
        String queryNanoCAD = "cmd /k cd \"" + pathNanoCAD + "\" & lmutil lmstat -a -c 27001@localhost > NanoCAD.txt";
        String queryCSoft = "cmd /k cd \"" + pathCSoft + "\" & lmutil lmstat -a -c 27000@localhost > CSoft.txt";
        try {
            ServerSocket serverSocket = new ServerSocket(4444); // Создаём сервер сокет
            while (true) {
                Socket socket = serverSocket.accept(); // Ждём подключения к нашему сокету
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream())); // Получение информации с клиента
                String request = reader.readLine();//Передаём всю информацию с клиента
                //Проверка команд
                switch (request) {
                    case "showLic" -> {

                        Runtime.getRuntime().exec(queryNanoCAD);
                        Runtime.getRuntime().exec(queryCSoft);
                        LicFile nanoCAD = new LicFile(pathNanoCAD, "NanoCAD.txt");
                        LicFile cSoft = new LicFile(pathCSoft, "CSoft.txt");
                        nanoCAD.parse();
                        cSoft.parse();
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e); //Если что-либо пошло не по плану
        }
    }
}
