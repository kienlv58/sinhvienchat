package Adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kien.sinhvienchat.R;

import java.util.AbstractList;

import CustomView.FriendViewHolder;
import Enity.ProfileEnity;

/**
 * Created by Kiên on 3/15/2016.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendViewHolder> {
    AbstractList<ProfileEnity> listfriend;

    public FriendAdapter(AbstractList<ProfileEnity> listfriend) {
        this.listfriend = listfriend;
    }

    @Override
    public FriendViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_friend, parent, false);
        return new FriendViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(FriendViewHolder holder, int position) {
        ProfileEnity f = listfriend.get(position);
        holder.avatar.setImageBitmap(StringToBitMap(f.getAvatar().toString()));
        holder.name.setText(f.getName().toString());
        holder.adress.setText(f.getAdress().toString());

    }
    public static Bitmap StringToBitMap(String image) {
        try {
            byte[] encodeByte = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
    @Override
    public int getItemCount() {
        return listfriend.size();
    }


}
