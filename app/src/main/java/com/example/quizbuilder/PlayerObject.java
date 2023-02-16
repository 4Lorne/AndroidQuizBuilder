package com.example.quizbuilder;

import java.io.Serializable;

public class PlayerObject implements Serializable {
    private String name;

    public PlayerObject(){

    }

    public PlayerObject(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
