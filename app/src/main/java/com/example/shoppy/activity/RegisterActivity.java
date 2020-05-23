package com.example.shoppy.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.shoppy.R;
import com.example.shoppy.rest.ApiClient;
import com.example.shoppy.rest.ApiInterface;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.ResponseBody;
import retrofit2.Callback;
import retrofit2.Response;


public class RegisterActivity extends AppCompatActivity {
    @BindView(R.id.etusername)
    EditText etusername;

    @BindView(R.id.etpassword)
    EditText etpassword;

    @BindView(R.id.etnama)
    EditText etnama;

    @BindView(R.id.etemail)
    EditText etemail;

    @BindView(R.id.ethp)
    EditText ethp;

    @BindView(R.id.btnsave)
    Button btnsave;

    @BindView(R.id.masuk)
    TextView usr_login;

    ApiInterface apiService;
    ProgressDialog pd;

    private static final String TAG = RegisterActivity.class.getSimpleName();


    protected void onCreate (Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registeruser);
        ButterKnife.bind(this);
        apiService = ApiClient.getClient().create(ApiInterface.class);
        btnsave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signup();
            }
        });
        usr_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usr_login();
            }
        });

    }
    //@OnClick(R.id.btnsave)

    public void signup() {
        Log.d(TAG,"Menjalankan method Signup");
        pd = ProgressDialog.show(this,null,"Sedang mendaftarkan account",true,false);
        String username = etusername.getText().toString();
        String password = etpassword.getText().toString();
        String nama_lengkap = etnama.getText().toString();
        String email = etemail.getText().toString();
        String no_hp = ethp.getText().toString();

        Log.d(TAG,"mendapatkan data dari edit text");

        apiService.sign_up(username,password,nama_lengkap,email,no_hp).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(retrofit2.Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.d(TAG,"berhasil memanggil api");
                if (response.isSuccessful()){
                    pd.dismiss();
                    Log.d(TAG,"succes mendapatkan api");
                    Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(i);
                    finish();
                }
            }

            @Override
            public void onFailure(retrofit2.Call<ResponseBody> call, Throwable t) {
                pd.dismiss();
                Log.e(TAG,t.getLocalizedMessage());
                Toast.makeText(getApplicationContext(),"Gagal",Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Intent i = new Intent(RegisterActivity.this,LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(i);

    }

    private void usr_login(){
        Intent i = new Intent(this,LoginActivity.class);
        startActivity(i);
    }
}
