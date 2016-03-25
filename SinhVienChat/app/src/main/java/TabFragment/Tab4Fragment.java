package TabFragment;


import android.annotation.TargetApi;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.kien.sinhvienchat.ChatActivity;
import com.example.kien.sinhvienchat.R;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.io.ByteArrayOutputStream;

import Enity.ProfileEnity;
import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab4Fragment extends Fragment {
    public static Tab4Fragment neInstance() {
        return new Tab4Fragment();
    }

    CircleImageView avatarPF;
    EditText emailPF, namePF, birthPF, adressPF, schoolPF;
    Button svePF;
    Firebase root;
    ImageButton camera;
    String avatarString = "chua co avatar";
    String email;
    public static String UID;
    static ProfileEnity pf;

    public static ProfileEnity getPf() {
        return pf;
    }

    public void setPf(ProfileEnity pf) {
        this.pf = pf;
    }

    public Tab4Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Firebase.setAndroidContext(getContext());
        root = new Firebase("https://chatsv2016.firebaseio.com/");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_tab4, container, false);
        avatarPF = (CircleImageView) v.findViewById(R.id.avatar_profile);
        namePF = (EditText) v.findViewById(R.id.edt_nameprofile);
        birthPF = (EditText) v.findViewById(R.id.edt_birthday);
        adressPF = (EditText) v.findViewById(R.id.edt_adressrofile);
        schoolPF = (EditText) v.findViewById(R.id.edt_schoolprofile);
        svePF = (Button) v.findViewById(R.id.save_profile);
        emailPF = (EditText) v.findViewById(R.id.edt_email);
        camera = (ImageButton) v.findViewById(R.id.btn_camera);
        camera.setOnClickListener(clickTakephoto);
        //lấy email từ activity main gửi về
        ChatActivity activity = (ChatActivity) getActivity();
        email = activity.getEmail();
        UID = activity.getUID();
        emailPF.setText(email);


            //lưu thông tin người dùng khi bấm save
            svePF.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String EMAIL = emailPF.getText().toString().trim();
                    String NAME = namePF.getText().toString().trim();
                    String BIRTH = birthPF.getText().toString().trim();
                    String ADRESS = adressPF.getText().toString().trim();
                    String SCHOOL = schoolPF.getText().toString().trim();
                    if (NAME.equals("") || BIRTH.equals("") || ADRESS.equals("") || SCHOOL.equals("")) {
                        Toast.makeText(getContext(), "du lieu chua dung", Toast.LENGTH_SHORT).show();
                    } else {

                        ProfileEnity profile = new ProfileEnity(UID, avatarString, EMAIL, NAME, BIRTH, ADRESS, SCHOOL, "FRIENDS");
                        root.child(UID).child("profile").child(UID). setValue(profile);
                    }
                }
            });
        root.child(UID).child("profile").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    pf = dataSnapshot.getValue(ProfileEnity.class);
                    if (pf != null && pf.getEmail().equals(email)) {
                        namePF.setText(pf.getName());
                        birthPF.setText(pf.getBirth());
                        adressPF.setText(pf.getAdress());
                        schoolPF.setText(pf.getSchool());

                        if (!pf.getAvatar().toString().equals("chua co avatar")) {
                            Bitmap avt = StringToBitMap(pf.getAvatar().toString());
                            avatarPF.setImageBitmap(avt);
                        }
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot != null) {
                    pf = dataSnapshot.getValue(ProfileEnity.class);
                    if (pf != null && pf.getEmail().equals(email)) {
                        namePF.setText(pf.getName());
                        birthPF.setText(pf.getBirth());
                        adressPF.setText(pf.getAdress());
                        schoolPF.setText(pf.getSchool());

                        if (!pf.getAvatar().toString().equals("chua co avatar")) {
                            Bitmap avt = StringToBitMap(pf.getAvatar().toString());
                            avatarPF.setImageBitmap(avt);
                        }
                    }
//
                }
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

            return v;
        }


        //chup anh
        View.OnClickListener clickTakephoto = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takephoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(takephoto, 1);
            }
        };

    //lay anh
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR1)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == ChatActivity.RESULT_OK && data != null) {
            //lấy uri của ảnh và chuyển đổi sang bitmap.
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            ChatActivity chat = (ChatActivity) getActivity();
            Cursor cursor = chat.getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            cursor.moveToFirst();
            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            Bitmap bitmap = BitmapFactory.decodeFile(picturePath);
            avatarString = BitMapToString(bitmap);
        }
    }

    public static Bitmap StringToBitMap(String image){
        try{
            byte [] encodeByte=Base64.decode(image,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }
    public static String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] arr=baos.toByteArray();
        String result=Base64.encodeToString(arr, Base64.DEFAULT);
        return result;
    }
}
