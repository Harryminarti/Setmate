package com.setmate.setmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.setmate.setmate.databinding.ActivityNotificationBinding;

import java.util.ArrayList;

public class notification extends AppCompatActivity {


    private ActivityNotificationBinding notificationBinding;

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;




    private ArrayList<user_chat_connection>arrayList;
    private notification_adapter adapter;


    @Override
    public void onBackPressed() {
        super.onBackPressed();


        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();


        notificationBinding = ActivityNotificationBinding.inflate(getLayoutInflater());


        setContentView(notificationBinding.getRoot());

        arrayList = new ArrayList<>();
        adapter = new notification_adapter(arrayList,this);

        notificationBinding.chatRecyclerView.setLayoutManager(new GridLayoutManager(this,2));
        notificationBinding.chatRecyclerView.setAdapter(adapter);


        notificationBinding.profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(notification.this,profile_page.class));
                finish();
            }
        });



        notificationBinding.homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        firebaseDatabase.getReference().child("user_chat_seen").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();

                for (DataSnapshot sn:snapshot.getChildren()) {

                    user_chat_connection userChatConnection = sn.getValue(user_chat_connection.class);

                    arrayList.add(userChatConnection);

                    notificationBinding.progressBar.setVisibility(View.GONE);


                }
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });











    }
}