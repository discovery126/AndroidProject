package com.example.myapplication.ui.favourite_recipes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.DescriptionRecipe;
import com.example.myapplication.FavouriteRecipe;
import com.example.myapplication.ListenerInterface;
import com.example.myapplication.MyBitMap;
import com.example.myapplication.R;
import com.example.myapplication.adapter.CategoryAdapter;
import com.example.myapplication.adapter.FavouriteRecipeAdapter;
import com.example.myapplication.adapter.RecipeAdapter;
import com.example.myapplication.model.CategoryModel;
import com.example.myapplication.model.RecipeModel;
import com.example.myapplication.ui.main_recipes.MainRecipesFragment;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FavouriteRecipesFragment extends Fragment implements ListenerInterface {
    static ArrayList<RecipeModel> favouriteRecipeModels = new ArrayList<>();
    static Map<Integer,Integer> indexesRecyclerView = new HashMap<>();
    private View rootView;
//
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_favourite_recipes, container, false);


        initFavouriteModels();
        if (favouriteRecipeModels.size()==0) {
            TextView a = rootView.findViewById(R.id.noFavouriteRecipeText);
            a.setVisibility(TextView.VISIBLE);
        }

        RecyclerView mRecyclerView = (RecyclerView) rootView.findViewById(R.id.favouriteRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(rootView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        FavouriteRecipeAdapter favouriteAdapter = new FavouriteRecipeAdapter(rootView.getContext(),favouriteRecipeModels,this);
        mRecyclerView.setAdapter(favouriteAdapter);
        return rootView;

    }

    private void initFavouriteModels() {
        favouriteRecipeModels.clear();
        indexesRecyclerView.clear();

        for (Integer i : FavouriteRecipe.items_id) {
            RecipeModel currentRecipeModel = MainRecipesFragment.fullListRecipeModels.get(i);

            favouriteRecipeModels.add(currentRecipeModel); // Добавляет в избранное

            indexesRecyclerView.put(favouriteRecipeModels.size()-1, i); // Запоминает индекс на главном recyclerView и на favourite
        }
    }

    @Override
    public void onItemClicked(int position) {
        Bundle bundle = new Bundle();
        bundle.putString("tittleRecipe",favouriteRecipeModels.get(position).getTittleRecipe());
        bundle.putString("authorRecipe",favouriteRecipeModels.get(position).getAuthorRecipe());
        bundle.putString("numberComponents",favouriteRecipeModels.get(position).getComponentsRecipe());
        bundle.putInt("imageRecipe",favouriteRecipeModels.get(position).getImageRecipe());
        bundle.putInt("pos",indexesRecyclerView.get(position));

        Navigation.findNavController(rootView).navigate(R.id.action_favourite_recipes_to_recipeFragment,bundle);
    }

    @Override
    public void onItemLongClicked(int position) {

    }
}