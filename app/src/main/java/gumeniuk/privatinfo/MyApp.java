package gumeniuk.privatinfo;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import gumeniuk.privatinfo.GoogleMapApi.LocationWorking;
import gumeniuk.privatinfo.PrivatApi.AtmService;
import gumeniuk.privatinfo.PrivatApi.TsoService;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by Ваня on 12.04.2017.
 */

public class MyApp extends Application {

    private static AtmService atmService;
    private static TsoService tsoService;
    private static LocationWorking locationWorking;

    public static AtmService getAtmService() {
        return atmService;
    }

    public static TsoService getTsoService() {
        return tsoService;
    }

    public static LocationWorking getLocationWorking() {
        return locationWorking;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

         Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.privatbank.ua/p24api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        atmService = retrofit.create(AtmService.class);
        tsoService = retrofit.create(TsoService.class);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/geocode/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        locationWorking = retrofit.create(LocationWorking.class);
    }
}


