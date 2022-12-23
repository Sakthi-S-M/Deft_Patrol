package com.example.patrollingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;

    ArrayList<User> list;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    public MyAdapter(Context context, ArrayList<User> list) {
        this.context = context;
        this.list = list;

        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("patrol-users");

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        User user = list.get(position);
        holder.fullname.setText(user.getName());
        holder.username.setText(user.getUsername());
        holder.role.setText(user.getRole());

        holder.delete.setOnClickListener(view -> {

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

            // set title

            alertDialogBuilder.setTitle("Delete User");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Are you sure to delete this User...?")
                    .setCancelable(false)
                    .setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, close
                            // current activity
                            rootNode.getReference().child("patrol-users").child(user.username).setValue(null);
                        }
                    })
                    .setNegativeButton("No",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            // if this button is clicked, just close
                            // the dialog box and do nothing
                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();



            // show it
            alertDialog.show();

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fullname,username,role;
        ImageView delete;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fullname = itemView.findViewById(R.id.tvFullname);
            username = itemView.findViewById(R.id.tvUsername);
            role = itemView.findViewById(R.id.tvRole);

            delete = itemView.findViewById(R.id.delete_btn);

        }
    }

    public void setFilteredList(ArrayList<User> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }

}
