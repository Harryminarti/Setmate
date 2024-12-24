package com.setmate.setmate;

import static com.setmate.setmate.profile_page.first;
import static com.setmate.setmate.profile_page.four;
import static com.setmate.setmate.profile_page.second;
import static com.setmate.setmate.profile_page.third;

import android.os.SystemClock;
import android.view.View;
import android.view.View.OnClickListener;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.setmate.setmate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class home_page_adapter extends RecyclerView.Adapter<home_page_adapter.viewholder> {

    ArrayList<edit_pageClass>arrayList;

    private static int change=0;
    Context context;

public  static int   positions=0;
public String TAG = "context";

    int i=0;


    public home_page_adapter(ArrayList<edit_pageClass> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }




    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context==null){
            context=parent.getContext();
        }

        View v = LayoutInflater.from(context).inflate(R.layout.user_home_item,parent,false);
        return  new viewholder(v);

    }





    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {


        edit_pageClass editPageClass = arrayList.get(position);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();






// here we show the data of girls to current boys users--------------------------------------------------------------------------------







        if (!Objects.equals(editPageClass.getGender(), "male")){

//            Toast.makeText(context, "Hey he want girl", Toast.LENGTH_SHORT).show();


        firebaseDatabase.getReference().child("female_user").orderByChild("user_no").limitToLast(1).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot sat:snapshot.getChildren()) {


                    edit_pageClass edit_pageClass1 = sat.getValue(edit_pageClass.class);
                    if (Objects.equals(edit_pageClass1.getUser_id().toString(), editPageClass.getUser_id())){
//                    Log.d(TAG, "onDataChange: *************************"+edit_pageClass1.getUser_id());

                    firebaseDatabase.getReference().child("current_user").child(FirebaseAuth.getInstance().getUid()).setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            if(task.isSuccessful()){
                                holder.progressBar.setVisibility(View.GONE);
                                holder.textView.setText(editPageClass.getName());

                                if (editPageClass.getImageLink()!=null) {
                                    Glide.with(context).load(editPageClass.getImageLink()).into(holder.imageView);
                                }



                                Toast.makeText(context, "Its a last Member ,Please Restart the app to load app", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    }else{
                        firebaseDatabase.getReference("current_user").child(FirebaseAuth.getInstance().getUid()).setValue(editPageClass.getUser_no()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    holder.progressBar.setVisibility(View.GONE);
                                    holder.textView.setText(editPageClass.getName());
                                    holder.age.setText(editPageClass.getAge());
                                    holder.course.setText(editPageClass.getBio());

                                    if (editPageClass.getImageLink()!=null) {
                                        Glide.with(context).load(editPageClass.getImageLink()).into(holder.imageView);
                                    }


                                    holder.picture_change.setOnClickListener(new OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            firebaseDatabase.getReference().child("extra_image").addValueEventListener(new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                    if (snapshot.child(editPageClass.getUser_id()).exists()){

                                                        change++;

                                                        if (change==1 && snapshot.child(editPageClass.getUser_id()).child(first).exists()){
                                                            Glide.with(context).load(snapshot.child(editPageClass.getUser_id()).child(first).getValue().toString()).into(holder.imageView);
                                                        } else if (change==2 && snapshot.child(editPageClass.getUser_id()).child(second).exists()){
                                                            Glide.with(context).load(snapshot.child(editPageClass.getUser_id()).child(second).getValue().toString()).into(holder.imageView);
                                                        }else if (change==3 && snapshot.child(editPageClass.getUser_id()).child(third).exists()){
                                                            Glide.with(context).load(snapshot.child(editPageClass.getUser_id()).child(third).getValue().toString()).into(holder.imageView);
                                                        }else if (change==4 && snapshot.child(editPageClass.getUser_id()).child(four).exists()){
                                                            Glide.with(context).load(snapshot.child(editPageClass.getUser_id()).child(four).getValue().toString()).into(holder.imageView);

                                                        }else{

                                                            Glide.with(context).load(editPageClass.getImageLink()).into(holder.imageView);
                                                            change=0;
                                                        }



//                                                    Toast.makeText(context,"You have click on it",Toast.LENGTH_SHORT).show();







                                                    }
                                                    else {
                                                        Toast.makeText(context,"No more images uploaded by this user",Toast.LENGTH_LONG).show();

                                                }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError error) {

                                                }
                                            });



                                        }
                                    });




                                }

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


} else {



// here we show the data of boys to current girls users--------------------------------------------------------------------------------




//            Toast.makeText(context, "Hey he want boy", Toast.LENGTH_SHORT).show();


            firebaseDatabase.getReference().child("male_user").orderByChild("user_no").limitToLast(1).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot sat:snapshot.getChildren()) {


                        edit_pageClass edit_pageClass1 = sat.getValue(edit_pageClass.class);
                        if (Objects.equals(edit_pageClass1.getUser_id().toString(), editPageClass.getUser_id())){


//                            Log.d(TAG, "onDataChange: *************************"+edit_pageClass1.getUser_id());

                            firebaseDatabase.getReference().child("current_user").child(FirebaseAuth.getInstance().getUid()).setValue(1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if(task.isSuccessful()){
                                        holder.progressBar.setVisibility(View.GONE);
                                        holder.textView.setText(editPageClass.getName());


                                        if (editPageClass.getImageLink()!=null) {
                                            Glide.with(context).load(editPageClass.getImageLink()).into(holder.imageView);
                                        }

                                        Toast.makeText(context, "Its a last Member ,Please Restart the app to load app", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        }else{
                            firebaseDatabase.getReference("current_user").child(FirebaseAuth.getInstance().getUid()).setValue(editPageClass.getUser_no()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    if (task.isSuccessful()) {
                                        holder.progressBar.setVisibility(View.GONE);

                                        holder.textView.setText(editPageClass.getName());
                                        holder.age.setText(editPageClass.getAge());
                                        holder.course.setText(editPageClass.getBio());



                                        if (editPageClass.getImageLink()!=null) {
                                            Glide.with(context).load(editPageClass.getImageLink()).into(holder.imageView);
                                        }

                                        holder.picture_change.setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {

                                                firebaseDatabase.getReference().child("extra_image").addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                                                        if (snapshot.child(editPageClass.getUser_id()).exists()){

                                                            change++;

                                                            if (change==1 && snapshot.child(editPageClass.getUser_id()).child(first).exists()){
                                                                Glide.with(context).load(snapshot.child(editPageClass.getUser_id()).child(first).getValue().toString()).into(holder.imageView);
                                                            } else if (change==2 && snapshot.child(editPageClass.getUser_id()).child(second).exists()){
                                                                Glide.with(context).load(snapshot.child(editPageClass.getUser_id()).child(second).getValue().toString()).into(holder.imageView);
                                                            }else if (change==3 && snapshot.child(editPageClass.getUser_id()).child(third).exists()){
                                                                Glide.with(context).load(snapshot.child(editPageClass.getUser_id()).child(third).getValue().toString()).into(holder.imageView);
                                                            }else if (change==4 && snapshot.child(editPageClass.getUser_id()).child(four).exists()){
                                                                Glide.with(context).load(snapshot.child(editPageClass.getUser_id()).child(four).getValue().toString()).into(holder.imageView);

                                                            }else{

                                                                Glide.with(context).load(editPageClass.getImageLink()).into(holder.imageView);
                                                                change=0;
                                                            }



//                                                            Toast.makeText(context,"You have click on it",Toast.LENGTH_SHORT).show();







                                                        }
                                                        else {
                                                            Toast.makeText(context,"NO more images uploaded by this user.",Toast.LENGTH_LONG).show();

                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });



                                            }
                                        });


                                    }
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






        abstract class DoubleClickListener implements OnClickListener {

            // The time in which the second tap should be done in order to qualify as
            // a double click
            private static final long DEFAULT_QUALIFICATION_SPAN = 200;
            private long doubleClickQualificationSpanInMillis;
            private long timestampLastClick;

            public DoubleClickListener() {
                doubleClickQualificationSpanInMillis = DEFAULT_QUALIFICATION_SPAN;
                timestampLastClick = 0;
            }

            public DoubleClickListener(long doubleClickQualificationSpanInMillis) {
                this.doubleClickQualificationSpanInMillis = doubleClickQualificationSpanInMillis;
                timestampLastClick = 0;
            }

            @Override
            public void onClick(View v) {
                if((SystemClock.elapsedRealtime() - timestampLastClick) < doubleClickQualificationSpanInMillis) {
                    onDoubleClick();
                }
                timestampLastClick = SystemClock.elapsedRealtime();
            }

            public abstract void onDoubleClick();

        }





        // Scenario 2: Setting double click listener for myView, specifying a custom double-click span time
        holder.relativeLayout.setOnClickListener(new DoubleClickListener(400) {




            @Override
            public void onDoubleClick() {
// double-click code that is executed if the user double-taps
                // within a span of 400ms (default).

               holder.like_icon.setVisibility(View.VISIBLE);



                user_chat_connection userChatConnection = new user_chat_connection(editPageClass.getUser_id(),false);


                firebaseDatabase.getReference().child("user_chat_seen").child(FirebaseAuth.getInstance().getUid()).push().setValue(userChatConnection).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            holder.like_icon.setVisibility(View.GONE);

                            holder.relativeLayout.setClickable(false);
                            holder.like_shown.setVisibility(View.VISIBLE);

                        }


                    }
                });

            }
        });






        firebaseDatabase.getReference().child("user_chat_seen").child(FirebaseAuth.getInstance().getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot da: snapshot.getChildren()   ) {

                    user_chat_connection dan = da.getValue(user_chat_connection.class);
                    if ((dan.getReceiver_id().contains(editPageClass.getUser_id()))){

                        holder.relativeLayout.setClickable(false);
                        holder.like_icon.setVisibility(View.GONE);
                        holder.like_shown.setVisibility(View.VISIBLE);

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

        ImageView imageView,like_icon,homepage_image;
        ProgressBar progressBar;

        RelativeLayout relativeLayout,picture_change;
        ImageView floatingActionButton,like_shown;
        TextView textView,age,course;
        CircleImageView chat_per_user;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            imageView= itemView.findViewById(R.id.home_page_image);
            textView = itemView.findViewById(R.id.home_page_name);

//            -----------------------------------------------------
            age = itemView.findViewById(R.id.age);

            course  = itemView.findViewById(R.id.course);
//----------------------------------------------------------------------------

            like_icon = itemView.findViewById(R.id.likes_icon);
            homepage_image = itemView.findViewById(R.id.home_page_image);
            floatingActionButton = itemView.findViewById(R.id.last_refresh_btn);

            progressBar = itemView.findViewById(R.id.progress_bar);

            relativeLayout =itemView.findViewById(R.id.double_likes);

            like_shown = itemView.findViewById(R.id.likes_showns);


            picture_change = itemView.findViewById(R.id.picture_change);

        }
    }
}
