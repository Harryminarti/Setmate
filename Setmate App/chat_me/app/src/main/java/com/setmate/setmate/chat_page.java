package com.setmate.setmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.setmate.setmate.databinding.ActivityChatPageBinding;

import java.util.ArrayList;

public class chat_page extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private ActivityChatPageBinding chatPageBinding;

    private ArrayList<user_chat_connection>arrayList;
    private user_chat_shown_adapter adapter;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        chatPageBinding = ActivityChatPageBinding.inflate(getLayoutInflater());

        setContentView(chatPageBinding.getRoot());


//        ----------------------------------------------------------------------------------------






//        ------------------------------------------------------------------------------------------

        arrayList = new ArrayList<>();



        MainActivity mainActivity = MainActivity.instance;
        FloatingActionButton element = mainActivity.getElement();





        adapter = new user_chat_shown_adapter(arrayList,this,element);
        chatPageBinding.chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatPageBinding.chatRecyclerView.setAdapter(adapter);

        user_chat_seen();






    }



    private void user_chat_seen(){


        firebaseDatabase.getReference().child("user_chat_seen").child(firebaseAuth.getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();

                for (DataSnapshot shot:snapshot.getChildren()  ) {

                    user_chat_connection editPageClass1 = shot.getValue(user_chat_connection.class);
                    arrayList.add(editPageClass1);


                    chatPageBinding.progressBar.setVisibility(View.GONE);
                }
                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(chat_page.this, "something went wrong please, try again..", Toast.LENGTH_SHORT).show();

            }
        });


    }



}