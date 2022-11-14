package com.example.rasachatbotapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenu extends AppCompatActivity {

    CardView musicCard,alarmCard,chatbotCard,lightCard,healthCard,feedbackCard;

    int PERMISSION_ALL = 1;

    String[] PERMISSIONS = new String[]{
            Manifest.permission.CAMERA,
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CALL_PHONE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    int vibratetrigger =10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
        
        musicCard = findViewById(R.id.musicCardID);
        lightCard = findViewById(R.id.lightCardID);
        feedbackCard = findViewById(R.id.formCardID);
        alarmCard = findViewById(R.id.alarmCardID);
        healthCard = findViewById(R.id.healthCardID);
        chatbotCard = findViewById(R.id.chatbotCardID);

        askPermission();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Alarm");
        myRef.child("alarmstatus").setValue(1);

        String loguserAge = getIntent().getStringExtra("userage");
        String gurmobNum = getIntent().getStringExtra("gurtelNum");


        FirebaseDatabase databases = FirebaseDatabase.getInstance();
        DatabaseReference myRefe = databases.getReference("VibratorState");

        myRefe.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshots) {
                vibratetrigger = snapshots.child("VibratorStatus").getValue(int.class);
                System.out.println(vibratetrigger);

                if (vibratetrigger == 1)
                {

                        //TODO
//                    Intent serviceIntent = new Intent(MainMenu.this,sleepwalk.class);
//                    startService(serviceIntent);
                }
                else
                {

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainMenu.this, "Error DB", Toast.LENGTH_SHORT).show();

            }
        });

        /*if (vibratetrigger == 1){
            Toast.makeText(this, "Sleep Walk Alert Triggered", Toast.LENGTH_SHORT).show();
            startService(new Intent(getApplication(),sleepwalk.class));
        }*/

        musicCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO
//                Intent intent = new Intent(MainMenu.this, MusicTherapyF.class);
//                startActivity(intent);
            }
        });

        lightCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO
//                Intent intent = new Intent(MainMenu.this, LightTherapyF.class);
//                startActivity(intent);
            }
        });

        feedbackCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO
//                Intent intent = new Intent(MainMenu.this, FeedbackF.class);
//                startActivity(intent);
            }
        });
        alarmCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO
//                Intent intent = new Intent(MainMenu.this, automaticAlarmF.class);
//                intent.putExtra("userage1", loguserAge);
//                startActivity(intent);
            }
        });
        healthCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO
//                TODO
//                Intent intent = new Intent(MainMenu.this, HealthSystemF.class);
//                startActivity(intent);
            }
        });

        chatbotCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                TODO
//                Intent intent = new Intent(MainMenu.this, sleepapena.class);
//                startActivity(intent);
            }
        });

    }

    private void askPermission() {
        for (String permission : PERMISSIONS) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(PERMISSIONS, PERMISSION_ALL);
                return;
            }
        }
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {

        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Until you grant the permission, we cannot proceed further", Toast.LENGTH_SHORT).show();
            }
        }
    }
}