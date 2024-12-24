package com.setmate.setmate;



import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.setmate.setmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.setmate.setmate.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity   {

    public static int pos=0;

    public static MainActivity instance;





    private  String  TAG ="MainActivity";


    String l;
    private static int ee;
    public  static int unseen =0;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase database;
    private ActivityMainBinding mainBinding;


    private ArrayList<edit_pageClass>arrayList;
    private home_page_adapter adapter;



    int value;


//    ------------------------------------------------------------------------------------------------------>>>>>>>>




    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    public FloatingActionButton getElement() {
        FloatingActionButton floatingActionButton;
         floatingActionButton= findViewById(R.id.seen_red_dots);
         return floatingActionButton;
    }


//---------------------------------------------------------------------------->>>>>>>>>>>>>>>>>>>



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        database = FirebaseDatabase.getInstance();
        mainBinding= ActivityMainBinding.inflate(getLayoutInflater());
        firebaseAuth= FirebaseAuth.getInstance();
        setContentView(mainBinding.getRoot());


        instance = this;


//  ----------------------------------------------------------------------------------------------------->>>>>>>>>>>>>>>>>>>>

        current_status();




//        ------------------------------------------------------------------------------------------------------>>>>>>>>>>>>>


        arrayList =  new ArrayList<>();
        adapter= new home_page_adapter(arrayList,this);

        mainBinding.mainRecyclerView.setOrientation(ViewPager2.ORIENTATION_VERTICAL);



        mainBinding.mainRecyclerView.setAdapter(adapter);




        mainBinding.mainRecyclerView.beginFakeDrag();
        mainBinding.mainRecyclerView.fakeDragBy(-70f);
        mainBinding.mainRecyclerView.endFakeDrag();
//--------------------------------------------------------------------------------------------->>>>>>>>>>>>>>>


        mainBinding.profileIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,profile_page.class));
            }
        });


        mainBinding.likesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,notification.class));
            }
        });

//        ........................................................................................................................




        mainBinding.chatIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(MainActivity.this,chat_page.class));
            }
        });

    }




//-------------------------------------------------------------------------------------->>>>>>>>>>>>>
//checking user status



    private void current_status(){
        if (firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(MainActivity.this,phone_authentication_page.class));
            finish();
        }else {

        check();

        }

    }






    private void check(){
        database.getReference().child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!(snapshot.child(firebaseAuth.getUid()).exists())){

                    database.getReference().child("male_user").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            if (!(snapshot.child(firebaseAuth.getUid()).exists())){
                                startActivity(new Intent(MainActivity.this,Edit_page.class));

                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });



                }else{



                    data_adds();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();

            }
        });
    }








    private void user_shown(){





        database.getReference().child("current_user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                if (!(snapshot.child(firebaseAuth.getUid()).exists())) {


                    database.getReference().child("current_user").child(firebaseAuth.getUid()).setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {


                            if (task.isSuccessful()){
                                user_shown();


                            }

                        }
                    });

                }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



//--------------------------------------------------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


        database.getReference().child("current_user").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshots) {

//----------------------------------------------------------------------------------------->>>>>>>>>>>>>>>>>>>>>>>>>>>>.



                database.getReference().child("user").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.child("gender").getValue().toString().equals("male")) {




                          Query pagination= database.getReference().child("user").orderByChild("user_no").startAt(Integer.parseInt(snapshots.getValue().toString()));
                            pagination.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {




                                    arrayList.clear();

                                    for (DataSnapshot data : snapshot.getChildren()) {




                                        if (!data.child("gender").getValue().toString().equals("male")) {
                                            edit_pageClass pageClass = data.getValue(edit_pageClass.class);

                                            mainBinding.progressBar.setVisibility(View.GONE);


                                            Log.d(TAG, "onDataChange: "+arrayList.size());



                                            arrayList.add(pageClass);





                                        }
//


                                    }




                                    adapter.notifyDataSetChanged();



                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

                                }


                            });




                        }else {

                            int ee=        Integer.parseInt(snapshots.getValue().toString());
                            database.getReference().child("user").orderByChild("user_no").startAt(Integer.parseInt(snapshots.getValue().toString())).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {


                                    arrayList.clear();
                                    for (DataSnapshot data : snapshot.getChildren()) {

                                        if (data.child("gender").getValue().toString().equals("male")) {
                                            edit_pageClass pageClass = data.getValue(edit_pageClass.class);
                                            mainBinding.progressBar.setVisibility(View.GONE);


                                            arrayList.add(pageClass);

                                        }
//


                                    }


                                    adapter.notifyDataSetChanged();


                                }


                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                    Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

                                }


                            });



                        }




                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });




            }



            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(getApplicationContext(), "Something Went Wrong", Toast.LENGTH_SHORT).show();

            }
        });
















                }





    private  void data_adds(){

        mainBinding.progressBar.setVisibility(View.VISIBLE);


        database.getReference().child("current_user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {



                    if (!(snapshot.child(firebaseAuth.getUid()).exists())) {


                        database.getReference().child("current_user").child(firebaseAuth.getUid()).setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {


                                if (task.isSuccessful()){
                                    user_shown();


                                }

                            }
                        });

                    }


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        user_shown();





    }






            }
















