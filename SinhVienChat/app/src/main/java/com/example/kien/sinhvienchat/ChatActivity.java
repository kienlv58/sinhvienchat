package com.example.kien.sinhvienchat;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import Adapter.TabAdapter;

public class ChatActivity extends AppCompatActivity {
    TabLayout tabLayout;
    ViewPager viewPager;
    TabAdapter adapter;
    String email;
    public static String UID;

    public String getEmail() {
        return email;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Bundle data = getIntent().getBundleExtra("data");
        //truyen du lieu qua fragment
//        Tab4Fragment tab4Fragment = new Tab4Fragment();
//        tab4Fragment.setArguments(data);

        UID = data.getString("uid");
        email = data.getString("email");
        //tv1.setText(uid+"-"+email);
        initView();
    }
    private void initView(){
        tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        viewPager = (ViewPager)findViewById(R.id.view_pager);
        adapter = new TabAdapter(getSupportFragmentManager());
//        tv1 = (TextView)findViewById(R.id.txtv_data);
//        img = (CircleImageView)findViewById(R.id.img);
//        img.setImageResource(R.drawable.ki);


        viewPager.setAdapter(adapter);
        tabLayout.setTabsFromPagerAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

}
