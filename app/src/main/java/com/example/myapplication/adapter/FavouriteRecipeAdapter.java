package com.example.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.ListenerInterface;
import com.example.myapplication.R;
import com.example.myapplication.model.RecipeModel;
import com.example.myapplication.ui.favourite_recipes.FavouriteRecipesFragment;
import com.example.myapplication.ui.main_recipes.MainRecipesFragment;

import java.util.ArrayList;

public class FavouriteRecipeAdapter extends RecyclerView.Adapter<FavouriteRecipeAdapter.MyViewHolder> {

    Context context;
    ArrayList<RecipeModel> recipes;
    private ListenerInterface listener;
    public FavouriteRecipeAdapter(Context context, ArrayList<RecipeModel> recipes,ListenerInterface listener) {
        this.context = context;
        this.recipes = recipes;
        this.listener = listener;
    }

    @NonNull
    @Override
    public FavouriteRecipeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_recipe,parent,false);
        return new FavouriteRecipeAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteRecipeAdapter.MyViewHolder holder, int position) {
        holder.textTittleRecipe.setText(recipes.get(position).getTittleRecipe());
        holder.authorRecipe.setText("Автор: ".concat(recipes.get(position).getAuthorRecipe()));
        holder.textComponentsRecipe.setText(recipes.get(position).getComponentsRecipe());
        holder.imageRecipe.setImageResource(recipes.get(position).getImageRecipe());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Передаёт позицию рецепта, чтобы в дальнейшем передать данные во 2 фрагмент
                listener.onItemClicked(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView imageRecipe;
        TextView textTittleRecipe, textComponentsRecipe, authorRecipe;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageRecipe = itemView.findViewById(R.id.imageRecipe);
            textTittleRecipe = itemView.findViewById(R.id.titleRecipe);
            textComponentsRecipe = itemView.findViewById(R.id.numberComponents);
            authorRecipe = itemView.findViewById(R.id.authorRecipe);

        }
    }
}
