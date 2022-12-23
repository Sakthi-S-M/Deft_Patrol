package com.example.patrollingapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.patrollingapp.PatrolOfficer.RowdyAddingClass;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddAcquistsFragment extends Fragment {

    View view;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    TextInputLayout Name, HS, lati, longi, cases;
    String name, hs, Cases;
    double Lati, Longi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_acquists, container, false);

        Button signup = (Button) view.findViewById(R.id.reg_btn);

        TextInputLayout Name = (TextInputLayout) view.findViewById(R.id.reg_a_name);
        TextInputLayout HS = (TextInputLayout) view.findViewById(R.id.reg_a_hs_no);
        TextInputLayout lati = (TextInputLayout) view.findViewById(R.id.reg_latitude);
        TextInputLayout longi = (TextInputLayout) view.findViewById(R.id.reg_longitude);
        TextInputLayout cases = (TextInputLayout) view.findViewById(R.id.reg_case);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String val1 = Name.getEditText().getText().toString();
                String val2 = HS.getEditText().getText().toString();
                String val3 = lati.getEditText().getText().toString();
                String val4 = longi.getEditText().getText().toString();
                String val5 = cases.getEditText().getText().toString();

                if (val1.isEmpty()) {
                    Name.setError("Field cannot be empty");
                } else if (val2.isEmpty()) {
                    Name.setError(null);
                    Name.setErrorEnabled(false);
                    HS.setError("Field cannot be empty");
                } else if (val3.isEmpty()) {
                    Name.setError(null);
                    Name.setErrorEnabled(false);
                    HS.setError(null);
                    HS.setErrorEnabled(false);
                    lati.setError("Field cannot be empty");
                } else if (val4.isEmpty()) {
                    Name.setError(null);
                    Name.setErrorEnabled(false);
                    HS.setError(null);
                    HS.setErrorEnabled(false);
                    lati.setError(null);
                    lati.setErrorEnabled(false);
                    longi.setError("Field cannot be empty");
                } else {

                    Name.setError(null);
                    Name.setErrorEnabled(false);
                    HS.setError(null);
                    HS.setErrorEnabled(false);
                    lati.setError(null);
                    lati.setErrorEnabled(false);
                    longi.setError(null);
                    longi.setErrorEnabled(false);

                    name = Name.getEditText().getText().toString();
                    hs = HS.getEditText().getText().toString();
                    double Lati = Double.parseDouble(lati.getEditText().getText().toString());
                    double Longi  = Double.parseDouble(longi.getEditText().getText().toString());
                    Cases =cases.getEditText().getText().toString();


                    rootNode = FirebaseDatabase.getInstance();
                    reference = rootNode.getReference("patrol-acquists");
                    RowdyAddingClass helperClass = new RowdyAddingClass(name, hs, Lati, Longi, Cases);
                    reference.child(name).setValue(helperClass);

                    Toast.makeText(getActivity(), "Rowdy added successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });


        // Inflate the layout for this fragment
        return view;
    }


}