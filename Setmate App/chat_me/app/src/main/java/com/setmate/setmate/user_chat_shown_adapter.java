package com.setmate.setmate;


import static com.setmate.setmate.MainActivity.unseen;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.setmate.setmate.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class user_chat_shown_adapter extends RecyclerView.Adapter<user_chat_shown_adapter.viewholder> {
    private String sender_room;



    ArrayList<user_chat_connection>arrayList;
    Context context;
    String TAG = "context";




      FloatingActionButton floatingActionButtons;



    int i=0;



//


    public user_chat_shown_adapter(ArrayList<user_chat_connection> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    public user_chat_shown_adapter(ArrayList<user_chat_connection> arrayList, Context context, FloatingActionButton floatingActionButton) {
        this.arrayList = arrayList;
        this.context = context;
        this.floatingActionButtons = floatingActionButton;

    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_user_shown,parent,false);

        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {


        user_chat_connection editPageClass = arrayList.get(position);





        FirebaseDatabase.getInstance().getReference().child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                if (snapshot.child(editPageClass.getReceiver_id()).exists()){

                    for (DataSnapshot da:  snapshot.getChildren() ) {

                        edit_pageClass edit_pageClass = da.getValue(edit_pageClass.class);


                        if (Objects.equals(edit_pageClass.getUser_id(), editPageClass.getReceiver_id())) {


                            if (!editPageClass.getType()) {

                                FirebaseDatabase.getInstance().getReference().child("user_chat_seen").child(editPageClass.getReceiver_id()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot data : snapshot.getChildren()) {

                                            user_chat_connection userChatConnection1 = data.getValue(user_chat_connection.class);
                                            if (Objects.equals(userChatConnection1.getReceiver_id(), FirebaseAuth.getInstance().getUid())) {


                                                userChatConnection1.setType(true);

                                                data.getRef().setValue(userChatConnection1);

                                                FirebaseDatabase.getInstance().getReference().child("user_chat_seen").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        for (DataSnapshot datas: snapshot.getChildren()) {

                                                            user_chat_connection userChatConnection2 = datas.getValue(user_chat_connection.class);
                                                            if (Objects.equals(userChatConnection2.getReceiver_id(), editPageClass.getReceiver_id())) {


                                                                userChatConnection2.setType(true);

                                                                datas.getRef().setValue(userChatConnection2);}

                                                        }

                                                        }



                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });



                                            }

                                        }


                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
                            }
                            if (editPageClass.getType()){

                                holder.name.setText(edit_pageClass.getName().substring(0, 1).toUpperCase() + edit_pageClass.getName().substring(1));

                                if (edit_pageClass.getImageLink()!=null) {
                                    Glide.with(context).load(edit_pageClass.getImageLink()).circleCrop().into(holder.user_chat_image);
                                }
                                holder.name.setVisibility(View.VISIBLE);
                                holder.user_chat_image.setVisibility(View.VISIBLE);
                                holder.about_user.setVisibility(View.VISIBLE);


                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                                sender_room = FirebaseAuth.getInstance().getUid() + edit_pageClass.getUser_id();


                                firebaseDatabase.getReference().child("chat").child(sender_room).child("massage").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot datasnap : snapshot.getChildren()) {

                                            chat_data_class dataClass = datasnap.getValue(chat_data_class.class);



                                            if (!dataClass.getType()) {

                                                holder.massage_view.setVisibility(View.VISIBLE);
                                                floatingActionButtons.setVisibility(View.VISIBLE);




                                            }else {



                                                holder.massage_view.setVisibility(View.INVISIBLE);
                                                floatingActionButtons.setVisibility(View.INVISIBLE);


                                            }






                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });


                                holder.layout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        Intent intent = new Intent(context, chat_data.class);
                                        intent.putExtra("name", edit_pageClass.getName());
                                        intent.putExtra("image", edit_pageClass.getImageLink());
                                        intent.putExtra("receiver", edit_pageClass.getUser_id());
                                        unseen = 1;
                                        context.startActivity(intent);



                                    }
                                });
                            }
                        }

                    }

                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {


            }
        });






    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView name,about_user;
        FloatingActionButton massage_view;
        ImageView user_chat_image;
        LinearLayout layout;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.user_chat_name);
            layout = itemView.findViewById(R.id.name_chat_layout);

            massage_view = itemView.findViewById(R.id.massage_down);

            user_chat_image = itemView.findViewById(R.id.user_chat_profile_images);
            about_user = itemView.findViewById(R.id.about_users);




        }
    }






}
