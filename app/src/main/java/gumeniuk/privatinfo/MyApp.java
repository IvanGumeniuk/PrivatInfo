package gumeniuk.privatinfo;

import android.app.Application;

import gumeniuk.privatinfo.ATM.AtmService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Ваня on 12.04.2017.
 */

public class MyApp extends Application {

    private static AtmService atmService;

    public static AtmService getAtmService() {
        return atmService;
    }


    @Override
    public void onCreate() {
        super.onCreate();


        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.privatbank.ua/p24api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        atmService = retrofit.create(AtmService.class);


    }
}


