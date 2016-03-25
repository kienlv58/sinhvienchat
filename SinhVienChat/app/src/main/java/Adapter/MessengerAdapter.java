package Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kien.sinhvienchat.R;

import java.util.ArrayList;

import CustomView.MessengerViewHolder;
import Enity.ProfileEnity;
import interfaceOnclick.OnItemclickListener;

/**
 * Created by Kiên on 3/12/2016.
 */
public class MessengerAdapter extends RecyclerView.Adapter<MessengerViewHolder> {

    ArrayList<ProfileEnity> listchat = new ArrayList<>();
    String time,content;
    OnItemclickListener itemclickListener;

    public ArrayList<ProfileEnity> getListchat() {
        return listchat;
    }

    public void setListchat(ArrayList<ProfileEnity> listchat) {
        this.listchat = listchat;
    }
    //vấn đề ở chỗ 1list tên avatar nhưng có cùng 1 time và 1 content;


    public MessengerAdapter(ArrayList<ProfileEnity> listchat, String time, String content) {
        this.listchat = listchat;
        this.time = time;
        this.content = content;
    }

    public MessengerAdapter() {
    }

    @Override
    public MessengerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.messenger_item,parent,false);
        return new MessengerViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(final MessengerViewHolder holder, int position) {
        ProfileEnity ms = listchat.get(position);
        holder.avatar.setImageBitmap(StringToBitMap(ms.getAvatar().toString()));
        holder.name.setText(ms.getName());
        holder.time.setText(time);
        holder.content.setText(content);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemclickListener != null){
                    itemclickListener.OnItemClick(v,holder.getPosition());
                }
            }
        });

    }

    //viet ham de setonclick cho fragment
    public void SetOnItemClick(final OnItemclickListener itemclickListener){
        this.itemclickListener = itemclickListener;

    }
    public static Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte= Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public int getItemCount() {
        return listchat.size();
    }
}
