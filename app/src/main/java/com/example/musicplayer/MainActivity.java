package com.example.musicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;

import android.os.Build;
import android.os.Bundle;


import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity{

    Button playclick, pauseclick, nextclick, prevclick, show_location;
    TextView addressText;
    private BroadcastReceiver broadcastReceiver;

    MediaPlayer mp;
    int currentPlayingSongIndex = 0;
    int[] playList;
    int currentPlayingSongResId = 0;

    @Override
    protected void onResume() {
        super.onResume();
        if(broadcastReceiver == null){
            broadcastReceiver = new BroadcastReceiver() {
                @Override
                public void onReceive(Context context, Intent intent) {

                    addressText.append("\n" +intent.getExtras().get("coordinates"));

                }
            };
        }
        registerReceiver(broadcastReceiver,new IntentFilter("location_update"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(broadcastReceiver != null){
            unregisterReceiver(broadcastReceiver);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playclick = findViewById(R.id.play);
        pauseclick = findViewById(R.id.pause);
        nextclick = findViewById(R.id.next);
        addressText = findViewById(R.id.addressText);
        prevclick = findViewById(R.id.prev);
        show_location = findViewById(R.id.show_location);

        if(!runtime_permissions())
            enable_buttons();


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
                if(currentPlayingSongResId == songResId)
                {
                    fromPause = true;
                }
            }


        }
        currentPlayingSongResId = songResId;
        if(!fromPause) mp = MediaPlayer.create(this,songResId);
        mp.start();
    }

    private void enable_buttons() {

        show_location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i =new Intent(getApplicationContext(),GPS_Service.class);
                startService(i);
            }
        });


    }



    private boolean runtime_permissions() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);

            return true;
        }
        return false;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if( grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                enable_buttons();
            }else {
                runtime_permissions();
            }
        }
    }

}


