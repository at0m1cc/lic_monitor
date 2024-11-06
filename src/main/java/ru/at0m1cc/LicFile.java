package ru.at0m1cc;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.http.HttpClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;

public class LicFile {
    private File fileSource;
    private File fileParsed;
    private List<Programm> licList;

    public LicFile(String path,String fileSourceName) throws IOException{
        fileSource = new File(path,fileSourceName);
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

    public void sendToAPI() throws IOException{
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost("http://localhost");
        List<NameValuePair> nameValue = new ArrayList<>();
        int count = 0;
        for (Programm i : getLicList()){
            nameValue.add(new BasicNameValuePair(i.getNameProgramm(),  i.getUsersProgramm().toString()));
        }
        post.setEntity(new UrlEncodedFormEntity(nameValue));
        client.execute(post);
    }
}