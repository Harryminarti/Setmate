package com.setmate.setmate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.setmate.setmate.databinding.ActivityProfilePageBinding;

import java.util.Calendar;

public class profile_page extends AppCompatActivity {

    private ActivityProfilePageBinding profilePageBinding;
    private FirebaseAuth firebaseAuth;
    private FirebaseStorage firebaseStorage;
    private String name;

   private static int checks=0 ;

    private Uri img1Uri;
    private Uri img1Uri2;
    private Uri img1Uri3;
    private Uri img1Uri4;


//    ----------------------------------------------------------------------


    private  final int Requestcode = 21;
    private final int Requestcode2 = 22;
    private final int Requestcode3= 23;
    private final int Requestcode4 = 24;

     static final String first = "1";
     static final String second = "2";

     static final String third = "3";
     static final String four = "4";


//    --------------------------------------------------------------------



    private FirebaseDatabase database;
    private  edit_pageClass edit_pageClass1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        database = FirebaseDatabase.getInstance();
        profilePageBinding = ActivityProfilePageBinding.inflate(getLayoutInflater());
        setContentView(profilePageBinding.getRoot());


        if (firebaseAuth.getCurrentUser()==null){
            startActivity(new Intent(profile_page.this,phone_authentication_page.class));

      finish();
        }

        find_photos_users();
        profile_show();




        profilePageBinding.logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(profile_page.this,phone_authentication_page.class));
                finish();
            }
        });

        profilePageBinding.homeIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();

            }
        });


        profilePageBinding.likesIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(profile_page.this,notification.class));

                finish();

            }
        });




        profilePageBinding.editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                startActivity(new Intent(profile_page.this,Edit_page.class));


            }
        });




        profilePageBinding.editbuttonTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                checks=1;

                profilePageBinding.imageUploads.setVisibility(View.VISIBLE);


            }
        });







        //----------------------------------------Buttons code going here----------------------------------------------------------------------------

//----------------------------------------Buttons1 click code going here----------------------------------------------------------------------------



        profilePageBinding.photoOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checks==1) {

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, Requestcode);
                }else {

                    Toast.makeText(profile_page.this, "Please Click On Edit Button2", Toast.LENGTH_SHORT).show();
                }
            }
        });




////----------------------------------------Buttons2 click code going here----------------------------------------------------------------------------



        profilePageBinding.photoTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checks==1) {

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, Requestcode2);
                }else {

                    Toast.makeText(profile_page.this, "Please Click On Edit Button2", Toast.LENGTH_SHORT).show();
                }
            }
        });


//        //----------------------------------------Buttons3 click code going here----------------------------------------------------------------------------


        profilePageBinding.photoThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checks==1) {

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, Requestcode3);
                }else {

                    Toast.makeText(profile_page.this, "Please Click On Edit Button2", Toast.LENGTH_SHORT).show();
                }
            }
        });



        //----------------------------------------Buttons4 click code going here----------------------------------------------------------------------------


        profilePageBinding.photoFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (checks==1) {

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, Requestcode4);
                }else {

                    Toast.makeText(profile_page.this, "Please Click On Edit Button2", Toast.LENGTH_SHORT).show();
                }
            }
        });







        String time = Calendar.getInstance().getTime().toString();
        String unique_id = firebaseAuth.getUid()+time;



        DatabaseReference databaseReference = database.getReference().child("extra_image").child(firebaseAuth.getUid());



        profilePageBinding.imageUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                //                ------------------------------------------------One images---------------------------------------
                profilePageBinding.progressBar3.setVisibility(View.VISIBLE);

                if (img1Uri != null) {

                    profilePageBinding.progressBar3.setVisibility(View.VISIBLE);
                    StorageReference storageReference = firebaseStorage.getReference().child("extra_image").child(unique_id + "1");
                    storageReference.putFile(img1Uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {


                                        String url = uri.toString();


                                        databaseReference.child(first).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    profilePageBinding.progressBar3.setVisibility(View.GONE);
                                                    Toast.makeText(profile_page.this, "Image one is uploaded successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(profile_page.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });


                                    }
                                });
                            } else {
                                Toast.makeText(profile_page.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }


                //                ------------------------------------------------Two images---------------------------------------

                profilePageBinding.progressBar3.setVisibility(View.VISIBLE);
                if (img1Uri2 != null) {

                    profilePageBinding.progressBar3.setVisibility(View.VISIBLE);
                    StorageReference storageReference = firebaseStorage.getReference().child("extra_image").child(unique_id + "2");
                    storageReference.putFile(img1Uri2).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {


                                        String url = uri.toString();


                                        databaseReference.child(second).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    profilePageBinding.progressBar3.setVisibility(View.GONE);
                                                    Toast.makeText(profile_page.this, "Image Two is uploaded successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(profile_page.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });


                                    }
                                });
                            } else {
                                Toast.makeText(profile_page.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }


                //                ------------------------------------------------Third images---------------------------------------
                profilePageBinding.progressBar3.setVisibility(View.VISIBLE);

                if (img1Uri3 != null) {
                    profilePageBinding.progressBar3.setVisibility(View.VISIBLE);
                    StorageReference storageReference = firebaseStorage.getReference().child("extra_image").child(unique_id + "3");
                    storageReference.putFile(img1Uri3).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {


                                        String url = uri.toString();


                                        databaseReference.child(third).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {

                                                    profilePageBinding.progressBar3.setVisibility(View.GONE);

                                                    Toast.makeText(profile_page.this, "Image Three is uploaded successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(profile_page.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });


                                    }
                                });
                            } else {
                                Toast.makeText(profile_page.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }


//                ------------------------------------------------fourth images---------------------------------------

                profilePageBinding.progressBar3.setVisibility(View.VISIBLE);
                if (img1Uri4 != null) {
                    profilePageBinding.progressBar3.setVisibility(View.VISIBLE);
                    StorageReference storageReference = firebaseStorage.getReference().child("extra_image").child(unique_id + "4");
                    storageReference.putFile(img1Uri4).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {
                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {


                                        String url = uri.toString();


                                        databaseReference.child(four).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {
                                                    profilePageBinding.progressBar3.setVisibility(View.GONE);
                                                    Toast.makeText(profile_page.this, "Image Four is uploaded successfully", Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(profile_page.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                                }

                                            }
                                        });


                                    }
                                });
                            } else {
                                Toast.makeText(profile_page.this, "something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });


                }


                checks=0;
                checks=0;
                profilePageBinding.imageUploads.setVisibility(View.GONE);





            }



        });

        profilePageBinding.progressBar3.setVisibility(View.GONE);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode==Requestcode && resultCode==RESULT_OK && data!=null) {

            Uri uri = data.getData();

            img1Uri = uri;

            Glide.with(this).load(img1Uri).circleCrop().into(profilePageBinding.photoOne);



        }
        if (requestCode==Requestcode2 && resultCode==RESULT_OK && data!=null) {

            Uri uri = data.getData();

            img1Uri2 = uri;

            Glide.with(this).load(img1Uri2).circleCrop().into(profilePageBinding.photoTwo);



        }

        if (requestCode==Requestcode3 && resultCode==RESULT_OK && data!=null) {

            Uri uri = data.getData();

            img1Uri3 = uri;

            Glide.with(this).load(img1Uri3).circleCrop().into(profilePageBinding.photoThree);



        }

        if (requestCode==Requestcode4 && resultCode==RESULT_OK && data!=null) {

            Uri uri = data.getData();

            img1Uri4 = uri;

            Glide.with(this).load(img1Uri4).circleCrop().into(profilePageBinding.photoFour);



        }






    }

    private void profile_show(){


        DatabaseReference databaseReference = database.getReference().child("user").child(firebaseAuth.getCurrentUser().getUid());
        StorageReference storageReference = firebaseStorage.getReference().child("images").child(firebaseAuth.getCurrentUser().getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    profilePageBinding.profileNameField.setText(snapshot.child("name").getValue().toString());

                if (snapshot.child("imageLink").exists()) {
                    Glide.with(getApplicationContext()).load(snapshot.child("imageLink").getValue().toString()).circleCrop().into(profilePageBinding.profileImages);
                }

                profilePageBinding.progressBar.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(profile_page.this, "Due to some problem your profile is not loaded", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void find_photos_users(){

        database.getReference().child("extra_image").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(firebaseAuth.getUid()).exists()){


                    get_photos();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void get_photos(){
        profilePageBinding.progressBar2.setVisibility(View.VISIBLE);

        database.getReference().child("extra_image").child(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                profilePageBinding.progressBar2.setVisibility(View.VISIBLE);
                if (snapshot.child(first).exists()) {
                    profilePageBinding.progressBar3.setVisibility(View.VISIBLE);

                    Glide.with(profile_page.this).load(snapshot.child(first).getValue().toString()).circleCrop().into(profilePageBinding.photoOne);

                    profilePageBinding.progressBar3.setVisibility(View.GONE);
                }
                if (snapshot.child(second).exists()) {
                    profilePageBinding.progressBar3.setVisibility(View.VISIBLE);
                    Glide.with(profile_page.this).load(snapshot.child(second).getValue().toString()).circleCrop().into(profilePageBinding.photoTwo);

                    profilePageBinding.progressBar3.setVisibility(View.GONE);
                }
                if (snapshot.child(third).exists()) {
                    profilePageBinding.progressBar3.setVisibility(View.VISIBLE);
                    Glide.with(profile_page.this).load(snapshot.child(third).getValue().toString()).circleCrop().into(profilePageBinding.photoThree);
                    profilePageBinding.progressBar3.setVisibility(View.GONE);
                }
                if (snapshot.child(four).exists()) {
                    profilePageBinding.progressBar3.setVisibility(View.VISIBLE);
                    Glide.with(profile_page.this).load(snapshot.child(four).getValue().toString()).circleCrop().into(profilePageBinding.photoFour);
                    profilePageBinding.progressBar3.setVisibility(View.GONE);
                }

                profilePageBinding.progressBar2.setVisibility(View.GONE);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}