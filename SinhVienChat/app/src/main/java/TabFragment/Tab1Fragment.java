package TabFragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kien.sinhvienchat.ChatActivity;
import com.example.kien.sinhvienchat.MyMessenger;
import com.example.kien.sinhvienchat.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

import Adapter.MessengerAdapter;
import Enity.ProfileEnity;
import interfaceOnclick.OnItemclickListener;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab1Fragment extends Fragment {
    RecyclerView recyclerView;
    MessengerAdapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<ProfileEnity> listdata = new ArrayList<>();
    Firebase root;


    public static Tab1Fragment neInstance() {
        return new Tab1Fragment();
    }


    public Tab1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Firebase.setAndroidContext(getContext());
        root = new Firebase("https://chatsv2016.firebaseio.com/");
        root.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

               String key = dataSnapshot.getKey().toString();
                root.child(key).child("profile").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        if (dataSnapshot != null) {
                            ProfileEnity pf = dataSnapshot.getValue(ProfileEnity.class);
                            if (pf != null && !pf.getUID().equals(ChatActivity.UID)) {
                                listdata.add(pf);
                                adapter.notifyDataSetChanged();
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

    super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab1, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.recy_vew);
        return v;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MessengerAdapter(listdata, "", "chat với tôi nào...");
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //set su kien click vao recycle view
        adapter .SetOnItemClick(new OnItemclickListener() {
            @Override
            public void OnItemClick(View V, int possition) {
                Intent detail = new Intent(getActivity(), MyMessenger.class);
                ProfileEnity p = adapter.getListchat().get(possition);
                Bundle b = new Bundle();
                b.putSerializable("profile", p);
                detail.putExtra("data", b);
                getActivity().startActivity(detail);
            }
        });

    }
}
