package id.putraprima.retrofit.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.putraprima.retrofit.Adapters.RecipeAdapter;
import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.helper.ServiceGenerator;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.RecipeResponse;
import id.putraprima.retrofit.api.services.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    private Context context;
    private  Envelope<List<RecipeResponse>> recipeList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        progressDialog = new ProgressDialog(RecipeActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        getRecipe();

    }

    public void getRecipe(){
        ApiInterface services = ServiceGenerator.createService(ApiInterface.class);
        Call<Envelope<List<RecipeResponse>>> call = services.getRecipe();
        call.enqueue(new Callback<Envelope<List<RecipeResponse>>>() {
            @Override
            public void onResponse(Call<Envelope<List<RecipeResponse>>> call, Response<Envelope<List<RecipeResponse>>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(RecipeActivity.this, "Succes", Toast.LENGTH_SHORT).show();
                    generateRecipe(response.body());
                    progressDialog.dismiss();

                }else{
                    progressDialog.show();
                }

            }

            @Override
            public void onFailure(Call<Envelope<List<RecipeResponse>>> call, Throwable t) {
                Toast.makeText(RecipeActivity.this, "Error Response", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void generateRecipe(Envelope<List<RecipeResponse>> list) {
//        RecyclerView recipesView = findViewById(R.id.rv_recipes);
        RecyclerView recipesView = findViewById(R.id.rv_recipes);
        RecipeAdapter adapter = new RecipeAdapter(this, list);
        recipesView.setAdapter(adapter);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(RecipeActivity.this);
        recipesView.setLayoutManager(layoutManager);

    }
}
