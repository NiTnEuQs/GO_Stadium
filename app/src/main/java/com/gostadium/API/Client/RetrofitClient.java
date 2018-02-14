package com.gostadium.API.Client;

import retrofit2.GsonConverterFactory;
import retrofit2.Retrofit;

/**
 * La classe qui initialise le client Retrofit
 */
public class RetrofitClient {

    private static Retrofit retrofit = null;

    public static Retrofit getClient(String urlApi) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(urlApi)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit;
    }

}