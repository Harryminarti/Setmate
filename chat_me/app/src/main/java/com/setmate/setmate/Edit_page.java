package com.setmate.setmate;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import com.setmate.setmate.databinding.ActivityEditPageBinding;

import java.util.Objects;
import java.util.Random;

public class Edit_page extends AppCompatActivity{
        private FirebaseAuth firebaseAuth;
        private FirebaseStorage firebaseStorage;
        private FirebaseDatabase firebaseDatabase;
        private Uri img_uri;
        private String image_Url;
        private static final int Respnse_code=11;

        private ActivityEditPageBinding editPageBinding;



        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            firebaseAuth= FirebaseAuth.getInstance();
            firebaseStorage= FirebaseStorage.getInstance();
            firebaseDatabase= FirebaseDatabase.getInstance();
            editPageBinding = ActivityEditPageBinding.inflate(getLayoutInflater());
            setContentView(editPageBinding.getRoot());


users_already_exist();



//--------------------------------------------------------------------------------------------------------------------->>>>>>>>>>

            editPageBinding.editPageSaveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    editPageBinding.progressBar.setVisibility(View.VISIBLE);

                    String name = editPageBinding.editPageName.getText().toString();
                    String age = editPageBinding.editPageAge.getText().toString();
                    String bio = editPageBinding.editPageInterest.getText().toString();



                    if (name.isEmpty()){
                        editPageBinding.editPageName.setError("Enter your name");
                        editPageBinding.progressBar.setVisibility(View.GONE);
                        return;
                    }

                    if (age.isEmpty()|| age.length()>=3 || Integer.parseInt(age)<=18){
                        editPageBinding.editPageAge.setError("Check Your Age Input ");
                        editPageBinding.progressBar.setVisibility(View.GONE);
                        return;

                    }
                    if (bio.isEmpty()){
                        editPageBinding.editPageInterest.setError("Fill it");
                        editPageBinding.progressBar.setVisibility(View.GONE);
                        return;
                    }

                    int selected_id = editPageBinding.genderRadioBtn.getCheckedRadioButtonId();

                    String gender;
                    if (editPageBinding.maleRadioBtn.isChecked() ){
                        gender = editPageBinding.maleRadioBtn.getText().toString().toLowerCase();
                    }else if (editPageBinding.femaleRadioBtn.isChecked()){
                         gender = editPageBinding.femaleRadioBtn.getText().toString().toLowerCase();

                    }else{

                        editPageBinding.genderText.setError("Choose Your Gender");
                        editPageBinding.progressBar.setVisibility(View.GONE);
                        return;
                    }


                    Toast.makeText(Edit_page.this, "Please wait while.. we are setup your profile", Toast.LENGTH_SHORT).show();


                    StorageReference reference = firebaseStorage.getReference("images").child(firebaseAuth.getCurrentUser().getUid());
                    DatabaseReference databaseReference = firebaseDatabase.getReference("user");

                    if (img_uri!=null){
                        reference.putFile(img_uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                if (task.isSuccessful()){

                                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            image_Url = uri.toString();

                                            int unique , unique2,unique3,unique1;

                                            Random random = new Random();
                                            unique1 = random.nextInt(1000);
                                            unique2 = random.nextInt(100);
                                            unique3 = random.nextInt(99);

                                            unique = unique1*unique2*unique3;


                                            edit_pageClass edit_pageClass = new edit_pageClass(name,age,bio,image_Url,firebaseAuth.getUid(),unique,gender);
                                            databaseReference.child(firebaseAuth.getUid()).setValue(edit_pageClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                  if (task.isSuccessful()){

                                                 if (Objects.equals(edit_pageClass.getGender(), "male")){


                                                     firebaseDatabase.getReference().child("male_user").child(firebaseAuth.getUid()).setValue(edit_pageClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<Void> task) {

                                                         }
                                                     });


                                                 }else {


                                                     firebaseDatabase.getReference().child("female_user").child(firebaseAuth.getUid()).setValue(edit_pageClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                         @Override
                                                         public void onComplete(@NonNull Task<Void> task) {

                                                         }
                                                     });


                                                 }


                                                      firebaseDatabase.getReference().child("current_user").child(firebaseAuth.getUid()).setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                          @Override
                                                          public void onComplete(@NonNull Task<Void> task) {

                                                              if (task.isSuccessful()) {





                                                                  Toast.makeText(Edit_page.this, "You are added successfully", Toast.LENGTH_SHORT).show();

                                                                  startActivity(new Intent(Edit_page.this,MainActivity.class));
                                                                  editPageBinding.progressBar.setVisibility(View.GONE);
                                                                  finish();
                                                              }
                                                          }
                                                      });




                                                    }else {
                                                        Toast.makeText(Edit_page.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                                    }

                                                }
                                            });

                                        }
                                    });


                                }else {
                                    Toast.makeText(Edit_page.this, "Something Went Wrong", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }else {



//   ---------------------------------------------------->>>>  without image user add code going here >>>>>>>>>>>>>>>>>>>>>>>>>
//                        _________________________________________________________________________________________________




                        int unique , unique2,unique3,unique1;

                        Random random = new Random();
                        unique1 = random.nextInt(1000);
                        unique2 = random.nextInt(100);
                        unique3 = random.nextInt(99);

                        unique = unique1*unique2*unique3;




                        editPageBinding.progressBar.setVisibility(View.VISIBLE);





                        Log.d(TAG, "onClick:oo eio sos_____________ "+image_Url);

                            edit_pageClass edit_pageClass = new edit_pageClass(name, age, bio, image_Url, firebaseAuth.getUid(), unique,gender);


                            databaseReference.child(firebaseAuth.getUid()).setValue(edit_pageClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()){



                                        if (Objects.equals(edit_pageClass.getGender(), "male")){


                                            firebaseDatabase.getReference().child("male_user").child(firebaseAuth.getUid()).setValue(edit_pageClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });


                                        }else {


                                            firebaseDatabase.getReference().child("female_user").child(firebaseAuth.getUid()).setValue(edit_pageClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {

                                                }
                                            });


                                        }





                                        firebaseDatabase.getReference().child("current_user").child(firebaseAuth.getUid()).setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if (task.isSuccessful()) {

                                                    for (int i=0;i<100000;i++){

                                                    }

                                                    Toast.makeText(Edit_page.this, "You are added successfully", Toast.LENGTH_SHORT).show();

                                                    startActivity(new Intent(Edit_page.this, MainActivity.class));

                                                    editPageBinding.progressBar.setVisibility(View.GONE);
                                                    finish();
                                                }
                                            }
                                        });




                                    } else {
                                        Toast.makeText(Edit_page.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                                    }

                                }
                            });

                    }





                }
            });






//            -------------------------------------------------------------------------------------------->>>>>>>>>>>>>>>



            editPageBinding.profileEditImages.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent,Respnse_code);


                }
            });


        }




//        ---------------------------------------------------------------->>>>>>>>>>>>>>>>>>




        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode==Respnse_code && resultCode==RESULT_OK&& data!=null){
                Uri uri = data.getData();
                img_uri= uri;

                Glide.with(getApplicationContext()).load(uri).circleCrop().into(editPageBinding.profileEditImages);
            }


        }



        private void users_already_exist(){





            firebaseDatabase.getReference().child("user").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    if (snapshot.child(firebaseAuth.getUid()).exists()){

                        editPageBinding.editPageName.setText(snapshot.child(firebaseAuth.getUid()).child("name").getValue().toString());
                        editPageBinding.editPageAge.setText(snapshot.child(firebaseAuth.getUid()).child("age").getValue().toString());
                        editPageBinding.editPageInterest.setText(snapshot.child(firebaseAuth.getUid()).child("bio").getValue().toString());

                        if (snapshot.child(firebaseAuth.getUid()).child("gender").getValue().toString().equals("male")){

                            editPageBinding.maleRadioBtn.setChecked(true);
                            editPageBinding.femaleRadioBtn.setVisibility(View.GONE);

                        }else {

                         editPageBinding.femaleRadioBtn.setChecked(true);
                            editPageBinding.maleRadioBtn.setVisibility(View.GONE);
                        }


if (snapshot.child(firebaseAuth.getUid()).child("imageLink").exists()) {
    if (snapshot.child(firebaseAuth.getUid()).child("imageLink").getValue() != null) {
        Glide.with(Edit_page.this).load(snapshot.child(firebaseAuth.getUid()).child("imageLink").getValue().toString()).circleCrop().into(editPageBinding.profileEditImages);
    }
}

                        editPageBinding.progressBar.setVisibility(View.GONE);

                    }

                }




                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(Edit_page.this, "something went wrong", Toast.LENGTH_SHORT).show();

                }
            });









        }



}