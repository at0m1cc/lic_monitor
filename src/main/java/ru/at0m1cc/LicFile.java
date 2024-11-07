package ru.at0m1cc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LicFile {
    private File fileSource;
    private File fileParsed;
    private List<Programm> licList;

    public LicFile(String fileSourceName) throws IOException{
        fileSource = new File(fileSourceName);
        fileParsed = new File("parsed_"+fileSourceName);
        fileParsed.createNewFile();
        licList = new ArrayList<>();
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
        parseToList();
    }

    private void parseToList() throws FileNotFoundException{
        Scanner scanner = new Scanner(fileParsed);
        String i = "";
        int id = 0;
        for(;scanner.hasNextLine();){
            i = scanner.nextLine();
            if (licList.isEmpty()) {
                licList.add(new Programm());
                licList.get(id).setNameProgramm(i);
                i = scanner.nextLine();
            }
            if(i.contains("Users")){
                licList.add(new Programm());
                id++;
                licList.get(id).setNameProgramm(i);
            }
            if (i.contains("    ")){
                licList.get(id).addUser(i.substring(4));
            }
        }
        scanner.close();
    }

    public List<Programm> getLicList(){
        return licList;
    }
}