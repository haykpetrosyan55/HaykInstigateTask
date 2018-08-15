package com.example.hp.haykinstigatetask;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.hp.haykinstigatetask.models.ModelForUser;
import com.example.hp.haykinstigatetask.models.Result;
import com.example.hp.haykinstigatetask.services.ApiServices;
import com.example.hp.haykinstigatetask.services.Retrofit;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Result result;
    private String Tag = "Main";
    private String MAP_KEY = "key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        result = (Result) getIntent().getSerializableExtra(MAP_KEY);
        retrofitClient();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        BitmapDescriptor icon = BitmapDescriptorFactory.fromResource(R.drawable.ic_face_red);
        LatLng vanadzor = new LatLng(40.814133, 44.484070);
        MarkerOptions marker = new MarkerOptions();
        mMap.addMarker(marker.position(vanadzor).title("Vanadzor").icon(icon));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(vanadzor));

//        final Double lat = Double.valueOf(result.getLocation().getCoordinates().getLatitude());
//        final Double lng = Double.valueOf(result.getLocation().getCoordinates().getLongitude());
//        LatLng user = new LatLng(lat, lng);
//        mMap.addMarker(new MarkerOptions().position(user).title(result.getName().getFirst()));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(user));

    }

    private void retrofitClient() {
        ApiServices client = Retrofit.getClient().create(ApiServices.class);
        client.randomUsers(10).enqueue(new Callback<ModelForUser>() {
            @Override
            public void onResponse(@NonNull Call<ModelForUser> call,
                                   @NonNull Response<ModelForUser> response) {
                List<Result> list = response.body().getResults();
                //adapter.setData(list);
            }

            @Override
            public void onFailure(@NonNull Call<ModelForUser> call, @NonNull Throwable t) {
                Log.e(Tag, t.toString());
            }
        });
    }
}
