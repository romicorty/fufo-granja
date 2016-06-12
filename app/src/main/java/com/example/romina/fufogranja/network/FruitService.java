package com.example.romina.fufogranja.network;

import com.example.romina.fufogranja.model.Fruit;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.http.GET;
import retrofit.http.Path;

public class FruitService{

    private final static String baseUrl = "http://quick-entities.herokuapp.com" ;

    public FruitService() {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(baseUrl).build();
        this.api = restAdapter.create(FruitServiceApi.class);
    }

    public interface FruitServiceApi{
        @GET("/fruits")
        void getFruits(Callback<List<Fruit>> fruits);

        @GET("/fruits/{id}")
        void getFruit(@Path("id") String fruitId, Callback<Fruit> fruit);
    }

    private FruitServiceApi api;

    public void getFruits(Callback<List<Fruit>> callback) {
        api.getFruits(callback);
    }

    public void getFruit(String fruitId,Callback<Fruit> fruit) {
        api.getFruit(fruitId, fruit);
    }
}
