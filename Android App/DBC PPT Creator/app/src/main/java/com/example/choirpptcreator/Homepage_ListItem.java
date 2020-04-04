package com.example.choirpptcreator;

public class Homepage_ListItem {
    private String title, content;
    int key;

    public Homepage_ListItem(String title, String content, int key) {
        this.title = title;
        this.content = content;
        this.key = key;
    }

    public String getContent() {
        return content;
    }

    public String getTitle() {
        return title;
    }



    public int getKey() { return key; }

    public void setContent(String content) {
        this.content = content;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

