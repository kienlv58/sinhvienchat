package com.example.kien.sinhvienchat;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import Adapter.MyMessAdapter;
import Enity.ConntentMessEnity;
import Enity.ProfileEnity;
import TabFragment.Tab4Fragment;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyMessenger extends AppCompatActivity {
    RecyclerView listchat;
    MyMessAdapter adapter;
    RecyclerView.LayoutManager manager;
    ArrayList<ConntentMessEnity> listdata = new ArrayList<>();
    EditText edt_msg;
    ImageButton btn_send;
    CircleImageView avt_user1, avt_user2;
    TextView name_user1, name_user2;
    Firebase root;
    String UID_user2;
    String UID_user1;
    Boolean chated = false;
    ProfileEnity user1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_messenger);
        Firebase.setAndroidContext(getApplication());
        UID_user2 = ChatActivity.UID;
        root = new Firebase("https://chatsv2016.firebaseio.com/");
        initView();
        new LoadMsgAsynctask().execute();
    }

    private void initView() {
        listchat = (RecyclerView) findViewById(R.id.rec_chat);
        manager = new LinearLayoutManager(getApplication());
        listchat.setLayoutManager(manager);
//        listdata.clear();
        adapter = new MyMessAdapter(listdata,UID_user2);
        listchat.setAdapter(adapter);
        listchat.setHasFixedSize(true);
        listchat.setItemAnimator(new DefaultItemAnimator());
        avt_user1 = (CircleImageView) findViewById(R.id.avt_user1);
        avt_user2 = (CircleImageView) findViewById(R.id.avatar_user2);
        name_user1 = (TextView) findViewById(R.id.name_user1);
        name_user2 = (TextView) findViewById(R.id.name_user2);

        user1 = (ProfileEnity) getIntent().getBundleExtra("data").getSerializable("profile");
        avt_user1.setImageBitmap(StringToBitMap(user1.getAvatar().toString()));
        name_user1.setText(user1.getName());
        UID_user1 = user1.getUID().toString();


        if (Tab4Fragment.getPf() != null) {
            ProfileEnity user2 = Tab4Fragment.getPf();
            avt_user2.setImageBitmap(StringToBitMap(user2.getAvatar().toString()));
            name_user2.setText(user2.getName());
        }

        edt_msg = (EditText) findViewById(R.id.edt_msg);
        btn_send = (ImageButton) findViewById(R.id.btn_send);


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
    public class LoadMsgAsynctask extends AsyncTask<Void,Void,Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            root.child(UID_user2).child("listchat").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot != null) {
                        String key = dataSnapshot.getKey().toString();
                        if (key.equals(UID_user1)){
                            chated = true;
                        }
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            return chated;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (chated == true){
                root.child(UID_user2).child("listchat").child(UID_user1).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        ConntentMessEnity ct = dataSnapshot.getValue(ConntentMessEnity.class);
                        listdata.add(ct);
                        adapter.notifyDataSetChanged();
                        listchat.scrollToPosition(adapter.getItemCount()-1);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sms = edt_msg.getText().toString().trim();
                        if (!sms.equals("")) {
                            ConntentMessEnity send = new ConntentMessEnity(sms, "No Image", UID_user2);
                            root.child(UID_user2).child("listchat").child(UID_user1).push().setValue(send);
                            edt_msg.setText("");
                        }
                    }
                });
            }
            else {

                root.child(user1.getUID().toString()).child("listchat").child(UID_user2).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        ConntentMessEnity ct = dataSnapshot.getValue(ConntentMessEnity.class);
                        listdata.add(ct);
                        adapter.notifyDataSetChanged();
                        edt_msg.requestFocus();
                        listchat.scrollToPosition(adapter.getItemCount()-1);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                btn_send.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String sms = edt_msg.getText().toString().trim();
                        if (!sms.equals("")) {
                            ConntentMessEnity send = new ConntentMessEnity(sms, "No Image", UID_user2);
                            root.child(user1.getUID().toString()).child("listchat").child(UID_user2).push().setValue(send);
                            edt_msg.setText("");
                        }
                    }
                });
            }
        }
    }
}
