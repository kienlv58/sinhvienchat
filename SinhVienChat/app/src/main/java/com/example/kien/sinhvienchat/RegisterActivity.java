package com.example.kien.sinhvienchat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    EditText edt_email,edt_pass,edt_repass;
    TextInputLayout inputLayoutEmail,inputLayoutPass,inputLayoutRePass;
    Button btn_register;
    Firebase root;
    ProgressDialog wait;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
    }
    private void initView(){
        edt_email = (EditText)findViewById(R.id.edt_email);
        edt_pass = (EditText)findViewById(R.id.edt_pass);
        edt_repass = (EditText)findViewById(R.id.edt_repass);
        inputLayoutEmail = (TextInputLayout)findViewById(R.id.inputlayout_Email);
        inputLayoutPass = (TextInputLayout)findViewById(R.id.inputlayout_Pass);
        inputLayoutRePass = (TextInputLayout)findViewById(R.id.inputlayout_RePass);
        btn_register = (Button)findViewById(R.id.btn_register);

        edt_email.addTextChangedListener(new MyTextWatcher(edt_email));
        edt_pass.addTextChangedListener(new MyTextWatcher(edt_pass));
        edt_repass.addTextChangedListener(new MyTextWatcher(edt_repass));
        btn_register.setOnClickListener(REGISTER);

        Firebase.setAndroidContext(this);
        root = new Firebase("https://chatsv2016.firebaseio.com");
    }

    View.OnClickListener REGISTER = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (!validateEmail())
                return;
            else if (!validatePass())
                return;
            else if (!validateRepass())
                return;
                //kiem tra email/pass tren server
            else {
                wait = new ProgressDialog(RegisterActivity.this);
                wait.setMessage("register...");
                wait.show();
                root.createUser(edt_email.getText().toString().trim(), edt_pass.getText().toString().trim(), new Firebase.ValueResultHandler<Map<String, Object>>() {
                    @Override
                    public void onSuccess(Map<String, Object> result) {
                        //System.out.println("Successfully created user account with uid: " + result.get("uid"));
                        Bundle data = new Bundle();
                        data.putString("email",edt_email.getText().toString());
                        data.putString("pass",edt_pass.getText().toString());
                        Intent login = getIntent();
                        login.putExtra("create", data);
                        setResult(1,login);
                        wait.dismiss();
                        finish();
                    }

                    @Override
                    public void onError(FirebaseError firebaseError) {
                        // there was an error
                        wait.dismiss();
                        AlertDialog.Builder dialog = new AlertDialog.Builder(RegisterActivity.this);
                        dialog.setMessage("register not sucsess");
                        dialog.setNegativeButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                });
            }
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
                case R.id.edt_repass:
                    validateRepass();
                    break;
            }
        }
    }

    //kiem tra email co dung dinh dang
    private boolean validateEmail() {
        String email = edt_email.getText().toString().trim();
        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError("Enter valid email address");
            requestFocus(edt_email);
            return false;
        } else
            inputLayoutEmail.setErrorEnabled(false);
        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    //kiem tra mat khau
    private boolean validatePass() {
        String pass = edt_pass.getText().toString().trim();
        if (pass.isEmpty() || pass.length() < 6) {
            inputLayoutPass.setError("Validate pass");
            requestFocus(edt_pass);
            return false;
        } else
            inputLayoutPass.setErrorEnabled(false);
        return true;
    }
    //kiem tra nhap lai mat khau
    private boolean validateRepass(){
        String pass = edt_pass.getText().toString().trim();
        String repass = edt_repass.getText().toString().trim();
        if (repass.isEmpty() ||repass.length() < 6 || !(repass.equals(pass))) {
            inputLayoutRePass.setError("Validate  re-pass");
            requestFocus(edt_repass);
            return false;
        } else
            inputLayoutRePass.setErrorEnabled(false);
        return true;
    }
    //tra ve o sai
    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }
}
