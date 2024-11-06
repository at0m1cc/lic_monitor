package ru.at0m1cc;

import java.util.ArrayList;
import java.util.List;

public class Programm {
    private String nameProgramm;
    private List<String> usersProgramm;

    public String getNameProgramm() {
        return nameProgramm;
    }
    public List<String> getUsersProgramm() {
        return usersProgramm;
    }
    public void setNameProgramm(String nameProgramm) {
        this.nameProgramm = nameProgramm;
    }

    public void addUser(String user){
        usersProgramm.add(user);
    }

    public Programm(){
        usersProgramm = new ArrayList<>();
    }

}
