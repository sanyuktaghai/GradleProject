package com.example;

public class ClassJoke {

    private String myJoke;

    public String getMyJoke() {
        if(myJoke ==null || myJoke=="")
        {
            myJoke=getJoke();
        }
        return myJoke;
    }

    public void setMyJoke(String joke) {
        myJoke = joke;
    }

    public String getJoke(){
        return "Java Library says Hi! Thats the joke!!!";
    }
}
