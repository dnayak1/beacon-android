package com.example.dhirajnayak.introtobeacons;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.estimote.coresdk.common.requirements.SystemRequirementsChecker;
import com.estimote.coresdk.observation.region.beacon.BeaconRegion;
import com.estimote.coresdk.recognition.packets.Beacon;
import com.estimote.coresdk.service.BeaconManager;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {

    public int[] acceptedMajors = {15212,48071,26535};


    Button buttonAllProducts;
    private BeaconManager beaconManager;
    private BeaconRegion region;
    String url="http://13.59.212.226:5000/api/allProducts";

    private static final Map<String, String> PLACES_BY_BEACONS;
    static {
        Map<String, String> placesByBeacons = new HashMap<>();
        placesByBeacons.put("15212:31506","http://13.59.212.226:5000/api/allProducts/grocery");
        placesByBeacons.put("48071:25324","http://13.59.212.226:5000/api/allProducts/lifestyle");
        placesByBeacons.put("26535:44799","http://13.59.212.226:5000/api/allProducts/produce");
        PLACES_BY_BEACONS = Collections.unmodifiableMap(placesByBeacons);
    }

    private String placesNearBeacon(Beacon beacon) {
        String beaconKey = String.format("%d:%d", beacon.getMajor(), beacon.getMinor());
        String tempUrl= "http://13.59.212.226:5000/api/allProducts";
        if (PLACES_BY_BEACONS.containsKey(beaconKey)) {
            tempUrl= PLACES_BY_BEACONS.get(beaconKey);
        }
        return tempUrl ;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        beaconManager = new BeaconManager(this);
        beaconManager.setRangingListener(new BeaconManager.BeaconRangingListener() {
            @Override
            public void onBeaconsDiscovered(BeaconRegion region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    for (Beacon beacon : list) {
                        if (beacon.getMajor() == acceptedMajors[0] || beacon.getMajor() == acceptedMajors[0] || beacon.getMajor() == acceptedMajors[0]) {
                            Toast.makeText(MainActivity.this,"Beacon found",Toast.LENGTH_SHORT);
                        }

                    }
//                    Beacon nearestBeacon = list.get(0);
                }
            }
        });


//        buttonAllProducts= (Button) findViewById(R.id.buttonShowAllProducts);
//        buttonAllProducts.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent=new Intent(MainActivity.this,ProductsActivity.class);
//                startActivity(intent);
//            }
//        });
    }

    /*private void flashPageByBeacon(Beacon beacon){
        if (region==null){
            region = new BeaconRegion("ranged region",
                    UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
        }
        String urlCheck = placesNearBeacon(nearestBeacon);
        // TODO: update the UI here
        Log.d("URl", " : " + url);
        if(!url.equals(urlCheck)){
            url=urlCheck;
            Intent productIntent=new Intent(MainActivity.this,ProductsActivity.class);
            productIntent.putExtra("URL",url);
            startActivity(productIntent);
        }
        else{
        url="http://13.59.212.226:5000/api/allProducts";
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        SystemRequirementsChecker.checkWithDefaultDialogs(this);
        beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                beaconManager.startRanging(region);
            }
        });

        Intent productIntent=new Intent(MainActivity.this,ProductsActivity.class);
        productIntent.putExtra("URL",url);
        startActivity(productIntent);
    }

    @Override
    protected void onPause() {
/*
super.onPause();
beaconManager.stopRanging(region);
*/
    }
}
