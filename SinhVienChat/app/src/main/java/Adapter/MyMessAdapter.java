package Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.kien.sinhvienchat.R;

import java.util.ArrayList;

import CustomView.ContentMessHolder;
import Enity.ConntentMessEnity;

/**
 * Created by Kiên on 3/13/2016.
 */
public class MyMessAdapter extends RecyclerView.Adapter<ContentMessHolder> {
    ArrayList<ConntentMessEnity> listchat;
    String UID;
    LinearLayout layout_parent;
    LinearLayout bubble_layout;

    public MyMessAdapter(ArrayList<ConntentMessEnity> listchat, String UID) {
        this.listchat = listchat;
        this.UID = UID;
    }

    @Override
    public ContentMessHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_content_chat_user1, parent, false);
        layout_parent = (LinearLayout) v.findViewById(R.id.bubble_layout_parrent);
        bubble_layout = (LinearLayout) v.findViewById(R.id.bubble_layout);
        return new ContentMessHolder(v);
    }

    @Override
    public void onBindViewHolder(ContentMessHolder holder, int position) {
        ConntentMessEnity chat = listchat.get(position);
        //hiện tin nhắn ở bên phải
        if (chat.getKey().equals(UID)) {
            bubble_layout.setBackgroundResource(R.drawable.bg1);
            layout_parent.setGravity(Gravity.RIGHT);

        } else {
            //hiện tn ở bên trái
            bubble_layout.setBackgroundResource(R.drawable.bg2);
            layout_parent.setGravity(Gravity.LEFT);
        }
        //nếu có ảnh
        if (!chat.getImg().equals("No Image")) {
            holder.imageView.setVisibility(View.VISIBLE);
            holder.imageView.setImageBitmap(StringToBitMap(chat.getImg().toString()));
        } else
            holder.imageView.setVisibility(View.GONE);
        holder.content_msg.setText(chat.getMsg());
    }

    //khi nguoi dung bam nut gui se them 1 doi tuong tin nhan den adpter
    public void addNewMS(ConntentMessEnity c) {
        listchat.add(c);
    }

    @Override
    public int getItemCount() {
        return listchat.size();
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
}
