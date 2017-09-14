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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {


    private BeaconManager beaconManager;
    private BeaconRegion region;
    String urlCheck="http://13.59.212.226:5000/api/allProducts";
    String url="http://13.59.212.226:5000/api/allProducts";
    int countG=0, countL=0, countP=0;
    static  int DEFAULT=10;

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
        String tempUrl="";
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
                //Toast.makeText(MainActivity.this,"beacon 1: "+countG+". Beacon 2: "+countL+". Beacon 3: "+countP,Toast.LENGTH_SHORT).show();
                if (!list.isEmpty()) {
                    List<Beacon> filteredBeacons=new ArrayList<Beacon>();
                    for(int i=0;i<list.size();i++){
                        Beacon checkBeacon=list.get(i);
                        if(placesNearBeacon(checkBeacon) !=null && !placesNearBeacon(checkBeacon).isEmpty()){
                            filteredBeacons.add(checkBeacon);
                        }
                    }
                    Collections.sort(filteredBeacons, new Comparator<Beacon>() {
                        @Override
                        public int compare(Beacon beacon, Beacon t1) {
                            return beacon.getRssi() - t1.getRssi();
                        }
                    });
                    if(!filteredBeacons.isEmpty()){
                        Beacon nearestBeacon = filteredBeacons.get(0);
                        urlCheck = placesNearBeacon(nearestBeacon);
                        if(urlCheck.equals("http://13.59.212.226:5000/api/allProducts/grocery")){
                            if(countG<DEFAULT)
                                countG++;
                            if(countP>0)
                                countP--;
                            if(countL>0)
                                countL--;
                        } else if (urlCheck.equals("http://13.59.212.226:5000/api/allProducts/lifestyle")){
                            if(countL<DEFAULT)
                                countL++;
                            if(countP>0)
                                countP--;
                            if(countG>0)
                                countG--;
                        } else if(urlCheck.equals("http://13.59.212.226:5000/api/allProducts/produce")){
                            if(countP<DEFAULT)
                                countL++;
                            if(countL>0)
                                countL--;
                            if(countG>0)
                                countG--;
                        }
                    }

                    Log.d("URl", " : " + url);
                    if((!url.equals(urlCheck)) && ((countG==DEFAULT || countL==DEFAULT || countP==DEFAULT))){
                        url=urlCheck;
                        Intent productIntent=new Intent(MainActivity.this,ProductsActivity.class);
                        productIntent.putExtra("URL",url);
                        startActivity(productIntent);
                    }


                }else{
                    if(countG>0)
                        countG--;
                    else if(countL>0)
                        countL--;
                    else if(countP>0)
                        countP--;
                    if((!url.equals(urlCheck)) && (countL==0 && countP==0 && countG==0)){
                        url=urlCheck;
                        Intent productIntent=new Intent(MainActivity.this,ProductsActivity.class);
                        productIntent.putExtra("URL",url);
                        startActivity(productIntent);
                    }
                }
            }
        });
        region = new BeaconRegion("ranged region",
                UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
    }

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
        super.onPause();
        beaconManager.stopRanging(region);
    }
}
