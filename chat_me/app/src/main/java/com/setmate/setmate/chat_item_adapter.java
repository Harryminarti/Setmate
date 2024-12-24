package com.setmate.setmate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.setmate.setmate.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class chat_item_adapter extends RecyclerView.Adapter {



    ArrayList<chat_data_class>arrayList;


    Context context;

    public chat_item_adapter(ArrayList<chat_data_class> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    int Item_send=1;
    int Item_receive =2;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context==null){
            context= parent.getContext();
        }

        if (viewType==Item_send){
            View v = LayoutInflater.from(context).inflate(R.layout.sender_item_massage,parent,false);
            return new senderViewholder(v);
        }
        else{
            View view = LayoutInflater.from(context).inflate(R.layout.receiver_item_massage,parent,false);
            return  new receiverViewholder(view);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        chat_data_class massageDetails = arrayList.get(position);

        if (holder.getClass()==senderViewholder.class){
            senderViewholder senderViewholder = (senderViewholder)holder;
            senderViewholder.sender_items.setText(massageDetails.getMassage());
        }else {
            receiverViewholder receiverViewholder= (receiverViewholder)holder;
            receiverViewholder.receiver_items.setText(massageDetails.getMassage());
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        chat_data_class md = arrayList.get(position);

        if (md.getSender_id().equals(FirebaseAuth.getInstance().getUid())){
            return Item_send;
        }else{
            return  Item_receive;
        }
    }

    class  senderViewholder extends RecyclerView.ViewHolder{

        TextView sender_items;

        public senderViewholder(@NonNull View itemView) {
            super(itemView);
            sender_items = itemView.findViewById(R.id.sender_massage_txt);
        }
    }

    class  receiverViewholder extends  RecyclerView.ViewHolder{

        TextView receiver_items;

        public receiverViewholder(@NonNull View itemView) {
            super(itemView);
            receiver_items = itemView.findViewById(R.id.receiver_massage_txt);
        }
    }


}
