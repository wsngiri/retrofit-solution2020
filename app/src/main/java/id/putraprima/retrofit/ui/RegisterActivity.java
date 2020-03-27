package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.Envelope;
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
                if (response.code() !=201 ){
                    Toast.makeText(RegisterActivity.this, "Register Gagal Email Sudah Terdaftar", Toast.LENGTH_SHORT).show();
                } else if(response.code() == 201){
                    Toast.makeText(RegisterActivity.this, "Register Berhasil", Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, response.body().getData().getEmail(), Toast.LENGTH_SHORT).show();
                    Toast.makeText(RegisterActivity.this, response.body().getData().getName(), Toast.LENGTH_SHORT).show();
                }
            }

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

        boolean check;
        if (name.equals("")) {
            Toast.makeText(this, "Name is Empty!", Toast.LENGTH_SHORT).show();
            check = false;
        } else if(email.equals("")) {
            Toast.makeText(this, "Email is Empty!", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (password.equals("")) {
            Toast.makeText(this, "Password is Empty!", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (password.equals("")) {
            Toast.makeText(this, "Password Confirmation is Empty!", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (password.length() < 8) {
            Toast.makeText(this, "Password limit 8", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (!password_confirm.equals(password)) {
            Toast.makeText(this, "Confirm Password not Same!", Toast.LENGTH_SHORT).show();
            check = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Email Not Valid", Toast.LENGTH_SHORT).show();
            check = false;
        } else {
            check = true;
        }

        if (check == true) {
            Register();
        }
    }
}
