package id.putraprima.retrofit.Adapters;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

import id.putraprima.retrofit.R;
import id.putraprima.retrofit.api.models.Envelope;
import id.putraprima.retrofit.api.models.RecipeResponse;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private Context context;
    private Envelope<List<RecipeResponse>> recipes;
    List<Envelope<RecipeResponse>> rr;

    public RecipeAdapter(Context context, Envelope<List<RecipeResponse>> recipes) {
        this.context = context;
        this.recipes = recipes;
    }

    @NonNull
    @Override
    public RecipeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_recipe, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeAdapter.ViewHolder holder, int position) {
        RecipeResponse recipe = recipes.getData().get(position);
//        Picasso.get().load("mobile.putraprima.id/uploads/"+recipe.getFoto()).into(holder.imgRecipe);
        Glide.with(context).load("mobile.putraprima.id/uploads/"+recipe.getFoto()).into(holder.imgRecipe);
//        holder.imgRecipe.setImageURI(Uri.parse("mobile.putraprima.id/uploads/"+recipe.getFoto()));
        holder.idtxt.setText(Integer.toString(recipe.getId()));
        holder.namaTxt.setText(recipe.getNama_resep());
        holder.descTxt.setText(recipe.getDeskripsi());
        holder.bahanTxt.setText(recipe.getBahan());
        holder.langkahTxt.setText(recipe.getLangkah_pembuatan());
        Picasso.Builder builder = new Picasso.Builder(context);
        builder.downloader(new OkHttp3Downloader(context));
        builder.build().load("https://mobile.putraprima.id/uploads/"+recipe.getFoto())
                .placeholder((R.drawable.ic_launcher_background))
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgRecipe);
    }

    @Override
    public int getItemCount() {
        return (recipes != null) ? recipes.getData().size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgRecipe;
        TextView idtxt,namaTxt, descTxt, bahanTxt, langkahTxt;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgRecipe = itemView.findViewById(R.id.imgRecipe);
            idtxt = itemView.findViewById(R.id.idTtx);
            namaTxt = itemView.findViewById(R.id.recipeTxt);
            descTxt = itemView.findViewById(R.id.descTxt);
            bahanTxt = itemView.findViewById(R.id.bahanTxt);
            langkahTxt = itemView.findViewById(R.id.langkahTxt);
        }
    }
}
