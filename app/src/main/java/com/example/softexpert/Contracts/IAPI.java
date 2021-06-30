package com.example.softexpert.Contracts;

import com.example.softexpert.Constants.Constants;
import com.example.softexpert.POJOs.JsonData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IAPI {

    @GET(Constants.URI)
    Call<JsonData> getCars(@Query("page") int page);
}
