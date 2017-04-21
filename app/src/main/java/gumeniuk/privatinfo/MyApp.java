package gumeniuk.privatinfo;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.UUID;

import gumeniuk.privatinfo.Database.ATM;
import gumeniuk.privatinfo.Database.City;
import gumeniuk.privatinfo.Database.Shedule;
import gumeniuk.privatinfo.Database.TSO;
import gumeniuk.privatinfo.GoogleMapApi.LocationWorking;
import gumeniuk.privatinfo.PrivatApi.AtmService;
import gumeniuk.privatinfo.PrivatApi.Device;
import gumeniuk.privatinfo.PrivatApi.TsoService;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MyApp extends Application {

    private static AtmService atmService;
    private static TsoService tsoService;
    private static LocationWorking locationWorking;
    private Realm realm;

    public static AtmService getAtmService() {
        return atmService;
    }

    public static TsoService getTsoService() {
        return tsoService;
    }

    public static LocationWorking getLocationWorking() {
        return locationWorking;
    }

    public Realm getRealm() {
        return realm;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration
                .Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(config);
        realm = Realm.getDefaultInstance();

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

    public void setRealmData(final String cityName, final ArrayList<Device> atms, final ArrayList<Device> tsos) {

        final ArrayList<TSO> listTSO = new ArrayList<>();
        final ArrayList<ATM> listATM = new ArrayList<>();
        Shedule shedule;
        for (Device device : tsos) {
            shedule = new Shedule(
                    UUID.randomUUID().toString(),
                    device.getTw().getMon(),
                    device.getTw().getTue(),
                    device.getTw().getWed(),
                    device.getTw().getThu(),
                    device.getTw().getFri(),
                    device.getTw().getSat(),
                    device.getTw().getSun(),
                    device.getTw().getHol());

            listTSO.add(new TSO(
                    UUID.randomUUID().toString(),
                    device.getCityRU(),
                    device.getFullAddressRu(),
                    device.getPlaceRu(),
                    device.getLatitude(),
                    device.getLongitude(),
                    shedule));
        }

        for (Device device : atms) {
            shedule = new Shedule(
                    UUID.randomUUID().toString(),
                    device.getTw().getMon(),
                    device.getTw().getTue(),
                    device.getTw().getWed(),
                    device.getTw().getThu(),
                    device.getTw().getFri(),
                    device.getTw().getSat(),
                    device.getTw().getSun(),
                    device.getTw().getHol());

            listATM.add(new ATM(
                    UUID.randomUUID().toString(),
                    device.getCityRU(),
                    device.getFullAddressRu(),
                    device.getPlaceRu(),
                    device.getLatitude(),
                    device.getLongitude(),
                    shedule));
        }

        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                City city = realm.createObject(City.class, UUID.randomUUID().toString());
                city.setName(cityName);
                RealmList<ATM> atmRealmList = new RealmList<>();
                RealmList<TSO> tsoRealmList = new RealmList<>();
                atmRealmList.addAll(listATM);
                tsoRealmList.addAll(listTSO);

                if (!atmRealmList.isManaged()) {
                    RealmList<ATM> managedATM = new RealmList<>();
                    for (ATM atm : atmRealmList)
                        if (atm.isManaged()) {
                            managedATM.add(atm);
                        } else {
                            managedATM.add(realm.copyToRealm(atm));
                        }
                    atmRealmList = managedATM;
                }

                if (!tsoRealmList.isManaged()) {
                    RealmList<TSO> managedTSO = new RealmList<>();
                    for (TSO tso : tsoRealmList)
                        if (tso.isManaged())
                            managedTSO.add(tso);
                        else
                            managedTSO.add(realm.copyToRealm(tso));
                    tsoRealmList = managedTSO;
                }

                city.setAtms(atmRealmList);
                city.setTsos(tsoRealmList);
            }
        });

    }

    public City getRealmData(final String cityName) {
        final City[] result = new City[1];
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                result[0] = realm.where(City.class).equalTo(getString(R.string.name), cityName).findFirst();
            }
        });
        return result[0];
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        realm.close();
    }
}


