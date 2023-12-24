package com.example.myapplication.ui.favourite_recipes;

import static com.example.myapplication.MainActivity.AUTHORIZATION;
import static com.example.myapplication.MainActivity.ID;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import com.example.myapplication.ListenerInterface;
import com.example.myapplication.R;
import com.example.myapplication.adapter.FavouriteRecipeAdapter;
import com.example.myapplication.database.MainDb;
import com.example.myapplication.database.RecipeModel;

import java.util.ArrayList;
import java.util.List;

public class FavouriteRecipesFragment extends Fragment implements ListenerInterface {
    private ArrayList<RecipeModel> favouriteRecipeModels = new ArrayList<>();
    private SharedPreferences settings;
    private View root;
    private MainDb db;
    private int idUser;
    private List<RecipeModel> recipeModelList = new ArrayList<>();
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_favourite_recipes, container, false);

        settings = getContext().getSharedPreferences(AUTHORIZATION, Context.MODE_PRIVATE);

        idUser = settings.getInt(ID,0);

        db = Room.databaseBuilder(root.getContext(), MainDb.class, "RecipeBD").build();

        initFavouriteModels();
        if (favouriteRecipeModels.size()==0) {
            TextView a = root.findViewById(R.id.noFavouriteRecipeText);
            a.setVisibility(TextView.VISIBLE);
        }

        RecyclerView mRecyclerView = (RecyclerView) root.findViewById(R.id.favouriteRecyclerView);
        mRecyclerView.setLayoutManager(new GridLayoutManager(root.getContext(), 2, GridLayoutManager.VERTICAL, false));
        FavouriteRecipeAdapter favouriteAdapter = new FavouriteRecipeAdapter(root.getContext(),favouriteRecipeModels,this);
        mRecyclerView.setAdapter(favouriteAdapter);
        return root;

    }

    private void initFavouriteModels() {
        favouriteRecipeModels.clear();

        Thread thread = new Thread(() -> {
            try {
                recipeModelList = db.getFavouriteRecipeDao().getFavouriteRecipesUser(idUser);
            } catch (SQLiteConstraintException e) {
                System.out.println("Ошибка с currentUser(его нет)");
                System.out.println(e.getMessage());
            }
        });


        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        favouriteRecipeModels.addAll(recipeModelList);
    }

    @Override
    public void onItemClicked(int position) {
        Bundle bundle = new Bundle();
        bundle.putInt("recipe_id",favouriteRecipeModels.get(position).getIdRecipe());

        Navigation.findNavController(root).navigate(R.id.action_favourite_recipes_to_recipeFragment,bundle);
    }

    @Override
    public void onItemLongClicked(int position) {

    }
}