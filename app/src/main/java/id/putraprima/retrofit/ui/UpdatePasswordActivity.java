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
import id.putraprima.retrofit.api.models.ApiError;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.ErrorUtils;
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
                    SharedPreferences.Editor editor = preference.edit();
                    Toast.makeText(UpdatePasswordActivity.this, "Update Berhasil", Toast.LENGTH_SHORT).show();
//                    finishAffinity();
//                    editor.remove("token");
                    Intent intent = new Intent(UpdatePasswordActivity.this, ProfileActivity.class);
                    startActivity(intent);

                }
                if (response.errorBody() != null){
                    ApiError error = ErrorUtils.parseError(response);
                    if(error.getError().getPassword() != null) {
                        for(int i =0;i<error.getError().getPassword().size();i++) {
                            Toast.makeText(UpdatePasswordActivity.this,  error.getError().getPassword().get(i), Toast.LENGTH_LONG).show();
                        }
                    }
                }
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
            UpdatePassword();


    }
}
