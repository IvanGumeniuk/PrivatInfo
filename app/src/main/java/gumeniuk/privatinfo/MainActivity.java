package gumeniuk.privatinfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

import gumeniuk.privatinfo.ATM.CashMashineInfo;
import gumeniuk.privatinfo.ATM.CashMashines;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyApp app = (MyApp)this.getApplicationContext();

        final ArrayList<CashMashineInfo> atms = new ArrayList<>();

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                app.getAtmService().getData("","Днепр").enqueue(new Callback<CashMashines>() {
                    @Override
                    public void onResponse(Call<CashMashines> call, Response<CashMashines> response) {
                        atms.addAll(response.body().getCashMashineInfo());
                        Toast.makeText(MainActivity.this, String.valueOf(atms.size()), Toast.LENGTH_LONG).show();
                    }



                    @Override
                    public void onFailure(Call<CashMashines> call, Throwable t) {
                        Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                    }

                });
            }
        });
    }



}
