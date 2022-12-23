package com.example.patrollingapp.PatrolOfficer;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.patrollingapp.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.annotations.Nullable;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewImageFragment extends Fragment {

    Button select,upload;
    CircleImageView firebaseImg;

    TextInputLayout Name;

    String fileName;

    Uri imageUri;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_new_image, container, false);

        select = (Button) view.findViewById(R.id.selectFile);
        upload = (Button) view.findViewById(R.id.uploadFile);
        firebaseImg = (CircleImageView) view.findViewById(R.id.firebaseimage);

        Name = (TextInputLayout) view.findViewById(R.id.name);

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });

        return view;
    }

    private void uploadImage() {

        String val1 = Name.getEditText().getText().toString();

        if (val1.isEmpty()) {
            Name.setError("Field cannot be empty");
        } else {

            Name.setError(null);
            Name.setErrorEnabled(false);

            ProgressDialog progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Uploading File....");
            progressDialog.show();


            //SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.CANADA);
            //Date now = new Date();
            fileName = Name.getEditText().getText().toString().toLowerCase();
            StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/" + fileName);


            storageReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Picasso.with(getActivity()).load(imageUri).into((CircleImageView) firebaseImg);
                            Toast.makeText(getActivity(),"Successfully Uploaded",Toast.LENGTH_SHORT).show();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Failed to Upload",Toast.LENGTH_SHORT).show();


                }
            });

        }

    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && data != null && data.getData() != null) {

            imageUri = data.getData();
            Picasso.with(getActivity()).load(imageUri).into((CircleImageView) firebaseImg);

        }
    }


}