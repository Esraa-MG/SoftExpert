package com.example.softexpert;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;

import com.example.softexpert.Constants.Constants;
import com.example.softexpert.Contracts.IAPI;
import com.example.softexpert.POJOs.Car;
import com.example.softexpert.POJOs.JsonData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CarList extends AppCompatActivity {

    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    CarAdapter adapter;

    private int page = 1;
    private int previousTotal = 0;
    private boolean loading = true;
    private int visibleThreshold = 5;
    private int firstVisibleItem = 0;
    private int visibleItemCount = 0;
    private int totalItemCount = 0;

    private SwipeRefreshLayout swipe;
    private List<Car> cars = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_list);

        recyclerView = findViewById(R.id.recycler_view);
        swipe = findViewById(R.id.swipeRefresh);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        start(page);
        refreshApp();

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                visibleItemCount = recyclerView.getChildCount();
                totalItemCount = layoutManager.getItemCount();
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition();
                if (loading) {
                    if (totalItemCount > previousTotal) {
                        loading = false;
                        previousTotal = totalItemCount;
                    }
                }
                if (!loading && totalItemCount - visibleItemCount
                        <= firstVisibleItem + visibleThreshold
                ) {
                    page++;
                    start(page);
                    loading = true;
                }
            }
        });
    }

    private void refreshApp() {
        swipe.setOnRefreshListener(() -> {
            page = 1;
            previousTotal = 0;
            loading = true;
            visibleThreshold = 5;
            firstVisibleItem = 0;
            visibleItemCount = 0;
            totalItemCount = 0;
            cars.clear();
            start(page);
            swipe.setRefreshing(false);
        });
    }

    private void start(int page) {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        IAPI apiInterface = retrofit.create(IAPI.class);
        Call<JsonData> call = apiInterface.getCars(page);
        call.enqueue(new Callback<JsonData>() {
            @Override
            public void onResponse(Call<JsonData> call, Response<JsonData> response) {
                JsonData data = response.body();
                if(data != null){
                    cars.addAll(data.getData());
                    adapter = new CarAdapter(CarList.this, cars);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<JsonData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }
}