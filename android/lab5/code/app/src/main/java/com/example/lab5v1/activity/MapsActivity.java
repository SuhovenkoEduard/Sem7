package com.example.lab5v1.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.lab5v1.R;
//import com.example.lab5v1.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
//    private ActivityMapsBinding binding;
    private ArrayList<String> latitudesArray = new ArrayList<>();
    private ArrayList<String> longitudesArray = new ArrayList<>();
    private JSONArray resultArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        onMapUniversity(mMap);
        getCurrentPosition(mMap);
    }

    public void onMapUniversity(GoogleMap mMap) {
        LatLng university = new LatLng(52.4062435860273, 30.939300860880945);
        mMap.addMarker(new MarkerOptions().position(university).title("Marker in University"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(university));
        mMap.setMinZoomPreference(15);
    }

    public void getCurrentPosition(GoogleMap googleMap) {

        final JSONArray[] resultArray = new JSONArray[1];

        LatLng myDevice = new LatLng(52.4068247, 30.9370456);
        googleMap.addMarker(new MarkerOptions().position(myDevice)
                .title("Your device"));

        String url = "https://maps.googleapis.com/maps/api/place/nearbysearch/json"
                + "?location=" + 52.4068247 + "," + 30.9370456
                + "&radius=1000" + "&type=" + "bank" + "&sensor=true" + "&key="
                + "YFkO5HeS8r5fX2ZfVlpQMYoc";

        Request request = new Request.Builder()
                .url(url)
                .build();

        new OkHttpClient().newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, final Response response) throws IOException {
                        try {
                            JSONObject res = new JSONObject(response.body().string());
                            resultArray[0] = res.getJSONArray("results");
                            if (resultArray[0].length() == 0) {

                            } else {

                                for (int j = 0; j < resultArray[0].length(); j++) {
                                    latitudesArray.add(resultArray[0].getJSONObject(j).getJSONObject("geometry").getJSONObject("location").getString("lat"));
                                    longitudesArray.add(resultArray[0].getJSONObject(j).getJSONObject("geometry").getJSONObject("location").getString("lng"));
                                }
                            }
                            if (response.isSuccessful()) {
                                runOnUiThread(() -> {
                                    for (int i = 0; i < resultArray[0].length(); i++) {
                                        LatLng myDevice1 = new LatLng(Double.parseDouble(latitudesArray.get(i)), Double.parseDouble(longitudesArray.get(i)));
                                        googleMap.addMarker(new MarkerOptions().position(myDevice1)
                                                .title("Bank"));
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myDevice));
    }

    public void foo(Context context) {
        SingleShotLocationProvider.requestSingleUpdate(context,
                new SingleShotLocationProvider.LocationCallback() {
                    @Override
                    public void onNewLocationAvailable(SingleShotLocationProvider.GPSCoordinates location) {
                        Log.d("Location", "my location is " + location.toString());
                        LatLng myDevice = new LatLng(location.latitude, location.longitude);
                        mMap.addMarker(new MarkerOptions().position(myDevice).title("Marker in Device"));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(myDevice));
                        mMap.setMinZoomPreference(15);
                    }
                });
    }

    public static class SingleShotLocationProvider {


        public static interface LocationCallback {
            public void onNewLocationAvailable(GPSCoordinates location);
        }

        public static void requestSingleUpdate(final Context context, final LocationCallback callback) {
            final LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
            if (isNetworkEnabled) {
                Criteria criteria = new Criteria();
                criteria.setAccuracy(Criteria.ACCURACY_COARSE);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                locationManager.requestSingleUpdate(criteria, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        callback.onNewLocationAvailable(new GPSCoordinates(location.getLatitude(), location.getLongitude()));
                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                    }

                    @Override
                    public void onProviderDisabled(String provider) {
                    }
                }, null);
            } else {
                boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
                if (isGPSEnabled) {
                    Criteria criteria = new Criteria();
                    criteria.setAccuracy(Criteria.ACCURACY_FINE);
                    locationManager.requestSingleUpdate(criteria, new LocationListener() {
                        @Override
                        public void onLocationChanged(Location location) {
                            callback.onNewLocationAvailable(new GPSCoordinates(location.getLatitude(), location.getLongitude()));
                        }

                        @Override public void onStatusChanged(String provider, int status, Bundle extras) { }
                        @Override public void onProviderEnabled(String provider) { }
                        @Override public void onProviderDisabled(String provider) { }
                    }, null);
                }
            }
        }


        // consider returning Location instead of this dummy wrapper class
        public static class GPSCoordinates {
            public float longitude = -1;
            public float latitude = -1;

            public GPSCoordinates(float theLatitude, float theLongitude) {
                longitude = theLongitude;
                latitude = theLatitude;
            }

            public GPSCoordinates(double theLatitude, double theLongitude) {
                longitude = (float) theLongitude;
                latitude = (float) theLatitude;
            }
        }
    }


}