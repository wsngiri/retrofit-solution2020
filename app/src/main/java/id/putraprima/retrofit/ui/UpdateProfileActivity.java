package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.LoginRequest;
import id.putraprima.retrofit.api.models.RegisterResponse;
import id.putraprima.retrofit.api.models.UpdatePasswordRequest;
import id.putraprima.retrofit.api.models.UpdateProfileRequest;
import id.putraprima.retrofit.api.models.UpdateProfileResponse;
import id.putraprima.retrofit.api.models.UserInfo;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfileActivity extends AppCompatActivity {


    EditText emailTxt, nametxt;
    UpdateProfileRequest updateProfileRequest;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        emailTxt = findViewById(R.id.emailupdate);
        nametxt =findViewById(R.id.nameUpdate);
    }


    private void UpdateProfile() {
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class, "Bearer "+preference.getString("token",null));
//        Toast.makeText(context, preference.getString("token",null), Toast.LENGTH_SHORT).show();

        Call<Envelope<UpdateProfileResponse>> call = service.doUpateProfile(updateProfileRequest);
        call.enqueue(new Callback<Envelope<UpdateProfileResponse>>() {
            @Override
            public void onResponse(Call<Envelope<UpdateProfileResponse>> call, Response<Envelope<UpdateProfileResponse>> response) {
//                Toast.makeText(UpdateProfileActivity.this, response.body().getData().getEmail(), Toast.LENGTH_SHORT).show();
                if (response.code() != 200){
                    Toast.makeText(UpdateProfileActivity.this, "Update gagal", Toast.LENGTH_SHORT).show();
                }
                else if(response.code() == 200){
                    Toast.makeText(UpdateProfileActivity.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent();
                setResult(1, intent);
                finish();
            }

            @Override
            public void onFailure(Call<Envelope<UpdateProfileResponse>> call, Throwable t) {
                Toast.makeText(UpdateProfileActivity.this, "Error Request", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void hanldeUpdatee(View view) {
        String name = emailTxt.getText().toString();
        String email = nametxt.getText().toString();
        updateProfileRequest = new UpdateProfileRequest(name, email);

        boolean check;
        if (name.equals("")) {
            Toast.makeText(this, "Isi Nama!", Toast.LENGTH_SHORT).show();
            check = false;
        } else if(email.equals("")) {
            Toast.makeText(this, "Isi Email!", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            check = true;
        }

        if (check == true) {
            UpdateProfile();
        }
    }
}
