package com.serber.valeria.paymentapp.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.serber.valeria.paymentapp.Configuration;

import java.util.HashMap;
import java.util.Map;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

public class RetrofitService {

    private static Retrofit sRetrofit;
    private static Map<Class, Object> sServices;

    public static void initialize() {
        sServices = new HashMap<>();

        Gson gson = new com.google.gson.GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();

        sRetrofit = new Retrofit.Builder()
                .baseUrl(Configuration.BASE_URL_MERCADO_PAGO)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

    }

    public static <T> T getService(Class<T> clazz) {
        T service = (T) sServices.get(clazz);
        if (service != null) return service;
        service = sRetrofit.create(clazz);
        sServices.put(clazz, service);
        return service;
    }

}
