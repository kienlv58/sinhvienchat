package CustomView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.kien.sinhvienchat.R;

import de.hdodenhof.circleimageview.CircleImageView;
import interfaceOnclick.OnItemclickListener;

/**
 * Created by Kiên on 3/12/2016.
 */
public class MessengerViewHolder extends RecyclerView.ViewHolder {
    public CircleImageView avatar;
    public TextView name,content,time;
    OnItemclickListener itemclickListener;
    public MessengerViewHolder(View itemView) {
        super(itemView);
        avatar = (CircleImageView)itemView.findViewById(R.id.avatar);
        name = (TextView)itemView.findViewById(R.id.name);
        content = (TextView)itemView.findViewById(R.id.content);
        time = (TextView)itemView.findViewById(R.id.time);

    }

}
