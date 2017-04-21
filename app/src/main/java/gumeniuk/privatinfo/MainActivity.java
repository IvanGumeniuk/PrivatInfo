package gumeniuk.privatinfo;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.DecimalFormat;
import java.util.ArrayList;

import gumeniuk.privatinfo.Database.ATM;
import gumeniuk.privatinfo.Database.City;
import gumeniuk.privatinfo.Database.TSO;
import gumeniuk.privatinfo.GoogleMapApi.TownRequest;
import gumeniuk.privatinfo.PrivatApi.CashMashines;
import gumeniuk.privatinfo.PrivatApi.Device;
import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    public MyApp app;
    double latitude;
    double longitude;
    GoogleMap mMap;
    private ArrayList<Device> atms;
    private ArrayList<Device> tsos;
    private ProgressDialog mProgressDialog;
    private ArrayList<ATM> mapATMS;
    private ArrayList<TSO> mapTSOS;
    public City city;
    public String cityName;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        app = (MyApp) this.getApplicationContext();

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        atms = new ArrayList<>();
        tsos = new ArrayList<>();
        mapATMS = new ArrayList<>();
        mapTSOS = new ArrayList<>();

        getUserLocation();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.Research) {
            getUserLocation();
            return true;
        }
        if (id == R.id.Exit) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Are you sure?");
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    System.exit(0);
                }
            });
            builder.show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void privatRequest(final String name) {
        MyApp.getAtmService().getData("", "", name).enqueue(new Callback<CashMashines>() {
            @Override
            public void onResponse(Call<CashMashines> call, Response<CashMashines> response) {
                atms.addAll(response.body().getDevices());
            }

            @Override
            public void onFailure(Call<CashMashines> call, Throwable t) {
                hideProgressDialog();
                Toast.makeText(MainActivity.this, "ATM finding error. Check internet connection", Toast.LENGTH_LONG).show();
            }
        });

        MyApp.getTsoService().getData("", "", name).enqueue(new Callback<CashMashines>() {
            @Override
            public void onResponse(Call<CashMashines> call, Response<CashMashines> response) {
                tsos.addAll(response.body().getDevices());
                app.setRealmData(name, atms, tsos);
                city = app.getRealmData(name);
                hideProgressDialog();
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
        if(city!=null && mapTSOS.size()==0 && mapATMS.size()==0)
        {
            mapTSOS.addAll(city.getTsos());
            mapATMS.addAll(city.getAtms());
        }
        LatLng latlng;
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.mipmap.atm_marker);
        // for (ATM device : mapATMS) {
        for (ATM device : mapATMS) {
            latlng = new LatLng(Double.parseDouble(device.getLatitude()), Double.parseDouble(device.getLongitude()));
            mMap.addMarker(new MarkerOptions().position(latlng)
                    .title(device.getPlace())
                    .icon(icon)
                    .snippet("ATM"));
        }

        icon = BitmapDescriptorFactory.fromResource(R.mipmap.tso_marker);
        for (TSO device : mapTSOS) {
            latlng = new LatLng(Double.parseDouble(device.getLatitude()), Double.parseDouble(device.getLongitude()));
            mMap.addMarker(new MarkerOptions().position(latlng)
                    .title(device.getPlace())
                    .icon(icon)
                    .snippet("TSO"));
        }
        hideProgressDialog();

    }

    public String formatPosition(double value) {
        DecimalFormat myFormatter = new DecimalFormat("00.000000");
        return myFormatter.format(value);
    }


    private void clickMarker(final Marker marker) {
        final String lat = formatPosition(marker.getPosition().latitude).trim();
        final String lng = formatPosition(marker.getPosition().longitude).trim();
        final ATM[] atm = new ATM[1];
        final TSO[] tso = new TSO[1];
        app.getRealm().executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                if (marker.getSnippet().equals("ATM")) {
                    atm[0] = realm.where(ATM.class).equalTo("latitude", lat).findAll()
                            .where().equalTo("longitude", lng).findFirst();
                    showMarkerInfo(atm[0]);
                } else {
                    tso[0] = realm.where(TSO.class).equalTo("latitude", lat).findAll()
                            .where().equalTo("longitude", lng).findFirst();
                    showMarkerInfo(tso[0]);
                }
            }
        });

    }


    private void showMarkerInfo(ATM atm) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Information");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));

        TextView info = new TextView(this);
        TextView address = new TextView(this);
        TextView shedule = new TextView(this);
        if (atm != null) {
            info.setText("\n" + atm.getPlace() + "\n");
            info.setTextColor(ContextCompat.getColor(this, R.color.black));
            address.setText(atm.getFullAddress() + "\n");
            address.setTextColor(ContextCompat.getColor(this, R.color.black));
            shedule.setText(atm.getShedule().toString());
            shedule.setTextColor(ContextCompat.getColor(this, R.color.black));
        }

        layout.addView(info);
        layout.addView(address);
        layout.addView(shedule);
        builder.setView(layout);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void showMarkerInfo(TSO tso) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Information");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        TextView info = new TextView(this);
        TextView address = new TextView(this);
        TextView shedule = new TextView(this);
        if (tso != null) {
            info.setText("\n" + tso.getPlace() + "\n");
            info.setTextColor(ContextCompat.getColor(this, R.color.black));
            address.setText(tso.getFullAddress() + "\n");
            address.setTextColor(ContextCompat.getColor(this, R.color.black));
            shedule.setText(tso.getShedule().toString());
            shedule.setTextColor(ContextCompat.getColor(this, R.color.black));
        }
        else

        
        layout.addView(info);
        layout.addView(address);
        layout.addView(shedule);
        builder.setView(layout);

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);

            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    clickMarker(marker);
                    return false;
                }
            });

        } else {
            Toast.makeText(this, "You have to accept to enjoy all app's services!", Toast.LENGTH_LONG).show();
        }


    }

    private void getUserLocation() {
        showProgressDialog(this);
        GPSTracker gps = new GPSTracker(this, this);

        if (gps.canGetLocation()) {
            latitude = gps.getLatitude();
            longitude = gps.getLongitude();
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

                        cityName = String.valueOf(response.body().getResults().get(i).getAddressComponents().get(1).getShortName().trim());
                        city = app.getRealmData(cityName);
                        if (city != null) {
                            createMarkers();
                            break;
                        }
                        privatRequest(response.body().getResults().get(i).getAddressComponents().get(1).getShortName().trim());
                        break;
                    } else if (i == response.body().getResults().size() - 1) {
                        hideProgressDialog();
                        Toast.makeText(MainActivity.this, "Cannot find the city", Toast.LENGTH_SHORT).show();
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
