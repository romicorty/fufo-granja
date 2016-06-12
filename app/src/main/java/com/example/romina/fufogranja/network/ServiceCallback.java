package com.example.romina.fufogranja.network;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class ServiceCallback<S, E> implements Callback<S> {

    @Override
    public void failure(RetrofitError error) {
        Type superclass = getClass().getGenericSuperclass();
        Type t = ((ParameterizedType)superclass).getActualTypeArguments()[0];
        E e = (E) error.getBodyAs(t);
        failure(e, error.getResponse());
    }

    public abstract void failure(E error, Response response);
}
