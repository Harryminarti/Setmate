package com.setmate.setmate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.setmate.setmate.databinding.ActivityChatDataBinding;

import java.util.ArrayList;

public class chat_data extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseStorage firebaseStorage;
    private FirebaseAuth firebaseAuth;

    private ActivityChatDataBinding chatDataBinding;

    private ArrayList<chat_data_class> arrayList;
    private chat_item_adapter adapter;



    private String receiver_room;
    private String senderid;
    private String sender_room;
    private String receiverid;



//--------------------------------------------------------------------------------------------------------->>>>>>>>>>>>>>>>>

    // when we press button in our app


    @Override
    public void onBackPressed() {
        MainActivity.unseen=0;
        finish();
        super.onBackPressed();
    }





//    -------------------------------------------------------------------------------------------------------->>>>
    // Main function started here


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        chatDataBinding = ActivityChatDataBinding.inflate(getLayoutInflater());
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        setContentView(chatDataBinding.getRoot());




//        ------------------------------------------------------------------------------------------->>>>>>>>>>>>>>>>>>>>>
//        adapter is set here..................................




        arrayList = new ArrayList<>();
        adapter = new chat_item_adapter(arrayList, this);
//
            LinearLayoutManager  linearLayoutManager = new LinearLayoutManager(this);
            linearLayoutManager.setStackFromEnd(true);


        chatDataBinding.chatRecyclerView.setLayoutManager(linearLayoutManager);






        chatDataBinding.chatRecyclerView.setAdapter(adapter);
//------------------------------------------------------------------------------------------------------>>>>>>>>>>>>>>
//get data from holder and set data




        senderid = firebaseAuth.getCurrentUser().getUid();

        receiverid = getIntent().getStringExtra("receiver");

        sender_room = senderid + receiverid;
        receiver_room = receiverid + senderid;




        firebaseDatabase.getReference().child("user").child(receiverid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.child("imageLink").getValue()!=null) {
                    Glide.with(chat_data.this).load(snapshot.child("imageLink").getValue().toString()).into(chatDataBinding.receiverIcon);
                }
                chatDataBinding.receiverName.setText(snapshot.child("name").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

//------------------------------------------------------------------------------------------------------------------------>>>>>>>>>>>>>>>>>>.
//Seen and unseen features code going
// here also chat massage loaded here.............





        firebaseDatabase.getReference().child("chat").child(sender_room).child("massage").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                arrayList.clear();
                for (DataSnapshot data : snapshot.getChildren()) {

             chat_data_class chatDataClass = data.getValue(chat_data_class.class);

             if (MainActivity.unseen==1){
                    if (chatDataClass!=null){
                        if (!chatDataClass.getType()){
                            chatDataClass.setType(true);
                            data.child("type").getRef().setValue(chatDataClass.getType());
                        }

                    }

             }
            arrayList.add(chatDataClass);}

                chatDataBinding.chatRecyclerView.scrollToPosition(arrayList.size() - 1);

                adapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(chat_data.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });






//        ------------------------------------------------------------------------------->>>>>>>>>>>>>>>>>>>>
//        when we click on massage button................
//        massage send code going here...............





        chatDataBinding.sendMassageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String massage = chatDataBinding.massageText.getText().toString();

                if (massage.isEmpty()){
                    return;
                }

                chat_data_class chatDataClass = new chat_data_class(massage, senderid,false);


                firebaseDatabase.getReference().child("chat").child(sender_room).child("massage").push().setValue(chatDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        if (task.isSuccessful()) {
                            firebaseDatabase.getReference().child("chat").child(receiver_room).child("massage").push().setValue(chatDataClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {



                                    chatDataBinding.massageText.setText("");




                                }
                            });
                        }
                    }
                });
            }
        });




    }


}