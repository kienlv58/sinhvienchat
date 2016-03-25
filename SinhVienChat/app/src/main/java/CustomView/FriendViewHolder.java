package CustomView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kien.sinhvienchat.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Kiên on 3/15/2016.
 */
public class FriendViewHolder extends RecyclerView.ViewHolder{
   public CircleImageView avatar;
   public TextView name;
   public TextView adress;
    public FriendViewHolder(View itemView) {
        super(itemView);
        avatar = (CircleImageView) itemView.findViewById(R.id.avatar_friend);
        name = (TextView)itemView.findViewById(R.id.name);
        adress = (TextView)itemView.findViewById(R.id.adress);
    }
}
