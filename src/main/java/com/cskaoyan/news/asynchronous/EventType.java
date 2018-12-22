package com.cskaoyan.news.asynchronous;

public enum EventType{

    LIKE(0),
    DISLIKE(1);
    private  int value;
     EventType(int value)
    {this.value=value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
