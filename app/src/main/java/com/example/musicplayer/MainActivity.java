package com.example.musicplayer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity { //implements LocationListener{

    Button playclick, pauseclick, nextclick, prevclick;
    TextView adressText;
    /*protected LocationManager locationManager;
    protected LocationListener locationListener;
    protected Context context;
    protected String latitude,longitude;
    protected boolean gps_enabled,network_enabled;*/
    MediaPlayer mp;
    int currentPlayingSongIndex = 0;
    int[] playList;
    int curretPlayingSongResId = 0;
    //String lat;
    //String provider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playclick = (Button)findViewById(R.id.play);
        pauseclick = (Button)findViewById(R.id.pause);
        nextclick = (Button)findViewById(R.id.next);
        prevclick = (Button)findViewById(R.id.prev);
        adressText =(TextView)findViewById(R.id.addressText);

        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, (LocationListener) this);

        playList = new int[]{
                R.raw.yalin,
                R.raw.inna,
                R.raw.jenniferlopez
        };



        playclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playSong(playList[currentPlayingSongIndex]);

                }
        });

        pauseclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mp.pause();
            }
        });

        nextclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                currentPlayingSongIndex++;
                currentPlayingSongIndex = (currentPlayingSongIndex>2)? 0 :currentPlayingSongIndex;
                playSong(playList[currentPlayingSongIndex]);
            }
        });

        prevclick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                for(int i = 0; i < 3 ; i++){
                    if(musicList.get(i).isPlaying()){
                        musicList.get(i-1).start();
                    }
                }

                 */

                currentPlayingSongIndex--;
                currentPlayingSongIndex = (currentPlayingSongIndex<0)? 2 :currentPlayingSongIndex;
                playSong(playList[currentPlayingSongIndex]);
            }
        });


    }

    private void playSong(int songResId)
    {
        boolean fromPause = false;
        if(mp != null){
            if(mp.isPlaying())
            {
                mp.stop();
            }
            else
            {
                if(curretPlayingSongResId == songResId)
                {
                    fromPause = true;
                }
            }


        }
        curretPlayingSongResId = songResId;
        if(!fromPause) mp = MediaPlayer.create(this,songResId);
        mp.start();
    }

    /*@Override
    public void onLocationChanged(Location location) {
        adressText = (TextView)findViewById(R.id.addressText);
        adressText.setText("Latitude:" + location.getLatitude() + ", Longitude:" + location.getLongitude());
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }*/
}


