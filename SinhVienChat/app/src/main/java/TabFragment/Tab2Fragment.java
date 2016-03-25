package TabFragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kien.sinhvienchat.ChatActivity;
import com.example.kien.sinhvienchat.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import Adapter.FriendAdapter;
import Enity.ProfileEnity;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab2Fragment extends Fragment {

    public static Tab2Fragment neInstance() {
        return new Tab2Fragment();
    }

    public Tab2Fragment() {
        // Required empty public constructor
    }

    RecyclerView Recy_listfriend;
    FriendAdapter adapter;
    ArrayList<ProfileEnity> listdatafriend = new ArrayList<>();
    RecyclerView.LayoutManager layoutManager;
    Firebase root;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getContext());
        root = new Firebase("https://chatsv2016.firebaseio.com/");
        root.child(ChatActivity.UID).child("profile").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    ProfileEnity pf = dataSnapshot.getValue(ProfileEnity.class);
                    listdatafriend.add(pf);
                    adapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                ProfileEnity pf = dataSnapshot.getValue(ProfileEnity.class);
//                for (int i = 0;i < listdatafriend.size();i++){
//
//                }
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                ProfileEnity pf = dataSnapshot.getValue(ProfileEnity.class);
//                listdatafriend.add(pf);
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//                ProfileEnity pf = dataSnapshot.getValue(ProfileEnity.class);
//                listdatafriend.add(pf);
//                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


        }


        @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState){

            // Inflate the layout for this fragment
            View v = inflater.inflate(R.layout.fragment_tab2, container, false);
            Recy_listfriend = (RecyclerView) v.findViewById(R.id.recy_friend);
            adapter = new FriendAdapter(listdatafriend);
            layoutManager = new LinearLayoutManager(getContext());
            Recy_listfriend.setLayoutManager(layoutManager);
            Recy_listfriend.setAdapter(adapter);
            return v;
        }

        @Override
        public void onViewCreated (View view, Bundle savedInstanceState){
            super.onViewCreated(view, savedInstanceState);

        }

    }
