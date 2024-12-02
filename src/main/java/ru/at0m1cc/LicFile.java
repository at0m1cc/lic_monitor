package ru.at0m1cc;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LicFile {
    private File fileSource;
    private File fileParsed;

    public LicFile(String pathToFile,String fileSourceName) throws IOException{
        fileSource = new File(pathToFile,fileSourceName);
        fileParsed = new File("parsed_"+fileSourceName);
        fileParsed.createNewFile();
    }

    public void parse() throws IOException{
        Scanner scanner = new Scanner(fileSource);
        FileWriter fw = new FileWriter(fileParsed);
        String i = "";
        for(;scanner.hasNextLine(); ){
            i = scanner.nextLine();
            if(i.contains("Users")){
                fw.append(i + "\n");
                for(;scanner.hasNextLine();){
                    fw.append(scanner.nextLine() + "\n");
                }
                fw.flush();
                fw.close();
            }
        }
        scanner.close();
    }
}