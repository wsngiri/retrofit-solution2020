package id.putraprima.retrofit.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.LoginResponse;
import id.putraprima.retrofit.api.models.UserInfo;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {
    public Context context;
    TextView txtEmail, txtName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        txtEmail = findViewById(R.id.emailProfile);
        txtName = findViewById(R.id.nameProfile);
        context = getApplicationContext();
        getMe();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == 1){
            getMe();
            return;
        }else if (requestCode == 2 && resultCode == 2){
            getMe();
            return;
        }
    }
    private void getMe() {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(context);
        Toast.makeText(context, preference.getString("token",null), Toast.LENGTH_SHORT).show();
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class, "Bearer "+preference.getString("token",null));
        Call<Envelope<UserInfo>> call = service.me();
        call.enqueue(new Callback<Envelope<UserInfo>>() {
            @Override
            public void onResponse(Call<Envelope<UserInfo>> call, Response<Envelope<UserInfo>> response) {
                Toast.makeText(ProfileActivity.this, response.body().getData().getEmail(), Toast.LENGTH_SHORT).show();
                Toast.makeText(ProfileActivity.this, response.body().getData().getName(), Toast.LENGTH_SHORT).show();
                txtEmail.setText(response.body().getData().getEmail());
                txtName.setText(response.body().getData().getName());
            }

            @Override
            public void onFailure(Call<Envelope<UserInfo>> call, Throwable t) {
                Toast.makeText(ProfileActivity.this, "Error Request", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goUpdateProfile(View view) {
        Intent intent = new Intent(this, UpdateProfileActivity.class);
        startActivityForResult(intent, 1);
    }

    public void goUpdatePass(View view) {
        Intent intent = new Intent(this, UpdatePasswordActivity.class);
        startActivityForResult(intent, 2);
    }
}
