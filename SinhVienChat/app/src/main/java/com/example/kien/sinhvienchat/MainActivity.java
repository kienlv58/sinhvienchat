package com.example.kien.sinhvienchat;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

public class MainActivity extends AppCompatActivity {
    Button btn_sigup;
    EditText edt_mail, edt_pass;
    TextView txtv_forgotpass, txtv_register;
    TextInputLayout inputLayout_Email, inputLayout_Pass;
    Firebase root;
    ProgressDialog wait;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        edt_mail = (EditText) findViewById(R.id.edt_email);
        edt_pass = (EditText) findViewById(R.id.edt_pass);
        btn_sigup = (Button) findViewById(R.id.btn_sigup);
        txtv_forgotpass = (TextView) findViewById(R.id.txtv_forgotpass);
        txtv_register = (TextView) findViewById(R.id.txtv_register);
        inputLayout_Email = (TextInputLayout) findViewById(R.id.inputlayout_Email);
        inputLayout_Pass = (TextInputLayout) findViewById(R.id.inputlayout_Pass);

        edt_mail.setText("kien@gmail.com");
        edt_pass.setText("02101994");

        edt_mail.addTextChangedListener(new MyTextWatcher(edt_mail));
        edt_pass.addTextChangedListener(new MyTextWatcher(edt_pass));
        btn_sigup.setOnClickListener(LOGIN);
        txtv_register.setOnClickListener(REGISTER);
        txtv_forgotpass.setOnClickListener(FORGOT);

        Firebase.setAndroidContext(this);
        root = new Firebase("https://chatsv2016.firebaseio.com");
    }

    View.OnClickListener LOGIN = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!validateEmail())
                return;
            else if (!validatePass())
                return;
                //kiem tra email/pass tren server
            else {
                wait  = new ProgressDialog(MainActivity.this);
                wait.setMessage("login...");
                wait.show();
                root.authWithPassword(edt_mail.getText().toString().trim(), edt_pass.getText().toString().trim(), new Firebase.AuthResultHandler() {
                    @Override
                    public void onAuthenticated(AuthData authData) {
//                    System.out.println("User ID: " + authData.getUid() + ", Provider: " + authData.getProvider());
                        Intent main = new Intent(MainActivity.this, ChatActivity.class);
                        String e = (String) authData.getProviderData().get("email");
                        String uid = authData.getUid();
                        Bundle data = new Bundle();
                        data.putString("uid",uid);
                        data.putString("email",e);
                        main.putExtra("data", data);
                        startActivity(main);
                        wait.dismiss();
                        finish();
                    }

                    @Override
                    public void onAuthenticationError(FirebaseError firebaseError) {
                        wait.dismiss();
                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                        dialog.setMessage("Email or password not corret");
                        dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        // there was an error
                    }
                });

            }

        }
    };
    //click register
    View.OnClickListener REGISTER = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent regis = new Intent(MainActivity.this,RegisterActivity.class);
            startActivityForResult(regis,100);
        }
    };
    //ham lay du lieu sau khi dang ky xong

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            if (requestCode == 100){
                if (resultCode == 1){
                    Bundle result = data.getBundleExtra("create");
                    String email = result.getString("email");
                    String pass = result.getString("pass");
                    edt_mail.setText(email);
                    edt_pass.setText(pass);
                }
            }
        }
    }

    //click forgot password
    View.OnClickListener FORGOT = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //hien 1 dialog nhap email
        }
    };
    private class MyTextWatcher implements TextWatcher {
        View v;

        public MyTextWatcher(View v) {
            this.v = v;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (v.getId()) {
                case R.id.edt_email:
                    validateEmail();
                    break;
                case R.id.edt_pass:
                    validatePass();
                    break;
            }
        }
    }

    //kiem tra email co dung dinh dang
    private boolean validateEmail() {
        String email = edt_mail.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayout_Email.setError("Enter valid email address");
            requestFocus(edt_mail);
            return false;
        } else
            inputLayout_Email.setErrorEnabled(false);
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //kiem tra mat khau
    private boolean validatePass() {
        String pass = edt_pass.getText().toString().trim();
        if (pass.isEmpty() || pass.length() < 6) {
            inputLayout_Pass.setError("Validate pass");
            requestFocus(edt_pass);
            return false;
        } else
            inputLayout_Pass.setErrorEnabled(false);
        return true;
    }

    //tra ve o sai
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
