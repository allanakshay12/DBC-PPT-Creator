package com.example.choirpptcreator;

import com.google.gson.annotations.SerializedName;

public class Download_Link {

    @SerializedName("download_url")
    private String download_url;

    public Download_Link(String download_url) {
        this.download_url = download_url;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }
}
