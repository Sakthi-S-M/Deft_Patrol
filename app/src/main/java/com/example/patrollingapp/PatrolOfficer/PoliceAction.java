package com.example.patrollingapp.PatrolOfficer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.patrollingapp.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class PoliceAction extends AppCompatActivity {

    Button del;
    DatabaseReference mUsers, reference;
    TextInputLayout full_name;

    String title;

    FusedLocationProviderClient client;

    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    String Date, Name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police_action);

        del = findViewById(R.id.delete_btn);

        full_name = findViewById(R.id.name);

        title = getIntent().getStringExtra("title");

        client = LocationServices.getFusedLocationProviderClient(PoliceAction.this);


        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Date = simpleDateFormat.format(calendar.getTime());
        Name = full_name.getEditText().getText().toString();

        mUsers = FirebaseDatabase.getInstance().getReference("patrol-inform");
        reference = FirebaseDatabase.getInstance().getReference("patrol-update");

    }

    public void deleteGPS(View view) {

        if (!validateUsername()) {
            return;
        } else {

            mUsers.child(title).setValue(null);
            Toast.makeText(PoliceAction.this, "Deleted successfully", Toast.LENGTH_SHORT).show();
        }

    }

    private Boolean validateUsername() {
        String val = full_name.getEditText().getText().toString();
        if (val.isEmpty()) {
            full_name.setError("Field cannot be empty");
            return false;
        } else {
            full_name.setError(null);
            full_name.setErrorEnabled(false);
            return true;
        }
    }

    public void updateGPS(View view) {
        //Validate Login Info
        if (!validateUsername()) {
            return;
        } else {
            if (ActivityCompat.checkSelfPermission(PoliceAction.this,
                    Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                getGPS();
            } else {
                ActivityCompat.requestPermissions(PoliceAction.this
                        , new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
            }
        }
    }

    private void getGPS() {
        if (ActivityCompat.checkSelfPermission(PoliceAction.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(PoliceAction.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(PoliceAction.this,
                                Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1);

                        reference.child(title).child("latitude").setValue(addresses.get(0).getLatitude());
                        reference.child(title).child("longitude").setValue(addresses.get(0).getLongitude());

                        Name = full_name.getEditText().getText().toString();

                        reference.child(title).child("date").setValue(Date);
                        reference.child(title).child("name").setValue(Name);
                        Toast.makeText(PoliceAction.this, "Updated successfully", Toast.LENGTH_SHORT).show();


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        });

    }

}