package gumeniuk.privatinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import java.util.ArrayList;

import gumeniuk.privatinfo.PrivatApi.Device;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyApp app = (MyApp) this.getApplicationContext();

        final ArrayList<Device> atms = new ArrayList<>();
        final ArrayList<Device> tsos = new ArrayList<>();

        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);

/*
        MyApp.getAtmService().getData("", "", "Львов").enqueue(new Callback<CashMashines>() {
            @Override
            public void onResponse(Call<CashMashines> call, Response<CashMashines> response) {
                if (response.body().getDevices() != null)
                    atms.addAll(response.body().getDevices());
            }

            @Override
            public void onFailure(Call<CashMashines> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }

        });
        MyApp.getTsoService().getData("", "", "Львов").enqueue(new Callback<CashMashines>() {
            @Override
            public void onResponse(Call<CashMashines> call, Response<CashMashines> response) {
                if (response.body().getDevices() != null)
                    tsos.addAll(response.body().getDevices());
            }

            @Override
            public void onFailure(Call<CashMashines> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        MyApp.getLocationWorking().getData("49.53245628,23.97216106", "ru").enqueue(new Callback<TownRequest>() {
            @Override
            public void onResponse(Call<TownRequest> call, Response<TownRequest> response) {
                Toast.makeText(MainActivity.this, String.valueOf(response.body().getResults().get(1).getAddressComponents().get(0).getShortName()), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<TownRequest> call, Throwable t) {
                Toast.makeText(MainActivity.this, "wtf", Toast.LENGTH_SHORT).show();
            }
        });
*/


    }


}
