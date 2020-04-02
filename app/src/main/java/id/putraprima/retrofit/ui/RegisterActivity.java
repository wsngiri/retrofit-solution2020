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
import android.widget.TextView;
import android.widget.Toast;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.ApiError;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.ErrorUtils;
import id.putraprima.retrofit.api.models.RegisterRequest;
import id.putraprima.retrofit.api.models.RegisterResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    EditText nameTxt, emailTxt, passwordTxt, conPasstxt;
    public RegisterRequest registerRequest;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nameTxt = findViewById(R.id.usernametxt);
        emailTxt = findViewById(R.id.email);
        passwordTxt = findViewById(R.id.passwordtxt);
        conPasstxt = findViewById(R.id.password_confirm);
    }

    public  void Register(){
        ApiInterface service = ServiceGenerator.createService(ApiInterface.class);
        Call<Envelope<RegisterResponse>> call = service.doRegister(registerRequest);
        call.enqueue(new Callback<Envelope<RegisterResponse>>() {
            @Override
            public void onResponse(Call<Envelope<RegisterResponse>> call, Response<Envelope<RegisterResponse>> response) {
                if(response.errorBody()!=null) {
                    ApiError error = ErrorUtils.parseError(response);
                    if (error.getError().getName() != null && error.getError().getEmail() != null && error.getError().getPassword() != null) {
                        Toast.makeText(RegisterActivity.this, error.getError().getName().get(0) + ", " + error.getError().getEmail().get(0) + " and " + error.getError().getPassword().get(0), Toast.LENGTH_LONG).show();
                    } else if (error.getError().getName() != null) {
                        Toast.makeText(RegisterActivity.this,  error.getError().getName().get(0), Toast.LENGTH_LONG).show();
                    } else if (error.getError().getEmail() != null) {
                        Toast.makeText(RegisterActivity.this, error.getError().getEmail().get(0), Toast.LENGTH_LONG).show();
                    } else if (error.getError().getPassword() != null) {
                        for (int i = 0; i < error.getError().getPassword().size(); i++) {
                            Toast.makeText(RegisterActivity.this,  error.getError().getPassword().get(i), Toast.LENGTH_LONG).show();
                        }
                    } else {

                    }
                    if (response.body() != null) {
                        Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, MainActivity.class));
                    }
                }

            }
//                if (response.code() !=201 ){
//                    Toast.makeText(RegisterActivity.this, "Register Gagal Email Sudah Terdaftar", Toast.LENGTH_SHORT).show();
//                } else if(response.code() == 201){
//                    Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
//                    Toast.makeText(RegisterActivity.this, response.body().getData().getEmail(), Toast.LENGTH_SHORT).show();
//                    Toast.makeText(RegisterActivity.this, response.body().getData().getName(), Toast.LENGTH_SHORT).show();
//                }
//            }

            @Override
            public void onFailure(Call<Envelope<RegisterResponse>> call, Throwable t) {

            }
        });

    }

    public void handleRegister(View view) {
        String name = nameTxt.getText().toString();
        String email = emailTxt.getText().toString();
        String password = passwordTxt.getText().toString();
        String password_confirm = conPasstxt.getText().toString();
        registerRequest = new RegisterRequest(name, email, password, password_confirm);
        Register();
    }
}
