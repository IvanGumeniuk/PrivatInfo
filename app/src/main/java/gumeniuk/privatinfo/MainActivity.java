package gumeniuk.privatinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import java.util.ArrayList;

import gumeniuk.privatinfo.GoogleMapApi.TownRequest;
import gumeniuk.privatinfo.PrivatApi.CashMashines;
import gumeniuk.privatinfo.PrivatApi.Device;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    double latitude;
    double longitude;
    GoogleMap mMap;
    private ArrayList<Device> atms;
    private ArrayList<Device> tsos;
    private ProgressDialog mProgressDialog;

    public ArrayList<Device> getAtms() {
        return atms;
    }

    public void setAtms(ArrayList<Device> atms) {
        this.atms = atms;
    }

    public ArrayList<Device> getTsos() {
        return tsos;
    }

    public void setTsos(ArrayList<Device> tsos) {
        this.tsos = tsos;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final MyApp app = (MyApp) this.getApplicationContext();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Button button = (Button) findViewById(R.id.button);
        Button button2 = (Button) findViewById(R.id.button2);

        atms = new ArrayList<>();
        tsos = new ArrayList<>();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getUserLocation();
            }
        });

        getUserLocation();
    }


    private void privatRequest(String city) {
        MyApp.getAtmService().getData("", "", city).enqueue(new Callback<CashMashines>() {
            @Override
            public void onResponse(Call<CashMashines> call, Response<CashMashines> response) {
                hideProgressDialog();
                Toast.makeText(MainActivity.this, "atms size in resp " + response.body().getDevices().size(), Toast.LENGTH_LONG).show();
                atms.addAll(response.body().getDevices());
            }

            @Override
            public void onFailure(Call<CashMashines> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(MainActivity.this, "ATM finding error. Check internet connection", Toast.LENGTH_LONG).show();
            }

        });

        MyApp.getTsoService().getData("", "", city).enqueue(new Callback<CashMashines>() {
            @Override
            public void onResponse(Call<CashMashines> call, Response<CashMashines> response) {
                Toast.makeText(MainActivity.this, "tsos size in resp " + response.body().getDevices().size(), Toast.LENGTH_LONG).show();
                tsos.addAll(response.body().getDevices());
                createMarkers();
            }

            @Override
            public void onFailure(Call<CashMashines> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(MainActivity.this, "TSO finding error. Check internet connection", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void createMarkers() {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

        } else {
            Toast.makeText(this, "You have to accept to enjoy all app's services!", Toast.LENGTH_LONG).show();
        }


    }

    private void showLatLng() {
        Toast.makeText(this, latitude + " " + longitude, Toast.LENGTH_LONG).show();
    }

    private void getUserLocation() {
        GPSTracker gps = new GPSTracker(this, this);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
            Toast.makeText(this, latitude + " " + longitude, Toast.LENGTH_LONG).show();

            loadData();
        } else {
            gps.showSettingsAlert();
        }
    }

    private void loadData() {

        MyApp.getLocationWorking().getData(latitude + "," + longitude, "ru").enqueue(new Callback<TownRequest>() {
            @Override
            public void onResponse(Call<TownRequest> call, Response<TownRequest> response) {
                for (int i = 1; i < response.body().getResults().size(); i++)
                    if (response.body().getResults().get(i).getTypes().get(0).equals("postal_code")) {
                        Toast.makeText(MainActivity.this, String.valueOf(response.body().getResults().get(i).getAddressComponents().get(1).getShortName().trim()), Toast.LENGTH_SHORT).show();
                        privatRequest(response.body().getResults().get(i).getAddressComponents().get(1).getShortName().trim());
                        break;
                    } else if (i == response.body().getResults().size() - 1) {
                        hideProgressDialog();
                        Toast.makeText(MainActivity.this, "Не вдалось знайти місто", Toast.LENGTH_SHORT).show();
                    }
            }

            @Override
            public void onFailure(Call<TownRequest> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(MainActivity.this, "Something is wrong. Check internet connection", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void showProgressDialog(Context context) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(context);
            mProgressDialog.setMessage("Loading...");
            mProgressDialog.setIndeterminate(true);
        }
        mProgressDialog.show();
    }

    public void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    /*  private void showStartDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("City name");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputTitle = new EditText(this);

        inputTitle.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PERSON_NAME);
        inputTitle.setHint("City name in russian");
        inputTitle.setHintTextColor(ContextCompat.getColor(this, R.color.grey));

        layout.addView(inputTitle);
        builder.setView(layout);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!inputTitle.getText().toString().trim().isEmpty() && validCity(inputTitle.getText().toString().trim())) {
                    privatRequest(inputTitle.getText().toString().trim());
                    showProgressDialog(MainActivity.this);
                } else {
                    Toast.makeText(MainActivity.this, "Please, enter sity name", Toast.LENGTH_SHORT).show();
                    showStartDialog();
                }
            }
        });

        builder.setNeutralButton("Use current location", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getUserLocation();
                dialog.dismiss();
                showProgressDialog(MainActivity.this);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        final AlertDialog dialog = builder.create();
        dialog.show();
    }*/

}
