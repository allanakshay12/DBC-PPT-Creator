package com.example.choirpptcreator;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface LyricsGetDataService {

    @GET("/lyrics")
    Call<RetroLyrics> getLyrics(@Query("lyquery") String lyrics);

    @GET("/readings")
    Call<RetroReadings> getReadings(@Query("rdchoice") String day);

    @POST("/createppt")
    Call<Download_Link> sendPPTData(@Body PPTData pptdata);
}
