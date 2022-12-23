package com.example.patrollingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.example.patrollingapp.PatrolOfficer.Dashboard;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {

    Button callSignUp, login_btn;
    TextInputLayout username, password;
    CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login_btn = findViewById(R.id.lgn_btn);
        username = findViewById(R.id.user_name);
        password = findViewById(R.id.pass_word);
        rememberMe = findViewById(R.id.remember_me);

        SharedPreferences preferences = getSharedPreferences("checkbox",MODE_PRIVATE);
        String checkbox = preferences.getString("remember","");
        if(checkbox.equals("true")){

            Intent intent = new Intent(Login.this, Dashboard.class);
            startActivity(intent);
            finish();

        }else if(checkbox.equals("false")){

        }

        rememberMe.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if (compoundButton.isChecked()){

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","true");
                    editor.apply();

                } else if (!compoundButton.isChecked()){

                    SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("remember","false");
                    editor.apply();

                }

            }
        });

    }

    private Boolean validateUsername() {
        String val = username.getEditText().getText().toString();
        if (val.isEmpty()) {
            username.setError("Field cannot be empty");
            return false;
        } else {
            username.setError(null);
            username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = password.getEditText().getText().toString();
        if (val.isEmpty()) {
            password.setError("Field cannot be empty");
            return false;
        } else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }

    // Call for Login functionality
    public void loginUser(View view) {
        //Validate Login Info
        if (!validateUsername() | !validatePassword()) {
            return;
        } else {
            isUser();
        }
    }

    private void isUser() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        reference.child("patrol-users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                final String userEnteredUsername = username.getEditText().getText().toString().trim();
                final String userEnteredPassword = password.getEditText().getText().toString().trim();

                if (snapshot.child(userEnteredUsername).exists()){

                    if(snapshot.child(userEnteredUsername).child("password").getValue(String.class).equals(userEnteredPassword)){

                        if (snapshot.child(userEnteredUsername).child("role").getValue(String.class).equals("patrol-officer")) {
                            username.setError(null);
                            username.setErrorEnabled(false);
                            String nameFromDB = snapshot.child(userEnteredUsername).child("name").getValue(String.class);
                            String usernameFromDB = snapshot.child(userEnteredUsername).child("username").getValue(String.class);
                            String phoneNoFromDB = snapshot.child(userEnteredUsername).child("phoneNo").getValue(String.class);
                            String emailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);
                            String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);
                            //Double latFromDB = snapshot.child(userEnteredUsername).child("latitude").getValue(Double.class);
                            //Double longFromDB = snapshot.child(userEnteredUsername).child("longitude").getValue(Double.class);
                            //String latfromdb = latFromDB.toString();
                            //String longfromdb = longFromDB.toString();
                            Intent intent = new Intent(getApplicationContext(), Dashboard.class);
                            intent.putExtra("name", nameFromDB);
                            //intent.putExtra("latitude", latfromdb);
                            //intent.putExtra("longitude", longfromdb);
                            intent.putExtra("email", emailFromDB);
                            intent.putExtra("username", usernameFromDB);
                            intent.putExtra("phoneNo", phoneNoFromDB);
                            intent.putExtra("password", passwordFromDB);
                            startActivity(intent);
                            finish();
                            Toast.makeText(Login.this,"Patrol-Officer Logged-in successfully",Toast.LENGTH_SHORT).show();

                        } else if (snapshot.child(userEnteredUsername).child("role").getValue(String.class).equals("admin")) {
                            Intent intent = new Intent(Login.this, AdminDashboard.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(Login.this,"Admin Logged-in successfully",Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        password.setError("Wrong Password");
                        password.requestFocus();
                    }

                } else {
                    username.setError("No such User exist");
                    username.requestFocus();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}