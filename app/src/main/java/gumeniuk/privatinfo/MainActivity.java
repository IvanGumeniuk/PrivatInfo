package gumeniuk.privatinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import gumeniuk.privatinfo.GoogleMapApi.TownRequest;
import gumeniuk.privatinfo.PrivatApi.Device;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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


     /*   MyApp.getAtmService().getData("", "", "Львов").enqueue(new Callback<CashMashines>() {
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
*/
     button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             MyApp.getLocationWorking().getData("48.696716,26.582536","ru").enqueue(new Callback<TownRequest>() {
                 @Override
                 public void onResponse(Call<TownRequest> call, Response<TownRequest> response) {
                     Toast.makeText(MainActivity.this, String.valueOf(response.body().getResults().get(0).getAddressComponents().get(2).getShortName()), Toast.LENGTH_SHORT).show();
                 }

                 @Override
                 public void onFailure(Call<TownRequest> call, Throwable t) {
                     Toast.makeText(MainActivity.this, "wtf", Toast.LENGTH_SHORT).show();
                 }
             });
         }
     });


        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "atms:" + atms.size() + "\ntsos:" + tsos.size(), Toast.LENGTH_SHORT).show();
            }
        });

    }


}
