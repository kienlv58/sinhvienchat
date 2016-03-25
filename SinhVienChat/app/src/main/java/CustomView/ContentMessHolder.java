package CustomView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kien.sinhvienchat.R;

/**
 * Created by Kiên on 3/13/2016.
 */
public class ContentMessHolder extends RecyclerView.ViewHolder{
    public  TextView content_msg;
    public ImageView imageView;

    public ContentMessHolder(View itemView) {
        super(itemView);
        content_msg = (TextView)itemView.findViewById(R.id.txtv_msg);
        imageView = (ImageView)itemView.findViewById(R.id.img);
    }
}
