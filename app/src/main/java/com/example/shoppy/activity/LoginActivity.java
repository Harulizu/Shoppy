package com.example.shoppy.activity;


import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.telecom.CallScreeningService;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppy.R;
import com.example.shoppy.model.User;
import com.example.shoppy.respone.ResponseLogin;
import com.example.shoppy.rest.ApiClient;
import com.example.shoppy.rest.ApiInterface;
import com.example.shoppy.utils.SessionManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.btn_masuk)
    Button login;

    @BindView(R.id.username)
    EditText usr;

    @BindView(R.id.password)
    EditText pwd;

    @BindView(R.id.create_user)
    TextView reg_usr;

    ApiInterface apiservice;
    //Session Manager
    SessionManager sessionManager;

    private static final String TAG = "LoginActivity";

    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        setContentView(R.layout.login_relative);
        ButterKnife.bind(this);

        apiservice = ApiClient.getClient().create(ApiInterface.class);
        sessionManager = new SessionManager(this);

//        login = (Button) findViewById(R.id.btn_masuk);
//        usr = (EditText) findViewById(R.id.username);
//        pwd = (EditText) findViewById(R.id.password);
//        reg_usr = (TextView) findViewById(R.id.create_user);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });

        reg_usr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reg_usr();
            }
        });

    }

    private void reg_usr(){
        Intent i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    private void loginUser() {
//        String usr_login = usr.getText().toString();
//        Intent i = new Intent(this, MainActivity.class);
//        //Kirimkan username ke tampilan utama
//        i.putExtra("USR", usr_login);
//        startActivity(i);
        String username = usr.getText().toString();
        String password = pwd.getText().toString();



        apiservice.login(username,password).enqueue(new Callback<ResponseLogin>() {
            @Override
            public void onResponse(Call<ResponseLogin> call, Response<ResponseLogin> response) {
//                String err = response.body().getStatus();
                if(username.isEmpty()){
                    usr.setError("Harap isi username anda");
                    usr.requestFocus();
                    return;
                }
                if(password.isEmpty()){
                    pwd.setError("Harap isi password anda");
                    pwd.requestFocus();
                    return;
                }
                if (response.body().getStatus()==1){
                    User userLoggedIn = response.body().getUser();
                    sessionManager.createLoginSession(userLoggedIn);
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
                    startActivity(intent);
                    finish();
                }else if (response.body().getStatus()==0) {
                    Toast.makeText(LoginActivity.this, "User tidak ditemukan", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(LoginActivity.this, "Server bermasalah", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseLogin> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
            }

        });

    }
}
