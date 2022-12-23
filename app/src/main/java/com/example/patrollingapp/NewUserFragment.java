package com.example.patrollingapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class NewUserFragment extends Fragment {

    View view;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    TextInputLayout Name, Username, PhoneNo, Password;
    String name, username, phoneNo, password, role;

    Button upload,select;

    String fileName;

    Uri imageUri;
    View firebaseImg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_new_user, container, false);

        Button signup = (Button) view.findViewById(R.id.reg_btn);

        TextInputLayout Name = (TextInputLayout) view.findViewById(R.id.reg_name);
        TextInputLayout Username = (TextInputLayout) view.findViewById(R.id.reg_username);
        TextInputLayout PhoneNo = (TextInputLayout) view.findViewById(R.id.reg_phoneNo);
        TextInputLayout Password = (TextInputLayout) view.findViewById(R.id.reg_password);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String noWhiteSpace = "\\A\\w{4,20}\\z";
                String val1 = Name.getEditText().getText().toString();
                String val2 = Username.getEditText().getText().toString();
                String val3 = PhoneNo.getEditText().getText().toString();
                String val4 = Password.getEditText().getText().toString();
                String passwordVal = "^" +
                        //"(?=.*[0-9])" +         //at least 1 digit
                        //"(?=.*[a-z])" +         //at least 1 lower case letter
                        //"(?=.*[A-Z])" +         //at least 1 upper case letter
                        "(?=.*[a-zA-Z])" +      //any letter
                        "(?=.*[@#$%^&+=])" +    //at least 1 special character
                        "(?=\\S+$)" +           //no white spaces
                        ".{4,}" +               //at least 4 characters
                        "$";


                if (val1.isEmpty()) {
                    Name.setError("Field cannot be empty");
                } else if (val2.isEmpty()) {
                    Name.setError(null);
                    Name.setErrorEnabled(false);
                    Username.setError("Field cannot be empty");
                } else if (val2.length() >= 15) {
                    Username.setError("Username too long");
                } else if (!val2.matches(noWhiteSpace)) {
                    Username.setError("White Spaces are not allowed");
                } else if (val3.isEmpty()) {
                    Name.setError(null);
                    Name.setErrorEnabled(false);
                    Username.setError(null);
                    Username.setErrorEnabled(false);
                    PhoneNo.setError("Field cannot be empty");
                } else if (val4.isEmpty()) {
                    Name.setError(null);
                    Name.setErrorEnabled(false);
                    Username.setError(null);
                    Username.setErrorEnabled(false);
                    PhoneNo.setError(null);
                    PhoneNo.setErrorEnabled(false);
                    Password.setError("Field cannot be empty");
                } else if (!val4.matches(passwordVal)) {
                    Password.setError("Password is too weak");
                } else {

                    Name.setError(null);
                    Name.setErrorEnabled(false);
                    Username.setError(null);
                    Username.setErrorEnabled(false);
                    PhoneNo.setError(null);
                    PhoneNo.setErrorEnabled(false);
                    Password.setError(null);
                    Password.setErrorEnabled(false);

                    name = Name.getEditText().getText().toString();
                    username = Username.getEditText().getText().toString();
                    phoneNo = PhoneNo.getEditText().getText().toString();
                    password = Password.getEditText().getText().toString();
                    role = "patrol-officer";

                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("patrol-users");
                    UserHelperClass helperClass = new UserHelperClass(name, username, phoneNo, password, role);
                    reference.child(username).setValue(helperClass);

                    Toast.makeText(getActivity(), "User registered successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    private Boolean validateName() {
        String val = Name.getEditText().getText().toString();

        if (val.isEmpty()) {
            Name.setError("Field cannot be empty");
            return false;
        } else {
            Name.setError(null);
            Name.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validateUsername() {
        String val = Username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty()) {
            Username.setError("Field cannot be empty");
            return false;
        } else if (val.length() >= 15) {
            Username.setError("Username too long");
            return false;
        } else if (!val.matches(noWhiteSpace)) {
            Username.setError("White Spaces are not allowed");
            return false;
        } else {
            Username.setError(null);
            Username.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePhoneNo() {
        String val = PhoneNo.getEditText().getText().toString();

        if (val.isEmpty()) {
            PhoneNo.setError("Field cannot be empty");
            return false;
        } else {
            PhoneNo.setError(null);
            PhoneNo.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = Password.getEditText().getText().toString();
        String passwordVal = "^" +
                //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[a-zA-Z])" +      //any letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                "(?=\\S+$)" +           //no white spaces
                ".{4,}" +               //at least 4 characters
                "$";

        if (val.isEmpty()) {
            Password.setError("Field cannot be empty");
            return false;
        } else if (!val.matches(passwordVal)) {
            Password.setError("Password is too weak");
            return false;
        } else {
            Password.setError(null);
            Password.setErrorEnabled(false);
            return true;
        }
    }

    private void registerUser(View view) {

        if (!validateName() | !validatePassword() | !validatePhoneNo() | !validateUsername()) {
            return;
        }

        name = Name.getEditText().getText().toString();
        username = Username.getEditText().getText().toString();
        phoneNo = PhoneNo.getEditText().getText().toString();
        password = Password.getEditText().getText().toString();
        role = "patrol-officer";

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("patrol-users");
        UserHelperClass helperClass = new UserHelperClass(name, username, phoneNo, password, role);
        reference.child(username).setValue(helperClass);
        Toast.makeText(getActivity(), "User registered successfully", Toast.LENGTH_SHORT).show();

    }

}