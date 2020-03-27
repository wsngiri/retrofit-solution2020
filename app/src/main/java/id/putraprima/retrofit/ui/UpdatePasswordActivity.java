package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.UpdatePasswordRequest;
import id.putraprima.retrofit.api.models.UpdatePasswordResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePasswordActivity extends AppCompatActivity {

    EditText passwordtxt, passwordConTxt;
    UpdatePasswordRequest updatePasswordRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        passwordtxt = findViewById(R.id.updatepasstxt);
        passwordConTxt = findViewById(R.id.updateconpasstxt);

    }

    public void UpdatePassword(){
        SharedPreferences preference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class, "Bearer " + preference.getString("token", null));

        Call<Envelope<UpdatePasswordResponse>> call = service.doUpatePassword(updatePasswordRequest);
        call.enqueue(new Callback<Envelope<UpdatePasswordResponse>>() {
            @Override
            public void onResponse(Call<Envelope<UpdatePasswordResponse>> call, Response<Envelope<UpdatePasswordResponse>> response) {
                if (response.code() == 200){
                    Toast.makeText(UpdatePasswordActivity.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(UpdatePasswordActivity.this, "Update Gagal", Toast.LENGTH_SHORT).show();
                }
                Intent intent = new Intent();
                setResult(2, intent);
                finish();
            }

            @Override
            public void onFailure(Call<Envelope<UpdatePasswordResponse>> call, Throwable t) {
                Toast.makeText(UpdatePasswordActivity.this, "Error Request", Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void handleUpdatePass(View view) {
        String password = passwordtxt.getText().toString();
        String passcon = passwordConTxt.getText().toString();
        updatePasswordRequest = new UpdatePasswordRequest(password, passcon);

        boolean check;
        if (password.equals("")) {
            Toast.makeText(this, "Isi Password", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (passcon.equals("")) {
            Toast.makeText(this, "Isi Konfirmasi Password", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (password.length() < 8) {
            Toast.makeText(this, "Password harus lebih dari 7", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (!passcon.equals(password)) {
            Toast.makeText(this, "Konfirmasi Password tidak sama", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            check = true;
        }
        Toast.makeText(this, "Password baru : "+password, Toast.LENGTH_SHORT).show();
        if (check == true) {
            UpdatePassword();
        }

    }
}
