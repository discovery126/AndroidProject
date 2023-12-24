package com.example.myapplication.ui.main_recipes;

import static com.example.myapplication.MainActivity.AUTHORIZATION;
import static com.example.myapplication.MainActivity.ID;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.database.FavouriteRecipe;
import com.example.myapplication.database.MainDb;
import com.example.myapplication.database.RecipeModel;

import java.util.Optional;

public class RecipeFragment extends Fragment {

//    private ArrayList<String> descriptionsArray = new ArrayList<>();
//    private ArrayList<String> componentsRecipeArray = new ArrayList<>();
    private View root;
    private Optional<RecipeModel> currentRecipeModel;
    private RecipeModel recipeModel;
    private int idRecipe;
    private MainDb db;
    private SharedPreferences settings;
    private int idUser;
    private CheckBox favouriteCheckBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        root = inflater.inflate(R.layout.fragment_page_recipe,container,false);
        settings = getContext().getSharedPreferences(AUTHORIZATION, Context.MODE_PRIVATE);
        idUser = settings.getInt(ID,0);

        db = Room.databaseBuilder(root.getContext(), MainDb.class, "RecipeBD").build();

        idRecipe = getArguments().getInt("recipe_id");

        Thread thread = new Thread(() -> {
            currentRecipeModel = db.getFavouriteRecipeDao().getFavouriteRecipesUser(idUser, idRecipe);
            recipeModel = db.getRecipeDao().getRecipe(idRecipe);
        });
        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        favouriteCheckBox = root.findViewById(R.id.favouriteCheckBox);


        if (currentRecipeModel.isPresent())
            favouriteCheckBox.setChecked(Boolean.TRUE);
        else
            favouriteCheckBox.setChecked(Boolean.FALSE);

        favouriteCheckBox.setOnCheckedChangeListener((compoundButton, b) -> addToCard(b));

        //setDescriptionRecipe();
        setPageRecipe(root);

        return root;
    }
//    private void setDescriptionRecipe() {
//        if (descriptionsArray.size()==0) {
//            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_CAESAR);
//            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_OLIVIE);
//            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_BORSCH);
//            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_SOUP_WITH_MEATBALLS);
//            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_UHA);
//            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_LULA_KEBAB);
//            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_POTATO_PIE);
//            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_ANNA_PAVLOVA);
//            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_NUTS_DESSERT);
//            descriptionsArray.add(DescriptionRecipe.DESCRIPTION_SELEDKA_POD_SHUBOI);
//        }
//        if (componentsRecipeArray.size()==0) {
//            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_CAESAR);
//            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_OLIVIE);
//            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_BORSCH);
//            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_SOUP_WITH_MEATBALLS);
//            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_UHA);
//            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_LULA_KEBAB);
//            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_POTATO_PIE);
//            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_ANNA_PAVLOVA);
//            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_NUTS_DESSERT);
//            componentsRecipeArray.add(DescriptionRecipe.COMPONENTS_SELEDKA_POD_SHUBOI);
//        }
//    }
    private void setPageRecipe(View view) {
        TextView tittle = view.getRootView().findViewById(R.id.title);

        TextView authorRecipeInDescription = view.findViewById(R.id.authorRecipeInDescription);

        ImageView imageRecipeInRecipe = view.findViewById(R.id.imageRecipeInRecipe);

        ListView pageRecipeListView = view.getRootView().findViewById(R.id.pageRecipeListView);

        Button a = view.findViewById(R.id.componentButton);

        Button b = view.findViewById(R.id.cookingRecipeButton);

        String[] componentsRecipe = recipeModel.getComponentsRecipe().split("\\n");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1,componentsRecipe);
        pageRecipeListView.setAdapter(adapter);

        a.setOnClickListener(view1 -> {
            ArrayAdapter<String> adapter12 = new ArrayAdapter<>(view1.getContext(), android.R.layout.simple_list_item_1,componentsRecipe);
            pageRecipeListView.setAdapter(adapter12);
        });

        b.setOnClickListener(view12 -> {
            String[] cookingRecipe = recipeModel.getDescriptionRecipe().split("\\n");
            ArrayAdapter<String> adapter1 = new ArrayAdapter<>(view12.getContext(), android.R.layout.simple_list_item_1,cookingRecipe);
            pageRecipeListView.setAdapter(adapter1);
        });

        tittle.setText(recipeModel.getTittleRecipe());
        authorRecipeInDescription.setText("Автор: ".concat(recipeModel.getAuthorRecipe()));
        imageRecipeInRecipe.setImageResource(recipeModel.getImageRecipe());
    }
    public void addToCard(boolean b) {
        Thread thread;

        if (favouriteCheckBox.isChecked()) {

            FavouriteRecipe favouriteRecipe = new FavouriteRecipe(idUser,idRecipe);

            thread = new Thread(() -> {
                try {
                    db.getFavouriteRecipeDao().addFavouriteRecipe(favouriteRecipe);
                } catch (SQLiteConstraintException e) {
                    System.out.println("Ошибка с currentUser(его нет)");
                    System.out.println(e.getMessage());
                }
            });

            Toast.makeText(getActivity(), "Добавлено", Toast.LENGTH_SHORT).show();


        } else {

            thread = new Thread(()  -> {
                try {
                    db.getFavouriteRecipeDao().deleteFavouriteRecipe(idUser,idRecipe);
                } catch (SQLiteConstraintException e) {
                    System.out.println("Ошибка с currentUser(его нет)");
                    System.out.println(e.getMessage());
                }
            });

            Toast.makeText(getActivity(), "Удалено", Toast.LENGTH_SHORT).show();
        }

        thread.start();

        try {
            thread.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}